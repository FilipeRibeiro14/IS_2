CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS professor (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    FOREIGN KEY(id) REFERENCES relacionamento(professor_id) ON UPDATE CASCADE
);

--DROP TABLE student;
CREATE TABLE IF NOT EXISTS student (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    birth TEXT NOT NULL,
    credits integer NOT NULL,
    avg_grade float NOT NULL,
    FOREIGN KEY(id) REFERENCES relacionamento(student_id) ON UPDATE CASCADE
    
);
--DROP TABLE relacionamento;
CREATE TABLE IF NOT EXISTS relacionamento (
    id SERIAL PRIMARY KEY,
    student_id integer NOT NULL,
    professor_id integer NOT NULL,
    FOREIGN KEY(professor_id) REFERENCES professor(id) ON UPDATE CASCADE,
    FOREIGN KEY(student_id) REFERENCES student(id) ON UPDATE CASCADE  
);