package thrift;




public class ThriftTest {

	public static void main(String args[]) {
		//String servletUrl = "http://localhost:8080/groupproject/studentThriftServlet";
		
		try{ 
			LecturerClient l = new LecturerClient();
			StudentClient s = new StudentClient();
			
			
			
			/*
			List<SpeedVotingResult> list = l.getSpeedHistory(150, 0);
			SpeedVotingResult r = list.get(0);
			System.out.println(r);
			int slower = -1;
			int ok = -1;
			int faster = -1;
			
			Map<Speed, Integer> speedVotingResultMap = new HashMap<Speed, Integer>();
			speedVotingResultMap = r.getEntries();
			
			for(Map.Entry<Speed,Integer> entry : speedVotingResultMap.entrySet()) {
				System.out.println(entry.getKey() + " : " +entry.getValue());
				if(entry.getKey().equals(Speed.SLOWER)) slower = entry.getValue(); 
				if(entry.getKey().equals(Speed.OK)) ok = entry.getValue();
				if(entry.getKey().equals(Speed.FASTER)) faster = entry.getValue(); 
			}
			
			System.out.println(slower +" | " + ok + " | " + faster);*/
			
			/*
			List<PauseVotingResult> list = l.getPauseHistory(1, 0);
			System.out.println("The PauseVotingResults are: ");
			for(int i=0; i<list.size(); i++){
				PauseVotingResult pvr = list.get(i);
				Map<Pause,Integer> entries = pvr.getEntries(); 
				
				System.out.println("At time "+pvr.getTime()+" : ");
				
				for(Map.Entry<Pause,Integer> entry : entries.entrySet()) {
					System.out.println(entry.getKey() + " : " +entry.getValue());
				}
			}*/
			
			//s.addAnswer(2, "blablubb23");
			
			//s.addQuestion(3, "another question?");
			
			/*
			List<Question> questions = s.getQuestions(3);
			//Question q = s.addQuestion(3, "is it working?");
			System.out.println("Found " + questions.size() + " questions!");
			for(int i=0; i<questions.size(); i++) {
			Question q = questions.get(i);
				
			System.out.println(q.getLectureId() + " | " + q.getId());
			System.out.println("question: " + q.getQuestionText() + " at time: " + q.getTime());
			System.out.println(q.isAnswered + " | " + q.getVoting());
			System.out.println("has "+q.getAnswersSize()+" answers:");
				for(int j=0; j<q.getAnswersSize(); j++) {
					System.out.println("-- " + q.getAnswers().get(j));
				}
			System.out.println();
			}*/
			
			
			/*
			TTransport transport = new TSocket("localhost",8080);
			transport.open();
			
			TProtocol protocol = new TBinaryProtocol(transport);
			
			Student.Client client = new Student.Client(protocol);
			client.registerCallback("home", 23);
			
			
			TTransport transport2 = new TSocket("localhost",8080);
			transport2.open();
			
			TProtocol protocol2 = new TBinaryProtocol(transport2);
			
			Lecturer.Client lclient = new Lecturer.Client(protocol);
			lclient.registerCallback("home", 55);*/
			
			
			/* old tests
			THttpClient thc = new THttpClient(servletUrl);
			TProtocol protocol = new TBinaryProtocol(thc);
			Student.Client client = new Student.Client(protocol); 
		
			client.addAnswer(0, "test"); 
			//Expected outcome:
			// "Testing thrift! (StudentServiceImpl)" on standard output
			// real functionality not yet used
			
			if(thc.isOpen()) thc.close();
			
			
			thc = new THttpClient("http://localhost:8080/groupproject/lecturerThriftServlet");
			protocol = new TBinaryProtocol(thc);
			Lecturer.Client lclient = new Lecturer.Client(protocol); 
		
			lclient.registerCallback("ip", 0);
			if(thc.isOpen()) thc.close();
			
			thc = new THttpClient("http://localhost:8080/groupproject/studentThriftServlet");
			protocol = new TBinaryProtocol(thc);
			client = new Student.Client(protocol); 
			client.registerCallback("home", 23);
			*/
			
			/** LecturerClient l = new LecturerClient();
			
			LecturesDAOImpl dao = new LecturesDAOImpl();
			int id = -1;
			id = l.newLecture("spring testing2....");
			
			System.out.println("ID is : "+id);
			**/
			
			
			/*
			List<SpeedVotingResult> list = l.getSpeedHistory(1, 0);
			System.out.println("The SpeedVotingResults are: ");
			for(int i=0; i<list.size(); i++){
				SpeedVotingResult svr = list.get(i);
				Map<Speed,Integer> entries = svr.getEntries(); 
				
				System.out.println("At time "+svr.getTime()+" : ");
				
				for(Map.Entry<Speed,Integer> entry : entries.entrySet()) {
					System.out.println(entry.getKey() + " : " +entry.getValue());
				}
			}
			*/
			
			/*List<PauseVotingResult> list = l.getPauseHistory(1, 0);
			System.out.println("The PauseVotingResults are: ");
			for(int i=0; i<list.size(); i++){
				PauseVotingResult pvr = list.get(i);
				Map<Pause,Integer> entries = pvr.getEntries(); 
				
				System.out.println("At time "+pvr.getTime()+" : ");
				
				for(Map.Entry<Pause,Integer> entry : entries.entrySet()) {
					System.out.println(entry.getKey() + " : " +entry.getValue());
				}
			}*/
			
		} catch (Exception e) {
			System.out.println(e.getClass().toString());
		}
	}
}
