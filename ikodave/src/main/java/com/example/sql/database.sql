create schema if not exists Ikodave;

create table if not exists users (
    id int auto_increment primary key,
    username varchar(50) not null unique,
    password varchar(255) not null
);

-- problems
CREATE TABLE IF NOT EXISTS problems (
    id INT PRIMARY KEY,
    problem_name VARCHAR(100),
    problem_description TEXT,
    difficulty_id INT,
    FOREIGN KEY (difficulty_id) REFERENCES problem_difficulty(id)
);

CREATE TABLE IF NOT EXISTS problem_difficulty (
    id INT PRIMARY KEY,
    difficulty varchar(16)
);

CREATE TABLE IF NOT EXISTS problem_many_to_many_topic (
    id INT PRIMARY KEY,
    problem_id INT,
    topic_id INT,
    FOREIGN KEY (problem_id) REFERENCES problems(id),
    FOREIGN KEY (topic_id) REFERENCES problem_topic(id)
);

CREATE TABLE IF NOT EXISTS problem_topic (
    id INT PRIMARY KEY,
    topic varchar(16)
);
--



-- submissions
CREATE TABLE IF NOT EXISTS submissions (
    id INT PRIMARY KEY,
    user_id INT,
    problem_id INT,
    solution_code TEXT,
    FOREIGN KEY (problem_id) REFERENCES problems(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    verdict ENUM('TODO', 'ATTEMPTED', 'SOLVED') NOT NULL
);
--


