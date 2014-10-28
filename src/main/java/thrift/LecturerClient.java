package thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import de.tum.in.dss.project.Lecture;
import de.tum.in.dss.project.Lecturer;
import de.tum.in.dss.project.PauseVotingResult;
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.SpeedVotingResult;
import de.tum.in.dss.project.UnknownIdException;

public class LecturerClient {

	private Lecturer.Client client;
	
	public LecturerClient() {
		setClient();
	}
	
	//open transport, create and return the TProtocol
	private void setClient() {
		TTransport transport = new TSocket("localhost",9090);
		
		try{ 
			transport.open();
		} catch (TTransportException tte) {
			System.out.println("TTransportException while creating LecturerClient");
			return;
		}
		
		TProtocol protocol = new TBinaryProtocol(transport); 
		
		Lecturer.Client client = new Lecturer.Client(protocol);
		
		this.client = client;
	}
		
	public List<SpeedVotingResult> getSpeedHistory(int lectureId, long start)
			throws UnknownIdException, TException {
		List<SpeedVotingResult> speedHistory = client.getSpeedHistory(lectureId, start);
		return speedHistory;
	}
	
	public List<PauseVotingResult> getPauseHistory(int lectureId, long start)
			throws UnknownIdException, TException {
		List<PauseVotingResult> pauseHistory = client.getPauseHistory(lectureId, start);
		return pauseHistory;
	}
	
	public double getRating(int lectureId) throws UnknownIdException, TException {
		double rating = client.getRating(lectureId);		
		return rating;
	}
	
	public List<Question> getQuestions(int lectureId)
			throws UnknownIdException, TException {
		List<Question> questions = client.getQuestions(lectureId);	
		return questions;
	}
	
	public void markQuestionAsAnswered(int questionId)
			throws UnknownIdException, TException {
		client.markQuestionAsAnswered(questionId);
	}
	
	public int newLecture(String name) throws TException {
		int lectureId = client.newLecture(name);
		return lectureId;
	}
	
	public Lecture getLecture(int id) throws UnknownIdException, TException {
		Lecture lecture = client.getLecture(id);
		return lecture;
	}
	
	public void updateLecture(Lecture lecture) throws UnknownIdException, TException {
		client.updateLecture(lecture);
	}
	
	public void registerCallback(String ip, int port) throws TException {
		client.registerCallback(ip, port);
		
		// Start callback thrift server
		CallbackServiceImpl callbackService = new CallbackServiceImpl();
		CallbackThriftServer cts = new CallbackThriftServer(callbackService);
		Thread callbackServerThread = new Thread(cts);
		callbackServerThread.start();
	}
	
}
