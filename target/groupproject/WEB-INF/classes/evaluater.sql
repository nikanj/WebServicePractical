#SHOW DATABASES;
USE evaluater;

-- table for lectures
DROP TABLE IF EXISTS `lectures`;
CREATE TABLE `lectures` (
	`lectureID`		INT			NOT NULL auto_increment,
	`name`			CHAR(100)	NOT NULL,
	`start`			BIGINT		,
	`end`			BIGINT		,
	`evaluatable`	BIGINT		,
	PRIMARY KEY (`lectureID`)
) auto_increment=1, ENGINE=InnoDB;

-- table for questions
DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions` (
	`questionID`	INT			NOT NULL auto_increment,
	`lectureID`		INT			NOT NULL,
	`timeAsked`		BIGINT		NOT NULL,
	`questionText`	LONGTEXT 	NOT NULL,
	`voting`		INT			NOT NULL default 0,
	`hasAnswers`	BOOL		NOT NULL default false,
	`isAnswered`	BOOL		NOT NULL default false,
	PRIMARY KEY (`questionID`)
) auto_increment=1, ENGINE=InnoDB;

-- table for answers
DROP TABLE IF EXISTS `answers`;
CREATE TABLE `answers` (
	`answerID`		INT 		NOT NULL auto_increment,
	`questionID`	INT 		NOT NULL,
	`answer`		LONGTEXT	NOT NULL, 
	PRIMARY KEY (`answerID`)
) auto_increment=1, ENGINE=InnoDB;

-- table for speed votings
DROP TABLE IF EXISTS `speedvotings`;
CREATE TABLE `speedvotings` (
	`svID`		INT			NOT NULL auto_increment,
	`lectureID`	INT			NOT NULL,
	`time`		BIGINT		NOT NULL,
	`slower`	INT			NOT NULL,
	`ok`		INT			NOT NULL,
	`faster`	INT			NOT NULL,
	PRIMARY KEY (`svID`)
) auto_increment=1, ENGINE=InnoDB;

-- table for pause votings
DROP TABLE IF EXISTS `pausevotings`;
CREATE TABLE `pausevotings` (
	`pvID`		INT			NOT NULL auto_increment,
	`lectureID`	INT			NOT NULL,
	`time`		BIGINT		NOT NULL,
	`yes`		INT			NOT NULL,
	`no`		INT 		NOT NULL,
	`sleeping`	INT			NOT NULL,
	PRIMARY KEY (`pvID`)
) auto_increment=1, ENGINE=InnoDB;

-- table for evaluation votes
DROP TABLE IF EXISTS `evaluationvotes`;
CREATE TABLE `evaluationvotes` (
	`evID`		INT		NOT NULL auto_increment,
	`lectureID`	INT		NOT NULL,
	`voting`	INT		NOT NULL, 
	PRIMARY KEY (`evID`)
) auto_increment=1, ENGINE=InnoDB;


