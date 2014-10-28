package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import de.tum.in.dss.project.IllegalAnswerException;
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.UnknownIdException;

public class QuestionsDAOImpl implements QuestionsDAO{
	
	private SimpleJdbcTemplate template;

	@Autowired
	public void setDataSource(DriverManagerDataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		//System.out.println("QuestionsDAO: dataSource set!");
	}
	
	// RowMapper for Questions
	ParameterizedRowMapper<Question> questionMapping = new ParameterizedRowMapper<Question>() {
		public Question mapRow(ResultSet rs, int row) throws SQLException {
			Question question = new Question();
			question.setId(rs.getInt(1));
			question.setLectureId(rs.getInt(2));
			question.setTime(rs.getLong(3));
			question.setQuestionText(rs.getString(4));
			question.setVoting(rs.getInt(5));
			question.setIsAnswered(rs.getBoolean(6));
			return question;
		}
	};
	
	// returns all questions for lecture with id lectureID
	@Override
	public List<Question> getQuestions(int lectureID) throws UnknownIdException {
		//check if the lectureID exists; if check is 0 -> doesn't exist -> throw UnknownIdException
		int check = template.queryForInt("SELECT lectureID FROM lectures WHERE lectureID = "+lectureID+"");
		if(check == 0) throw new UnknownIdException();
		
		//get a list of all questions for the given lecture ID
		List<Question> questionList = template.query("SELECT questionID,lectureID,timeAsked,questionText,voting,isAnswered " +
															"FROM questions WHERE lectureID = "+lectureID+"", questionMapping);
		
		//add answers to all questions for the lectureID
		for(int i=0; i<questionList.size(); i++) {
			int currentId = questionList.get(i).getId();
			List<String> answerList = getAnswerList(currentId); 
			questionList.get(i).setAnswers(answerList);
		}
		
		return questionList;
	}
	
	// mark question with id questionID as answered
	@Override
	public void markQuestionAsAnswered(int questionID) throws UnknownIdException {
		//try to update the isAnswered value for the given questionID; if check=0 questionID does not exist -> throw UnknownIdException
		int check = template.update("UPDATE questions SET isAnswered = true WHERE questionID="+questionID+""); 
		if(check == 0) throw new UnknownIdException();
	}
	
	// increment the votings a question has
	@Override
	public void voteQuestion(int questionID, boolean vote) throws UnknownIdException {
		//check if question exists
		int check = template.queryForInt("SELECT questionID FROM questions WHERE questionID = "+questionID+"");
		if(check == 0) throw new UnknownIdException();
		
		//get the currently saved value for the questionID, increment it and save the new value to the database
		int oldVoting = template.queryForInt("SELECT voting FROM questions WHERE questionID = "+questionID+"");
		int newVoting = oldVoting + 1;
		template.update("UPDATE questions SET voting = ? WHERE questionID = ?", new Object[] {newVoting, questionID } );
	}
	
	// add new Question object to database
	@Override
	public Question addQuestion(int lectureID, String question) throws UnknownIdException {
		//check if lecture exists
		int check = template.queryForInt("SELECT lectureID FROM lectures WHERE lectureID = "+lectureID+"");
		if(check == 0) throw new UnknownIdException();
		
		//the current time
		long timeNow = new Date().getTime();
		//add new question to the questions table
		template.update("INSERT INTO questions (lectureID,timeAsked,questionText) values(?,?,?)",
				new Object[] {lectureID, timeNow, question});
		
		//get the question from the questions table and return it
		Question q = template.queryForObject("SELECT questionID,lectureID,timeAsked,questionText,voting,isAnswered " +
				"FROM questions WHERE questionText = ?", questionMapping, question);
		return q;
	}
	
	
	//RowMapper for Boolean
	ParameterizedRowMapper<Boolean> boolMapping = new ParameterizedRowMapper<Boolean>() {
		public Boolean mapRow(ResultSet rs, int row) throws SQLException {
			boolean value = rs.getBoolean(1);
			return value;
		}
	};
	
	// add a new answer to the Question with id questionID 
	// throws IllegalAnswerException if question is marked as answered
	@Override
	public void addAnswer(int questionID, String answer) throws UnknownIdException, IllegalAnswerException {
		//check if question exists
		int check = template.queryForInt("SELECT questionID FROM questions WHERE questionID = "+questionID+"");
		if(check == 0) throw new UnknownIdException();
		
		//check if question is already answered
		Boolean isAnswered = template.queryForObject("SELECT isAnswered FROM questions WHERE questionID = ?", boolMapping, questionID);
		if(isAnswered == true) throw new IllegalAnswerException();
		
		//add new answer to answers table
		template.update("INSERT INTO answers (questionID,answer) values(?,?)", new Object[] {questionID, answer});
	}
	
	
	// RowMapper for Strings
	ParameterizedRowMapper<String> stringMapping = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int row) throws SQLException {
			String s = rs.getString(1);
			return s;
		}
	};
	
	// returns a list of all answers for a given questionID
	private List<String> getAnswerList(int questionID) {
		//get a list of all answers for the given questionID
		List<String> list = template.query("SELECT answer FROM answers WHERE questionID = "+questionID+"", stringMapping); 
		return list;
	}
	
}
