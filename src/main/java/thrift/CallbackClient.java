package thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import de.tum.in.dss.project.Callback;
import de.tum.in.dss.project.PauseVotingResult;
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.SpeedVotingResult;

public class CallbackClient {
	
	private String ip = "localhost";
	private int port = 9292;
	private Callback.Client client;
	
	//default Constructor
	public CallbackClient() {
		setClient();
	}
	
	//Constructor with specific ip and port for the client
	public CallbackClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
		setClient();
	}
	
	private void setClient() {
		TTransport transport = new TSocket(ip,port);
		try { 
			transport.open();
		} catch (TTransportException e) {
			System.out.println("TTransportException while creating CallbackClient");
			return;
		}
			
		TProtocol protocol = new TBinaryProtocol(transport);
			
		Callback.Client client = new Callback.Client(protocol);
		this.client = client;
	}
	
	public void notifyQuestion(Question question) throws TException {
		client.notifyQuestion(question);
	}

	public void notifySpeedVoting(int lectureId, SpeedVotingResult currentResult)
			throws TException {
		client.notifySpeedVoting(lectureId, currentResult);
	}

	public void notifyPauseVoting(int lectureId, PauseVotingResult currentResult)
			throws TException {
		client.notifyPauseVoting(lectureId, currentResult);
	}
}
