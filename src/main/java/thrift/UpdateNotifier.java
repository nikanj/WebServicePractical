package thrift;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import de.tum.in.dss.project.PauseVotingResult;
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.SpeedVotingResult;

public class UpdateNotifier {

	private List<CallbackSubscriber> callbacks = new ArrayList<CallbackSubscriber>();
	
	public List<CallbackSubscriber> getCallbacks() {
		return this.callbacks;
	}
	
	public void registerCallback(String ip, int port) {   
		CallbackSubscriber cbSubscriber = new CallbackSubscriber();
		cbSubscriber.setIp(ip);
		cbSubscriber.setPort(port);
		
		callbacks.add(cbSubscriber);
	}
	
	public void changeToQuestion(Question question) throws TException {
		for(int i=0; i<callbacks.size(); i++) {
			CallbackSubscriber cbs = callbacks.get(i);
			
			CallbackClient client = new CallbackClient(cbs.getIp(), cbs.getPort());
			client.notifyQuestion(question);
		}
	}
	
	public void changeToSpeedVoting(int lectureId, SpeedVotingResult currentResult) throws TException {
		for (int i = 0; i<callbacks.size(); i++) {
			CallbackSubscriber cbs = callbacks.get(i);
			
			CallbackClient client = new CallbackClient(cbs.getIp(), cbs.getPort());
			client.notifySpeedVoting(lectureId, currentResult);
		}
	}
	
	public void changeToPauseVoting(int lectureId, PauseVotingResult currentResult) throws TException {
		for (int i = 0; i<callbacks.size(); i++) {
			CallbackSubscriber cbs = callbacks.get(i);
			
			CallbackClient client = new CallbackClient(cbs.getIp(), cbs.getPort());
			client.notifyPauseVoting(lectureId, currentResult);
		}
	}
}
