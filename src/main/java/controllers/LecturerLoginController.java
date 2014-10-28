package controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.SecureRandom;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.thrift.TException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import Utilities.ChangeIndicator;
import Utilities.QuestionsPerInterval;

import thrift.LecturerClient;
import de.tum.in.dss.project.Lecture;

public class LecturerLoginController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//LecturerClient lectureClientService = (LecturerClient) context.getBean("LecturerClient");
		
		String lectureName = RandomStringUtils.randomAlphanumeric(6);
		System.out.println("new lecturename: " + lectureName);
		//String lectureName = req.getParameter("lectureName");
		/**
		 * Form the Lecture name given by the Lecturer we get a Lecture Id from
		 * the database(auto increment value).
		 */
		
		LecturerClient lectureClientService = new LecturerClient();
		
		int lectureId = 0;
		Lecture lecture = null;
		try {
			lectureId = lectureClientService.newLecture(lectureName);
			
			lecture = lectureClientService.getLecture(lectureId);
			
			//register for callbacks
			String ip = InetAddress.getLocalHost().getHostAddress();
			System.out.println("ip is: " + ip);
			lectureClientService.registerCallback(ip, 9292);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * Random Number generation for creating the QR code
		 */
		SecureRandom random = new SecureRandom();
		/**
		 * create a string of random number for QR code
		 */
		String qrCodeText = new BigInteger(130, random).toString(32);
		ByteArrayOutputStream out = QRCode.from(qrCodeText).to(ImageType.PNG)
				.stream(); 
		
		/**
		 * Create the QR code Image
		 */
		FileOutputStream fout = new FileOutputStream(new File("QR_Code.JPG"));
		fout.write(out.toByteArray());
		fout.flush();
		fout.close();
		
		ServletContext sc = req.getSession().getServletContext();
		//sc.setAttribute("lectureId", lectureId);
		
		String attribute = "lectureId_for_" + lectureName;
		sc.setAttribute(attribute, lectureId);
		
		String lectureObj = "lectureObj_for_"+lectureId;
		sc.setAttribute(lectureObj, lecture);
		
		req.setAttribute("lectureName", lectureName);
		req.setAttribute("lectureId", lectureId);
		RequestDispatcher rd = req.getRequestDispatcher("lecturer.jsp");
		rd.forward(req, resp);
	}
}
