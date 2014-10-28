package thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import de.tum.in.dss.project.IllegalAnswerException;
import de.tum.in.dss.project.IllegalRatingException;
import de.tum.in.dss.project.Pause;
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.Rating;
import de.tum.in.dss.project.Speed;
import de.tum.in.dss.project.Student;
import de.tum.in.dss.project.UnknownIdException;

public class StudentClient {
	
	private Student.Client client;
	
	public StudentClient() {
		setClient();
	}
	
	private void setClient() {
		TTransport transport = new TSocket("localhost",9191);
		try{
			transport.open();
		} catch (TTransportException tte) {
			System.out.println("TTransportException while creating StudentClient");
			return;
		}
		
		TProtocol protocol = new TBinaryProtocol(transport);
		
		Student.Client client = new Student.Client(protocol);
		this.client = client;
	}
	
	public void voteLectureSpeed(int lectureId, Speed speedOld, Speed speedNew)
			throws UnknownIdException, TException {
		client.voteLectureSpeed(lectureId, speedOld, speedNew);
	}
	
	public void voteForPause(int lectureId, Pause pauseOld, Pause pauseNew)
			throws UnknownIdException, TException {
		client.voteForPause(lectureId, pauseOld, pauseNew);
	}
	
	public List<Question> getQuestions(int lectureId)
			throws UnknownIdException, TException {
		List<Question> questions = client.getQuestions(lectureId);
		return questions;
	}
	
	public void voteQuestion(int questionId, boolean vote)
			throws UnknownIdException, TException {
		client.voteQuestion(questionId, vote);
	}
	
	public Question addQuestion(int lectureId, String question)
			throws UnknownIdException, TException {
		Question newQuestion = client.addQuestion(lectureId, question);
		return newQuestion;
	}
	
	public void addAnswer(int questionId, String answer)
			throws UnknownIdException, IllegalAnswerException, TException {
		client.addAnswer(questionId, answer);
	}
	
	public void rateLecture(int lectureId, Rating rating)
			throws UnknownIdException, IllegalRatingException, TException {
		client.rateLecture(lectureId, rating);
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
