package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="evaluationvotes", catalog="evaluater")
public class EvaluationVoting {

	private int evID;
	private int lectureID;
	private int voting;
	
	public EvaluationVoting() {
	}
	
	public EvaluationVoting(int lectureID, int voting) {
		this.lectureID = lectureID;
		this.voting = voting;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="evID", unique=true, nullable=false)
	public void setEvID(int evID) {
		this.evID = evID;
	}
	public int getEvID() {
		return this.evID;
	}
	
	@Column(name="lectureID", nullable=false)
	public void setLectureID(int lectureID) {
		this.lectureID = lectureID;
	}
	public int getLectureID() {
		return this.lectureID;
	}
	
	@Column(name="voting", nullable=false)
	public void setVoting(int voting) {
		this.voting = voting;
	}
	public int getVoting() {
		return this.voting;
	}
	
}
