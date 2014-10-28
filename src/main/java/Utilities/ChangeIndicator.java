package Utilities;

public class ChangeIndicator {

	private int id;
	private boolean questionsHaveChanged = false;
	private boolean speedVotingHasChanged = false;
	private boolean pauseVotingHasChanged = false;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
	
	public void setQestionsHaveChanged(boolean questionsHaveChanged) {
		this.questionsHaveChanged = questionsHaveChanged;
	}
	public boolean getQuestionsHaveChanged() {
		return this.questionsHaveChanged;
	}
	
	public void setSpeedVotingHasChanged(boolean speedVotingHasChanged) {
		this.speedVotingHasChanged = speedVotingHasChanged;
	}
	public boolean getSpeedVotingHasChanged() {
		return this.speedVotingHasChanged;
	}
	
	public void setPauseVotingHasChanged(boolean pauseVotingHasChanged) {
		this.pauseVotingHasChanged = pauseVotingHasChanged;
	}
	public boolean getPauseVotingHasChanged() {
		return this.pauseVotingHasChanged;
	}
}
