package backendtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import backend.LecturesDAO;
import backend.LecturesDAOImpl;
import de.tum.in.dss.project.IllegalRatingException;
import de.tum.in.dss.project.Lecture;
import de.tum.in.dss.project.UnknownIdException;

public class LecturesDAOTester {

	private static final String name = "jUnitTestLecture";
	private static int testID;
	private static LecturesDAO lecturesDAO = new LecturesDAOImpl();
	
	@BeforeClass
	public static void newEntryTest() {
		int test = lecturesDAO.newLecture(name);
		testID = test;
	}
	
	
	@Test
	public void getLectureSuccesTest() {
		try{
			assertEquals("lectureID of the returned Lecture object should be the same as the testID ("+testID+")", testID, lecturesDAO.getLecture(testID).getLectureId());
		} catch (UnknownIdException e) {
			fail("UnknownIdException thrown, this should not have happened.");
		}
	}
	
	
	@Test (expected = EmptyResultDataAccessException.class)
	public void getLectureFailTest() throws UnknownIdException {
		lecturesDAO.getLecture(-1);
	}
	
	@Test (expected = UnknownIdException.class)
	public void updateLectureFailTest() throws UnknownIdException {
		Lecture lecture = new Lecture();
		lecture.setLectureId(-1);
		lecturesDAO.updateLecture(lecture);
	}
	
	@Test
	public void updateLectureSuccesTest() throws UnknownIdException {
		Lecture lecture = new Lecture();
		lecture.setLectureId(testID);
		lecture.setName(name);
		lecture.setEndAt(42);
		
		try{
			lecturesDAO.updateLecture(lecture);
		} catch (Exception e) {
			fail("Update was not possible. Test failed.");
		}
		assertEquals("endAt of updated lecture must equal 42", 42, lecturesDAO.getLecture(testID).getEndAt());
	}
	
	@Test (expected = EmptyResultDataAccessException.class)
	public void rateLectureFalseIdTest() throws UnknownIdException, IllegalRatingException {
		lecturesDAO.rateLecture(-1, 1);
	}
	
	/*
	@Test (expected = IllegalRatingException.class)
	public void rateLectureIllegalRatingTest() {
		// TODO
	}
	
	@Test
	public void rateLectureSuccesTest() {
		//TODO
	}
	*/
}
