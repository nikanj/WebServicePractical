package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="answers", catalog="evaluater")
public class Answer {

	private int answerID;
	private int questionID;
	private String answer;
	
	public Answer(){
	}
	
	public Answer(int questionID, String answer) {
		this.questionID = questionID;
		this.answer = answer;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="answerID", unique=true, nullable=false)
	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}
	public int getAnswerID() {
		return this.answerID;
	}
	
	@Column(name="questionID", nullable=false)
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
	public int getQuestionID() {
		return this.questionID;
	}
	
	@Column(name="answer", nullable=false)
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getAnswer() {
		return this.answer;
	}

}
