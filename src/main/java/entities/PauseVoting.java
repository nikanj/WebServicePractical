package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pausevotings", catalog="evaluater")
public class PauseVoting {

	private int pvID;
	private int lectureID;
	private long time;
	private int yes;
	private int no;
	private int sleeping;
	
	public PauseVoting() {
	}
	
	public PauseVoting(int lectureID, long time, int yes, int no, int sleeping) {
		this.lectureID = lectureID;
		this.time = time;
		this.yes = yes;
		this.no = no;
		this.sleeping = sleeping;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pvID", unique=true, nullable=false)
	public void setPvID(int pvID) {
		this.pvID = pvID;
	}
	public int getPvID() {
		return this.pvID;
	}
	
	@Column(name="lectureID", nullable=false)
	public void setLectureID(int lectureID) {
		this.lectureID = lectureID;
	}
	public int getLectureID() {
		return this.lectureID;
	}
	
	@Column(name="time", nullable=false)
	public void setTime(long time) {
		this.time = time;
	}
	public long getTime() {
		return this.time;
	}
	
	@Column(name="yes", nullable=false)
	public void setYes(int yes) {
		this.yes = yes;
	}
	public int getYes() {
		return this.yes;
	}
	
	@Column(name="no", nullable=false)
	public void setNo(int no) {
		this.no = no;
	}
	public int getNo() {
		return this.no;
	}
	
	@Column(name="sleeping", nullable=false)
	public void setSleeping(int sleeping) {
		this.sleeping = sleeping;
	}
	public int getSleeping() {
		return this.sleeping;
	}
}
