CREATE TABLE IF NOT EXISTS Ikodave.problems (
    id INT PRIMARY KEY,
    problem_name VARCHAR(100),
    problem_description TEXT,
    difficulty_level ENUM('easy', 'medium', 'hard') NOT NULL,
);

CREATE TABLE IF NOT EXISTS Ikodave.problem_solutions (
    id INT PRIMARY KEY,
    problem_id INT,
    user_id INT,
    solution_code TEXT,
    solution_language VARCHAR(50),
    FOREIGN KEY (problem_id) REFERENCES Ikodave.problems(id),
    FOREIGN KEY (user_id) REFERENCES Ikodave.users(id)
);