package Utilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.thrift.TException;

import thrift.LecturerClient;
import de.tum.in.dss.project.Lecture;
import de.tum.in.dss.project.Question;
import de.tum.in.dss.project.UnknownIdException;

public class QuestionsPerInterval {

	private List<QuestionInterval> questionsPerInterval = new ArrayList<QuestionInterval>();
	
	public List<QuestionInterval> getQuestionsPerInterval() {
		return this.questionsPerInterval;
	}
	
	public void lectureHasEnded(int lectureId) {
		//create a new LecturerClient
		LecturerClient lecturerClient = new LecturerClient();
		try {
			// get lecture object for given lectureId
			Lecture lecture = lecturerClient.getLecture(lectureId);
			//get all questions for given lectureId
			List<Question> questions = lecturerClient.getQuestions(lectureId); 

			/**
			 * compute how many questions were asked in each 15 minute interval
			 */
			long start = lecture.getStartAt();
			long end = lecture.getEndAt();
			long intervalEnd;
			long intervalStart = start;
			while (intervalStart < end) {
				//set new intervalEnd
				intervalEnd = new Date(intervalStart + (1000 * 60 * 15) - 1).getTime();
				
				//create new QuestionInterval
				QuestionInterval qi = new QuestionInterval(intervalStart,intervalEnd);
				
				//check how many questions were asked in this interval
				int count = 0;
				for(int i=0; i<questions.size(); i++) {
					long timeAsked = questions.get(i).getTime();
					if(timeAsked >= intervalStart && timeAsked <= intervalEnd) count++;
				}
				qi.setQuestions(count);
				
				//add QuestionInterval to QuestionInterval list
				questionsPerInterval.add(qi);
				
				//set new intervalStart
				intervalStart = intervalEnd + 1;
			}
		
		} catch (UnknownIdException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
		
	}
}
