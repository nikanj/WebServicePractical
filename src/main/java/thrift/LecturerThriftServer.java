package thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import de.tum.in.dss.project.Lecturer;

public class LecturerThriftServer implements Runnable{
	
	private TServer tserver;
	
	public LecturerThriftServer(LecturerServiceImpl lecturerService) throws TTransportException {
		//processor
		Lecturer.Processor<LecturerServiceImpl> processor = new Lecturer.Processor<LecturerServiceImpl>(lecturerService);
		
		//socket
		TServerTransport serverTransport = new TServerSocket(9090);
		
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
		System.out.println("Starting LecturerThriftServer");
		tserver.serve();
	}
}
