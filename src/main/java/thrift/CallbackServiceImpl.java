package thrift;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;

import Utilities.ChangeManager;
import de.tum.in.dss.project.Callback;
import de.tum.in.dss.project.PauseVotingResult;
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.SpeedVotingResult;

public class CallbackServiceImpl implements Callback.Iface { 
	
	@Override
	public void notifyQuestion(Question question) throws TException {
		System.out.println("new question arrived");
		
		//TODO
		
	}

	@Override
	public void notifySpeedVoting(int lectureId, SpeedVotingResult currentResult)
			throws TException {
		System.out.println("new speedvote arrived");
		//TODO
		
	}

	@Override
	public void notifyPauseVoting(int lectureId, PauseVotingResult currentResult)
			throws TException {
		System.out.println("new pausevote arrived");
		//TODO
	}

}
