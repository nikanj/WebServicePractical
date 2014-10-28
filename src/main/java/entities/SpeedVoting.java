package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="speedvotings", catalog="evaluater")
public class SpeedVoting {

	private int svID;
	private int lectureID;
	private long time;
	private int slower;
	private int ok;
	private int faster;
	
	public SpeedVoting() {
	}
	
	public SpeedVoting(int lectureID, long time, int slower, int ok, int faster) {
		this.lectureID = lectureID;
		this.time = time;
		this.slower = slower;
		this.ok = ok;
		this.faster = faster;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="svID", unique=true, nullable=false)
	public void setSvID(int svID) {
		this.svID = svID;
	}
	public int getSvID() {
		return this.svID;
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
	
	@Column(name="slower", nullable=false)
	public void setSlower(int slower) {
		this.slower = slower;
	}
	public int getSlower() {
		return this.slower;
	}
	
	@Column(name="ok", nullable=false)
	public void setOk(int ok) {
		this.ok = ok;
	}
	public int getOk() {
		return this.ok;
	}
	
	@Column(name="faster", nullable=false)
	public void setFaster(int faster) {
		this.faster = faster;
	}
	public int getFaster() {
		return this.faster;
	}
	
}
