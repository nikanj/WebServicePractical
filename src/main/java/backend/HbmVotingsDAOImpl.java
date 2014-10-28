package backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

import thrift.UpdateNotifier;
import de.tum.in.dss.project.Pause;
import de.tum.in.dss.project.PauseVotingResult;
import de.tum.in.dss.project.Speed;
import de.tum.in.dss.project.SpeedVotingResult;
import de.tum.in.dss.project.UnknownIdException;
import entities.EvaluationVoting;
import entities.PauseVoting;
import entities.SpeedVoting;

public class HbmVotingsDAOImpl implements VotingsDAO {
	
	private Session session;
	private Transaction tx;
	private UpdateNotifier updateNotifier;
	
	@Autowired
	public void setUpdateNotifier(UpdateNotifier updateNotifier) {
		this.updateNotifier = updateNotifier;
	}
	
	public HbmVotingsDAOImpl() {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		this.session = sessionFactory.getCurrentSession(); 
		this.tx = session.beginTransaction(); 
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public double getRating(int lectureID) throws UnknownIdException {
		if(!(validID(lectureID))) throw new UnknownIdException();
		
		double rating = 0.0;
		try{
			System.out.println("lectureID to search rating is " + lectureID);
			if(!session.isOpen()) connect();
			List<EvaluationVoting> votes = session.createQuery("FROM EvaluationVoting ev WHERE ev.lectureID = "+lectureID).list();
			
			if(votes.size() == 0) {
				System.out.println("+++++++ votes.size()==0 -> connection not open? -> connect and try again!!!!");
				connect();
				votes = session.createQuery("FROM EvaluationVoting ev WHERE ev.lectureID = "+lectureID).list();
			}
			
			System.out.println("votes size: " + votes.size());
			for(int i=0; i<votes.size(); i++) {
				System.out.println("vote: " + votes.get(i).getVoting());
				rating += (double)(votes.get(i).getVoting());
			}
			System.out.println("rating before averaging: " + rating);
			rating /= votes.size();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		System.out.println("rating in DAO: " +rating);
		return rating;
	}

	@Override
	public void voteLectureSpeed(int lectureID, int oldSpeed, int newSpeed) throws UnknownIdException {
		if(!(validID(lectureID))) throw new UnknownIdException();
		
		SpeedVoting sv = getLatestSpeedVoting(lectureID);
		SpeedVoting new_sv = new SpeedVoting();
		new_sv.setLectureID(lectureID);
		new_sv.setTime(new Date().getTime());
		if(sv==null) { //no SpeedVoting for this lectureID made yet
			System.out.println("first speed voting for lecture id "+lectureID+" !");
			new_sv.setSlower(0);
			new_sv.setOk(0);
			new_sv.setFaster(0);
			if(newSpeed==1) new_sv.setSlower(1);
			else if(newSpeed==2) new_sv.setOk(1);
			else if(newSpeed==3) new_sv.setFaster(1);
		}
		else { //update SpeedVoting 
			new_sv.setSlower(sv.getSlower());
			new_sv.setOk(sv.getOk());
			new_sv.setFaster(sv.getFaster());
			
			//change according to oldSpeed
			if(oldSpeed==1) {
				if(sv.getSlower() > 0) new_sv.setSlower(sv.getSlower() - 1);
			}
			else if(oldSpeed==2) {
				if(sv.getOk() > 0 && !(newSpeed==2)) new_sv.setOk(sv.getOk() - 1);
			}
			else if(oldSpeed==3) {
				if(sv.getFaster() > 0) new_sv.setFaster(sv.getFaster() - 1);
			}
			
			//change according to newSpeed
			if(newSpeed==1) new_sv.setSlower(sv.getSlower() + 1);
			else if(newSpeed==2) new_sv.setOk(sv.getOk() + 1);
			else if(newSpeed==3) new_sv.setFaster(sv.getFaster() + 1);
		}
		
		try{ 
			//if(!session.isConnected()) connect();
			session.saveOrUpdate(new_sv);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		
		
		// hand updateNotifier the newest SpeedVotingResult svr
		SpeedVotingResult svr = speedvotingToSpeedVotingResult(new_sv);
		try {
			updateNotifier.changeToSpeedVoting(lectureID, svr);
		} catch (TException e) {
			e.printStackTrace();
			System.out.println("TException in HbmVotingsDAOImpl!");
		}
	}

	@Override
	public void voteForPause(int lectureID, int oldPause, int newPause) throws UnknownIdException {
		if(!(validID(lectureID))) throw new UnknownIdException();
		
		PauseVoting pv = getLatestPauseVoting(lectureID);
		
		PauseVoting new_pv = new PauseVoting();
		new_pv.setLectureID(lectureID);
		new_pv.setTime(new Date().getTime());
		
		if(pv==null) { //no PauseVoting for this lectureID made yet
			System.out.println("first pause voting for lecture id "+lectureID+" !");
			new_pv.setYes(0);
			new_pv.setNo(0);
			new_pv.setSleeping(0);
			if(newPause==1) new_pv.setYes(1);
			else if(newPause==2) new_pv.setNo(1);
			else if(newPause==3) new_pv.setSleeping(1);
		}
		else { //update PauseVoting 
			new_pv.setYes(pv.getYes());
			new_pv.setNo(pv.getNo());
			new_pv.setSleeping(pv.getSleeping());
			
			//change according to oldPause
			if(oldPause==1) {
				if(pv.getYes() > 0) new_pv.setYes(pv.getYes() - 1);
			}
			else if(oldPause==2) {
				if(pv.getNo() > 0 && !(newPause==2)) new_pv.setNo(pv.getNo() - 1);
			}
			else if(oldPause==3) {
				if(pv.getSleeping() > 0) new_pv.setSleeping(pv.getSleeping() - 1);
			}
			
			//change according to newPause
			if(newPause==1) new_pv.setYes(pv.getYes() + 1);
			else if(newPause==2) new_pv.setNo(pv.getNo() + 1);
			else if(newPause==3) new_pv.setSleeping(pv.getSleeping() + 1);
		}
		
		try{
			//if(!session.isConnected()) connect();
			session.saveOrUpdate(new_pv);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		
		// hand updateNotifier the newest PauseVotingResult pvr
		PauseVotingResult pvr = pausevotingToPauseVotingResult(new_pv);
		try{
			updateNotifier.changeToPauseVoting(lectureID, pvr);
		} catch (TException e) {
			e.printStackTrace();
			System.out.println("TException in HbmVotingsDAOImpl!");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<SpeedVotingResult> getSpeedHistory(int lectureID, long start) throws UnknownIdException {
		
		System.out.println("lectureID: " + lectureID);
		if(!validID(lectureID)) throw new UnknownIdException();
		
		List<SpeedVoting> svList = new ArrayList<SpeedVoting>();
		try {
			if(!session.isConnected()) connect(); 
			svList = session.createQuery("FROM SpeedVoting sv WHERE sv.lectureID = "+lectureID).list();
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		
		List<SpeedVotingResult> results = new ArrayList<SpeedVotingResult>();
		for(int i=0; i<svList.size(); i++) {
			SpeedVotingResult svr = speedvotingToSpeedVotingResult(svList.get(i));
			results.add(svr);
		}
		
		return results;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PauseVotingResult> getPauseHistory(int lectureID, long start) throws UnknownIdException {
		if(!validID(lectureID)) throw new UnknownIdException();
		
		List<PauseVoting> pvList = new ArrayList<PauseVoting>();
		try{
			if(!session.isConnected()) connect();
			pvList = session.createQuery("FROM PauseVoting pv WHERE pv.lectureID = "+lectureID).list();
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		
		List<PauseVotingResult> results = new ArrayList<PauseVotingResult>();
		for(int i=0; i<pvList.size(); i++) {
			PauseVotingResult pvr = pausevotingToPauseVotingResult(pvList.get(i));
			results.add(pvr);
		}
		
		return results;
	}
	
	
	
	private SpeedVotingResult speedvotingToSpeedVotingResult(SpeedVoting sv) {
		SpeedVotingResult svr = new SpeedVotingResult();
		
		svr.setTime(sv.getTime());
		Map<Speed,Integer> entries = new HashMap<Speed,Integer>();
		entries.put(Speed.findByValue(1), sv.getSlower());
		entries.put(Speed.findByValue(2), sv.getOk());
		entries.put(Speed.findByValue(3), sv.getFaster());
		svr.setEntries(entries);
		
		return svr;
	}
	
	private PauseVotingResult pausevotingToPauseVotingResult(PauseVoting pv) {
		PauseVotingResult pvr = new PauseVotingResult();
		
		pvr.setTime(pv.getTime());
		Map<Pause,Integer> entries = new HashMap<Pause,Integer>();
		entries.put(Pause.findByValue(1), pv.getYes());
		entries.put(Pause.findByValue(2), pv.getNo());
		entries.put(Pause.findByValue(3), pv.getSleeping());
		pvr.setEntries(entries);
		
		return pvr;
	}
	
	private SpeedVoting getLatestSpeedVoting(int lectureID) {
		try {
			if(!session.isConnected()) connect();
			SpeedVoting sv = (SpeedVoting) session.createQuery("FROM SpeedVoting sv WHERE sv.lectureID = "+lectureID+" ORDER BY sv.time DESC LIMIT 1")
													.setMaxResults(1).uniqueResult();
			return sv;
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		return null;
	}
	
	private PauseVoting getLatestPauseVoting(int lectureID) {
		try{
			if(!session.isConnected()) connect();
			PauseVoting pv = (PauseVoting) session.createQuery("FROM PauseVoting pv WHERE pv.lectureID = "+lectureID+" ORDER BY pv.time DESC LIMIT 1")
													.setMaxResults(1).uniqueResult();
			return pv;
		} catch (HibernateException e) {
			e.printStackTrace();
			if(!(tx==null)) tx.rollback();
		}
		return null;
	}

	private boolean validID(int id) {
		long count = 0;
		try {
			if(!session.isConnected()) connect();
			
			count = (Long) session.createQuery("SELECT count(*) FROM LectureEntry le WHERE le.lectureID = "+id).uniqueResult();
			if(count == 0) {
				System.out.println("+++++++ count=0 -> connection not open? -> connect and try again!");
				connect();
				
				count = (Long) session.createQuery("SELECT count(*) FROM LectureEntry le WHERE le.lectureID = "+id).uniqueResult();
			}
		
			if(count == 1) return true;
		} catch (Exception e) {
			System.out.println("validID exception: " + e.getCause().toString());
			if(!(tx==null)) tx.rollback();
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void connect() {
		System.out.println("*** CONNECTING SESSION ***");
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		this.session = sessionFactory.getCurrentSession(); 
		this.tx = session.beginTransaction(); 
	}
}
