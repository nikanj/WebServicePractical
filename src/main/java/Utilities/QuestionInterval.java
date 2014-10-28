package Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuestionInterval {

	private String start;
	private String end;
	private int questions;
	
	public QuestionInterval() {		
	}
	
	public QuestionInterval(long begin, long end) {
		Date startDate = new Date(begin);
		Date endDate = new Date(end);
		this.start = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(startDate);
		this.end = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(endDate);
		this.questions = 0;
	}
	
	public void setStart(String start) {
		this.start = start;
	}
	public String getStart() {
		return this.start;
	}
	
	public void setEnd(String end) {
		this.end = end;
	}
	public String getEnd() {
		return this.end;
	}
	
	public void setQuestions(int questions) {
		this.questions = questions;
	}
	public int getQuestions() {
		return this.questions;
	}
	
}
