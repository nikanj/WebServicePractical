package backend;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Order;

import de.tum.in.dss.project.Lecture;
import de.tum.in.dss.project.UnknownIdException;
import entities.EvaluationVoting;
import entities.LectureEntry;

public class HbmLecturesDAOImpl implements LecturesDAO {
	
	private Session session;
	private Transaction tx;
	
	public HbmLecturesDAOImpl() {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		this.session = sessionFactory.getCurrentSession(); 
		this.tx = session.beginTransaction(); 
	}
	
	private int getNewestID() {
		LectureEntry le = new LectureEntry();
		try{
			if(!session.isOpen()) connect();
			//BigInteger newestID = (BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult();
			le = (LectureEntry) session.createCriteria(LectureEntry.class).addOrder(Order.desc("lectureID")).setMaxResults(1).uniqueResult();
			return le.getLectureID();
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		
		return le.getLectureID();
	}
	
	@Override
	public int newLecture(String name) {
		LectureEntry lecture = new LectureEntry();
		lecture.setName(name);
		
		/**
		 * Setting the dates for the lecture:
		 * lecture starts now, is rateable after 90 minutes and ends after 120 minutes
		 */
		Date start = new Date();
		Date evaluatable = new Date(start.getTime() + (1000 * 60 * 90));
		Date end = new Date(start.getTime() + (1000 * 60 * 120));
		
		lecture.setStart(start.getTime());
		lecture.setEnd(end.getTime());
		lecture.setEvaluatable(evaluatable.getTime());
		
		
		int latestID = -1;
		try{
			if(!session.isOpen()) connect(); 
			
			session.save(lecture);
			tx.commit();
			
			latestID = getNewestID(); 
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		
		return latestID;
	}

	@Override
	public Lecture getLecture(int id) throws UnknownIdException {
		Lecture l = new Lecture();
		
		try {
			if(!session.isOpen()) connect();
			String query = "FROM LectureEntry le WHERE le.lectureID = " +id;
			LectureEntry lecture = (LectureEntry) session.createQuery(query).uniqueResult();
			if(lecture == null) throw new UnknownIdException();
			
			l.setLectureId(lecture.getLectureID());
			l.setName(lecture.getName());
			l.setStartAt(lecture.getStart());
			l.setEndAt(lecture.getEnd());
			l.setRateableAfter(lecture.getEvaluatable());
			
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		
		return l;
	}

	@Override
	public void rateLecture(int lectureID, int voting) throws UnknownIdException {
		//check if lecture with lectureID exist, else throw exception
		if( !(validID(lectureID)) ) throw new UnknownIdException();
		
		try {
			if(!session.isOpen()) connect();
			EvaluationVoting ev = new EvaluationVoting();
			ev.setLectureID(lectureID);
			ev.setVoting(voting);
			
			session.saveOrUpdate(ev); 
			tx.commit();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
	}

	@Override
	public void updateLecture(Lecture lecture) throws UnknownIdException {
		//check if lecture with lectureID exist, else throw exception
		if( !(validID(lecture.getLectureId())) ) throw new UnknownIdException();
		
		try{
			if(!session.isOpen()) connect();
			
			LectureEntry le = getLectureEntry(lecture.getLectureId());
			le.setLectureID(lecture.getLectureId());
			le.setName(lecture.getName());
			le.setStart(lecture.getStartAt());
			le.setEnd(lecture.getEndAt());
			le.setEvaluatable(lecture.getRateableAfter());
			
			session.update(le);
			tx.commit();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		
	}

	private boolean validID(int id) {
		try {
			if(!session.isOpen()) connect();
			long count = (Long) session.createQuery("SELECT count(*) FROM LectureEntry le WHERE le.lectureID = "+id).uniqueResult();
			if(count == 0) {
				System.out.println("+++++++ count=0 -> connection not open? -> connect and try again!");
				connect();
				
				count = (Long) session.createQuery("SELECT count(*) FROM LectureEntry le WHERE le.lectureID = "+id).uniqueResult();
			}
			
			if(count == 1) return true;
		} catch (HibernateException e) {
			if(!(tx==null)) tx.rollback();
			e.printStackTrace();
		}
		return false;
	}
	
	private LectureEntry getLectureEntry(int id) {
		String query = "FROM LectureEntry le WHERE le.lectureID = " +id;
		LectureEntry lecture = (LectureEntry) session.createQuery(query).uniqueResult();
		return lecture;
	}
	
	private void connect() {
		System.out.println("*** CONNECTING SESSION ***");
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		this.session = sessionFactory.getCurrentSession(); 
		this.tx = session.beginTransaction(); 
	}
}
