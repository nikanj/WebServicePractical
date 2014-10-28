package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import de.tum.in.dss.project.Lecture;
import de.tum.in.dss.project.UnknownIdException;

public class LecturesDAOImpl implements LecturesDAO {

	private JdbcTemplate template;

	@Autowired
	public void setDataSource(DriverManagerDataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
		//System.out.println("LecturesDAO: dataSource set!");
	}
	
/*	public LecturesDAOImpl() {
		setDataSource();
	}
	
	private void setDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/evaluater");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		
		this.template = new JdbcTemplate(dataSource);
	}
*/
	

	//create a new lecture in the DB and return the ID of the lecture
	@Override
	public int newLecture(String name) {
		final String INSERT_LECTURE = "INSERT INTO lectures (name,start) VALUES(?,?)";
		final String LECTURE_NAME = name;
		//startDAte init value
		final long startTime = new Date().getTime();
		KeyHolder key = new GeneratedKeyHolder();
		
		template.update( 
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection conn)
							throws SQLException {
						PreparedStatement ps = conn.prepareStatement(INSERT_LECTURE, new String[] {"lectureID"});
						ps.setString(1, LECTURE_NAME);
						ps.setLong(2, startTime);
						return ps;
					}
				},
				key);
		
		int newID = key.getKey().intValue();
		
		return newID;
	}
	
	
	// RowMapper for Lectures
	ParameterizedRowMapper<Lecture> lectureMapping = new ParameterizedRowMapper<Lecture>() {

		public Lecture mapRow(ResultSet rs, int row) throws SQLException {
			Lecture lecture = new Lecture();
			lecture.setLectureId(rs.getInt(1));
			lecture.setName(rs.getString(2));
			lecture.setStartAt(rs.getLong(3));
			lecture.setEndAt(rs.getLong(4));
			lecture.setRateableAfter(rs.getLong(5));
			return lecture;
		}
	};
	
	//selects the data for the lecture with lectureID 'id' from the database and returns it
	@Override
	@SuppressWarnings("unchecked")
	public Lecture getLecture(int id) throws UnknownIdException {
		System.out.println("DAO tries to get lecture: " +id);
		//check if lecture with lectureID 'id' exists
		int check = template.queryForInt("SELECT lectureID FROM lectures WHERE lectureID = "+id+"");
		if(check == 0) throw new UnknownIdException();
		
		//get data for lecture with the given id out of the database
		final String GET_LECTURE = "SELECT lectureID,name,start,end,evaluatable FROM lectures WHERE lectureID = ?";
		List<Lecture> lectures = template.query(GET_LECTURE, new Object[] {id}, lectureMapping);
		Lecture lecture = lectures.get(0);
		
		return lecture;
	}
	
	// add a voting (int 1-5) for a lecture to the database
	@Override
	public void rateLecture(int lectureID, int voting) throws UnknownIdException {
		//check if lecture with given lectureID exists
		int check = template.queryForInt("SELECT lectureID FROM lectures WHERE lectureID = "+lectureID+"");
		if(check == 0) throw new UnknownIdException();
		
		//add new voting to evaluationvotes table
		final String ADD_VOTING = "INSERT INTO evaluationvotes (lectureID,voting) VALUES(?,?)";
		template.update(ADD_VOTING, new Object[] {lectureID, voting});
	}

	@Override
	public void updateLecture(Lecture lecture) throws UnknownIdException { 
		//update lecture in lectures table
		int check = template.update("UPDATE lectures SET name = ?, start = ?, end = ?, evaluatable = ? WHERE lectureID = ?", 
				new Object[] {lecture.getName(),lecture.getStartAt(),lecture.getEndAt(),lecture.getRateableAfter(),lecture.getLectureId()} );
		//if check = 0 the lecture is not in the db -> throw UnknownIdException
		if(check == 0) throw new UnknownIdException();
	}
	
}
