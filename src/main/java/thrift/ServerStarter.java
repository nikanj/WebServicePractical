package thrift;

import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;

public class ServerStarter {

	private LecturerServiceImpl lecturerService;
	private StudentServiceImpl studentService;
	
	@Autowired
	public void setLecturerService(LecturerServiceImpl lecturerService) throws TTransportException {
		this.lecturerService = lecturerService;
		System.out.println("lecturerService for server set!");
		
		// Start lecturer thrift server
		LecturerThriftServer lts = new LecturerThriftServer(this.lecturerService);
		Thread lecturerServerThread = new Thread(lts);
		lecturerServerThread.start();
	}
	
	@Autowired
	public void setStudentService(StudentServiceImpl studentService) throws TTransportException {
		this.studentService = studentService;
		System.out.println("ServerStarter: studentService set!");
		
		// Start student thrift server
		StudentThriftServer sts = new StudentThriftServer(this.studentService);
		Thread studentServerThread = new Thread(sts);
		studentServerThread.start();
	}
	
	/*
	public ServerStarter() throws TTransportException {
		// Start lecturer thrift server
		LecturerThriftServer lts = new LecturerThriftServer(lecturerService);
		Thread lecturerServerThread = new Thread(lts);
		lecturerServerThread.start();
		
		// Start student thrift server
		StudentThriftServer sts = new StudentThriftServer(studentService);
		Thread studentServerThread = new Thread(sts);
		studentServerThread.start();
	}*/
	
	
}
