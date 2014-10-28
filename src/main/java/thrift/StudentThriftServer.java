package thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import de.tum.in.dss.project.Student;

public class StudentThriftServer implements Runnable {

	private TServer tserver;
	
	public StudentThriftServer(StudentServiceImpl studentService) throws TTransportException {
		//processor
		Student.Processor<StudentServiceImpl> processor = new Student.Processor<StudentServiceImpl>(studentService);
		
		//socket
		TServerTransport serverTransport = new TServerSocket(9191);
		
		//protocol
		TProtocolFactory factory = new TBinaryProtocol.Factory();
		
		//start server
		TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport);
		args.processor(processor);
		args.protocolFactory(factory);
		TServer server = new TThreadPoolServer(args); 
		
		this.tserver = server;
	}
	
	public void run() {
		System.out.println("Starting StudentThriftServer");
		tserver.serve();
	}
	
}
