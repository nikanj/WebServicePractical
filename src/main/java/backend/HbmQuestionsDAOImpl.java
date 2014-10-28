package backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.thrift.TException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

import thrift.UpdateNotifier;
import de.tum.in.dss.project.IllegalAnswerException;
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.UnknownIdException;
import entities.Answer;
import entities.QuestionEntry;

public class HbmQuestionsDAOImpl implements QuestionsDAO {

	private Session session;
	private Transaction tx;
	private UpdateNotifier updateNotifier;
	
	@Autowired
	public void setUpdateNotifier(UpdateNotifier updateNotifier) {
		this.updateNotifier = updateNotifier;
	}
	
	public HbmQuestionsDAOImpl() {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		this.session = sessionFactory.getCurrentSession(); 
		this.tx = session.beginTransaction(); 
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Question> getQuestions(int lectureID) throws UnknownIdException {
		//check if lecture with lectureID exist, else throw exception
		if( !(validID(lectureID, "lectures")) ) throw new UnknownIdException();
		
		List<Question> questions = new ArrayList<Question>();
		try {
			if(!session.isOpen()) connect();
			List<QuestionEntry> questionList = 
					session.createQuery("FROM QuestionEntry qe WHERE qe.lectureID = "+lectureID+" ORDER BY qe.voting DESC, qe.timeAsked DESC").list();
			
			for(int i=0; i<questionList.size(); i++) {
				QuestionEntry qe = questionList.get(i);
				Question q = questionEntryToQuestion(qe);
				questions.add(q);
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		
		return questions;
	}

	@Override
	public void markQuestionAsAnswered(int questionID) throws UnknownIdException {
		if( !(validID(questionID,"questions")) ) throw new UnknownIdException();
		
		try {
			QuestionEntry qe = getQuestion(questionID);
			qe.setIsAnswered(true);
			if(!session.isOpen()) connect();
			session.saveOrUpdate(qe);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		
		callUpdateNotifier(questionID);
	}

	@Override
	public void voteQuestion(int questionID, boolean vote) throws UnknownIdException {
		if( !(validID(questionID,"questions")) ) throw new UnknownIdException();
		
		try {
			QuestionEntry qe = getQuestion(questionID);
			if(vote==true) qe.setVoting(qe.getVoting() + 1);
			else qe.setVoting(qe.getVoting() - 1);
			
			if(!session.isOpen()) connect();
			session.saveOrUpdate(qe);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		
		callUpdateNotifier(questionID);
	}

	@Override
	public Question addQuestion(int lectureID, String question) throws UnknownIdException {
		//check if lecture with lectureID exist, else throw exception
		if( !(validID(lectureID, "lectures")) ) throw new UnknownIdException();
		
		try {
			QuestionEntry qe = new QuestionEntry();
			qe.setLectureID(lectureID);
			qe.setQuestionText(question);
			qe.setTimeAsked(new Date().getTime());
			if(!session.isOpen()) connect();
			session.saveOrUpdate(qe);
			tx.commit();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		
		Question q = getNewestQuestion();
		return q;
	}

	@Override
	public void addAnswer(int questionID, String answer) throws UnknownIdException, IllegalAnswerException {
		if( !(validID(questionID,"questions")) ) throw new UnknownIdException();
		
		//check if the Question with questionID is marked as answered; throw IllegalAnswerException if true
		QuestionEntry qe = getQuestion(questionID);
		if(qe.getIsAnswered() == true) throw new IllegalAnswerException();
		
		try {
			Answer a = new Answer();
			a.setQuestionID(questionID);
			a.setAnswer(answer);
			if(!session.isOpen()) connect();
			session.saveOrUpdate(a);
			tx.commit();
			
			markQuestionForAnswers(questionID);
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		
		callUpdateNotifier(questionID);
	}
	
	/**
	 * Returns the newest Question 
	 */
	private Question getNewestQuestion() {
		Question q = null;
		try {
			if(!session.isOpen()) connect();
			//QuestionEntry qe = (QuestionEntry) session.createQuery("FROM QuestionEntry qe WHERE qe.questionText = '"+questionText+"'").setMaxResults(1).uniqueResult();
		
			QuestionEntry qe = (QuestionEntry) session.createCriteria(QuestionEntry.class).addOrder(Order.desc("questionID")).setMaxResults(1).uniqueResult();
			q = questionEntryToQuestion(qe);
		} catch (HibernateException e) {
			if(!(tx==null)) tx.rollback();
			e.printStackTrace();
		}
		
		return q;
	}
	
	/**
	 * Returns the QuestionEntry for the given questionID
	 */
	private QuestionEntry getQuestion(int questionID) {
		QuestionEntry qe = null;
		try {
			if(!session.isOpen()) connect();
			qe = (QuestionEntry) session.createQuery("FROM QuestionEntry qe WHERE qe.questionID = '"+questionID+"'").uniqueResult();
		} catch (HibernateException e) {
			if(!(tx==null)) tx.rollback();
			e.printStackTrace();
		}
		
		return qe;
	}
	
	/**
	 * Converts a given QuestionEntry object qe to a Question object q and returns q;
	 */
	private Question questionEntryToQuestion(QuestionEntry qe) {
		Question q = new Question();
		
		q.setId(qe.getQuestionID());
		q.setLectureId(qe.getLectureID());
		q.setTime(qe.getTimeAsked());
		q.setQuestionText(qe.getQuestionText());
		q.setVoting(qe.getVoting());
		q.setIsAnswered(qe.getIsAnswered());
		
		List<String> answers = new ArrayList<String>();
		if(qe.getHasAnswers()) answers = getAnswers(qe.getQuestionID());
		q.setAnswers(answers);
		
		return q;
	}
	
	/**
	 * returns a List with all answers for the question with the given questionID
	 */
	@SuppressWarnings("unchecked")
	private List<String> getAnswers(int questionID) {
		List<Answer> answerList = new ArrayList<Answer>();
		try {
			if(!session.isOpen()) connect();
			answerList = session.createQuery("FROM Answer a WHERE a.questionID = " + questionID).list();
		} catch (HibernateException e) {
			if(!(tx==null)) tx.rollback();
			e.printStackTrace();
		}
		
		List<String> answers = new ArrayList<String>();
		for(int i=0; i<answerList.size(); i++) {
			answers.add(answerList.get(i).getAnswer());
		}
		
		return answers;
	}

	/*
	 * checks if an entry for the given id is present in the table named tablename
	 */
	private boolean validID(int id, String tablename) {
		try {
			String query;
			if(tablename.equals("lectures")) query = "SELECT count(*) FROM LectureEntry le WHERE le.lectureID = "+id;
			else query = "SELECT count(*) FROM QuestionEntry qe WHERE qe.questionID = "+id;
			
			if(!session.isOpen()) connect();
			long count = (Long) session.createQuery(query).uniqueResult();
			if(count == 0) {
				System.out.println("+++++++ count=0 -> connection not open? -> connect and try again!");
				connect();
				
				count = (Long) session.createQuery(query).uniqueResult();;
			}
			
			if(count == 1) return true;
		} catch (HibernateException e) {
			if(!(tx==null)) tx.rollback();
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void markQuestionForAnswers(int questionID) {
		try { 
			if(!session.isOpen()) connect();
			QuestionEntry qe = getQuestion(questionID);
			qe.setHasAnswers(true);
			
			session.saveOrUpdate(qe);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
	}
	
	private void callUpdateNotifier(int questionID) {
		QuestionEntry qe = getQuestion(questionID);
		Question question = questionEntryToQuestion(qe);
		try {
			updateNotifier.changeToQuestion(question);
		} catch (TException e) {
			e.printStackTrace();
			System.out.println("TException in HbmQuestionsDAOImpl!");
		}
	}
	
	private void connect() {
		System.out.println("*** CONNECTING SESSION ***");
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		this.session = sessionFactory.getCurrentSession(); 
		this.tx = session.beginTransaction(); 
	}
}
