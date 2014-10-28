package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lectures", catalog="evaluater")
public class LectureEntry {
	
	private int lectureID;
	private String name;
	private long start;
	private long end;
	private long evaluatable;
	
	public LectureEntry() {
	}
	
	public LectureEntry(String name, long start, long end, long evaluatable) {
		this.name = name;
		this.start = start;
		this.end = end;
		this.evaluatable = evaluatable;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="lectureID", unique=true, nullable=false)
	public void setLectureID(int lectureID) {
		this.lectureID = lectureID;
	}
	public int getLectureID() {
		return this.lectureID;
	}
	
	@Column(name="name", nullable=false) 
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	
	@Column(name="start")
	public void setStart(long start) {
		this.start = start;
	}
	public long getStart() {
		return this.start;
	}
	
	@Column(name="end")
	public void setEnd(long end) {
		this.end = end;
	}
	public long getEnd() {
		return this.end;
	}
	
	@Column(name="evaluatable")
	public void setEvaluatable(long evaluatable) {
		this.evaluatable = evaluatable;
	}
	public long getEvaluatable() {
		return this.evaluatable;
	}
	
	

}
