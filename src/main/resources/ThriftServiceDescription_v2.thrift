namespace java de.tum.in.dss.project

enum Speed {
	SLOWER = 1,
	OK = 2,
	FASTER = 3
}

enum Pause {
	YES = 1,
	NO = 2,
	SLEEPING = 3,
}

enum Rating {
	VERY_BAD = 1, 
	BAD = 2,
	OK = 3,
	GOOD = 4,
	EXCELLENT = 5,
}

struct Question {
	1:i32 id,
	2:i32 lectureId,
	3:i64 time,	
	4:string questionText,
	5:i32 voting = 0,
	6:optional list<string> answers,
	7:bool isAnswered = false,
}

exception UnknownIdException {
	1:i32 errorCode, 
	2:string reason,
}

exception IllegalAnswerException {
	1: i32 questionId,
	2: string reason
}

exception IllegalRatingException {
	1: string reason
}

service Student {
	void voteLectureSpeed(1:i32 lectureId, 2:Speed speedOld, 3:Speed speedNew) throws (1:UnknownIdException e),
	
	void voteForPause(1:i32 lectureId, 2:Pause pauseOld, 3:Pause pauseNew) throws (1:UnknownIdException e),
	
	list<Question> getQuestions(1:i32 lectureId) throws (1:UnknownIdException e),
	
	void voteQuestion(1:i32 questionId, 2:bool vote) throws (1:UnknownIdException e),
	
	Question addQuestion(1:i32 lectureId, 2:string question) throws (1:UnknownIdException e),
	
	void addAnswer(1:i32 questionId, 2:string answer) throws (1:UnknownIdException ea, 2:IllegalAnswerException eb),
	
	void rateLecture(1:i32 lectureId, 2:Rating rating) throws (1:UnknownIdException ea, 2: IllegalRatingException eb),
	
	void registerCallback(1:string ip, 2:i32 port),
}

struct SpeedVotingResult {
	1: i64 time,
	2: map<Speed,i32> entries,
}

struct PauseVotingResult {
	1: i64 time,
	2: map<Pause,i32> entries,
}

struct Lecture {
	1: i32 lectureId,
	2: string name, 
	3: optional i64 startAt,
	4: optional i64 endAt,
	5: optional i64 rateableAfter,
}

service Callback {
	void notifyQuestion(1:Question question), 
	void notifySpeedVoting(1:i32 lectureId,2:SpeedVotingResult currentResult), 
	void notifyPauseVoting(1:i32 lectureId,2:PauseVotingResult currentResult),
}

service Lecturer {
	
	list<SpeedVotingResult> getSpeedHistory(1:i32 lectureId, 2:i64 start) throws (1:UnknownIdException e),
	
	list<PauseVotingResult> getPauseHistory(1:i32 lectureId, 2:i64 start) throws (1:UnknownIdException e),
	
	double getRating(1:i32 lectureId) throws (1:UnknownIdException e),
	
	list<Question> getQuestions(1:i32 lectureId, 2:i32 limit) throws (1:UnknownIdException e),
	
	void markQuestionAsAnswered(1:i32 questionId) throws (1:UnknownIdException e),
	
	i32 newLecture(1:string name),
	
	Lecture getLecture(1: i32 id) throws (1:UnknownIdException e), 
	
	void updateLecture(1:Lecture lecture) throws (1:UnknownIdException e),
	
	void registerCallback(1:string ip, 2:i32 port), 
}
