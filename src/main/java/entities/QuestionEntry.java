package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="questions", catalog="evaluater")
public class QuestionEntry {

	private int questionID;
	private int lectureID;
	private long timeAsked;
	private String questionText;
	private int voting;
	private boolean hasAnswers;
	private boolean isAnswerd;
	
	public QuestionEntry() {
	}
	
	public QuestionEntry(int lectureID, long timeAsked, String questionText, int voting, boolean hasAnswers, boolean isAnswered) {
		this.lectureID = lectureID;
		this.timeAsked = timeAsked;
		this.questionText = questionText;
		this.voting = voting;
		this.hasAnswers = hasAnswers;
		this.isAnswerd = isAnswered;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="questionID", unique=true, nullable=false)
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
	public int getQuestionID() {
		return this.questionID;
	}
	
	@Column(name="lectureID", nullable=false)
	public void setLectureID(int lectureID) {
		this.lectureID = lectureID;
	}
	public int getLectureID() {
		return this.lectureID;
	}
	
	@Column(name="timeAsked", nullable=false)
	public void setTimeAsked(long timeAsked) {
		this.timeAsked = timeAsked;
	}
	public long getTimeAsked() {
		return this.timeAsked;
	}
	
	@Column(name="questionText", nullable=false) 
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public String getQuestionText() {
		return this.questionText;
	}
	
	@Column(name="voting", nullable=false)
	public void setVoting(int voting) {
		this.voting = voting;
	}
	public int getVoting() {
		return this.voting;
	}
	
	@Column(name="hasAnswers", nullable=false)
	public void setHasAnswers(boolean hasAnswers) {
		this.hasAnswers = hasAnswers;
	}
	public boolean getHasAnswers() {
		return this.hasAnswers;
	}
	
	@Column(name="isAnswered", nullable=false) 
	public void setIsAnswered(boolean isAnswered) {
		this.isAnswerd = isAnswered;
	}
	public boolean getIsAnswered() {
		return this.isAnswerd;
	}
	
}
