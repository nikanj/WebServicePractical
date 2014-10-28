package thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import de.tum.in.dss.project.Callback;

public class CallbackThriftServer implements Runnable {

	private TServer tserver;
	
	public CallbackThriftServer(CallbackServiceImpl callbackService) throws TTransportException {
		//processor
		Callback.Processor<CallbackServiceImpl> processor = new Callback.Processor<CallbackServiceImpl>(callbackService);
		
		//socket
		TServerTransport serverTransport = new TServerSocket(9292); 
		
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
		System.out.println("Starting CallbackThriftServer");
		tserver.serve();
	}
	
}
