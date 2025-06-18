create schema if not exists Ikodave;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
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
    status_id INT,
    solution_code TEXT,
    FOREIGN KEY (problem_id) REFERENCES problems(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (status_id) REFERENCES problem_status(id)
);

CREATE TABLE IF NOT EXISTS problem_status (
    id INT PRIMARY KEY,
    status varchar(16)
);
--


