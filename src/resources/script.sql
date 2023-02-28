CREATE DATABASE script;
USE script;

-- student
CREATE TABLE STUDENT (
	ID VARCHAR(1),
	FullName VARCHAR(1),
CONSTRAINT STUDENT_PK PRIMARY KEY (ID)
);

-- instructor
CREATE TABLE INSTRUCTOR (
	FullName VARCHAR(1),
	ID VARCHAR(1),
);

-- course
CREATE TABLE COURSE (
	ID VARCHAR(1),
	Name VARCHAR(1),
	InstructorID VARCHAR(1),
);