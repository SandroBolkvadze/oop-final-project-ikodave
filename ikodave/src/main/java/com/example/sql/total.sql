CREATE SCHEMA IF NOT EXISTS Ikodave;

USE Ikodave;


CREATE TABLE IF NOT EXISTS user_role
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(32)
    );

CREATE TABLE IF NOT EXISTS users
(
    id                      INT AUTO_INCREMENT PRIMARY KEY,
    role_id                 INT,
    username                VARCHAR(64)  NOT NULL UNIQUE,
    password                VARCHAR(256) NOT NULL,
    register_date           DATETIME NOT NULL,
    FOREIGN KEY (role_id)   REFERENCES user_role (id)
    );

CREATE TABLE IF NOT EXISTS problem_status
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    status      VARCHAR(32) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS problem_difficulty
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    difficulty VARCHAR(32) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS problem_topic
(
    id    INT AUTO_INCREMENT PRIMARY KEY,
    topic VARCHAR(32) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS submission_verdict
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    verdict VARCHAR(32) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS code_language
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    language VARCHAR(32) UNIQUE NOT NULL
    );

-- problems
CREATE TABLE IF NOT EXISTS problems
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    problem_title       VARCHAR(128) UNIQUE NOT NULL,
    problem_description TEXT NOT NULL,
    difficulty_id       INT,
    time_limit          BIGINT NOT NULL,
    memory_limit        BIGINT,
    input_spec          TEXT NOT NULL,
    output_spec         TEXT NOT NULL,
    create_date         DATETIME NOT NULL,
    FOREIGN KEY (difficulty_id) REFERENCES problem_difficulty (id)
    );

CREATE TABLE IF NOT EXISTS problem_many_to_many_topic
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    problem_id INT,
    topic_id   INT,
    FOREIGN KEY (problem_id) REFERENCES problems (id),
    FOREIGN KEY (topic_id) REFERENCES problem_topic (id)
    );

CREATE TABLE IF NOT EXISTS submissions
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    user_id             INT,
    problem_id          INT,
    verdict_id          INT,
    solution_code       TEXT,
    code_language_id    INT NOT NULL,
    time                BIGINT NOT NULL,
    memory              BIGINT,
    submit_date         DATETIME NOT NULL,
    log                 TEXT,
    FOREIGN KEY (problem_id) REFERENCES problems (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (verdict_id) REFERENCES submission_verdict (id),
    FOREIGN KEY (code_language_id) REFERENCES code_language (id)
    );

CREATE TABLE IF NOT EXISTS test_cases
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    problem_id          INT,
    problem_input       MEDIUMTEXT,
    problem_output      MEDIUMTEXT,
    test_number         INT,
    FOREIGN KEY (problem_id) REFERENCES problems (id)
    );



USE Ikodave;
INSERT INTO user_role (role) VALUES ('Admin');
INSERT INTO user_role (role) VALUES ('User');


USE Ikodave;
INSERT INTO problem_status (status) VALUES ('Solved');
INSERT INTO problem_status (status) VALUES ('Attempted');
INSERT INTO problem_status (status) VALUES ('Todo');

USE Ikodave;
INSERT INTO problem_difficulty (difficulty) VALUES ('Easy');
INSERT INTO problem_difficulty (difficulty) VALUES ('Medium');
INSERT INTO problem_difficulty (difficulty) VALUES ('Hard');
USE Ikodave;
INSERT INTO submission_verdict (verdict) VALUES ('Running');
INSERT INTO submission_verdict (verdict) VALUES ('Accepted');
INSERT INTO submission_verdict (verdict) VALUES ('Wrong Answer');
INSERT INTO submission_verdict (verdict) VALUES ('Time Limit Exceeded');
INSERT INTO submission_verdict (verdict) VALUES ('Memory Limit Exceeded');
INSERT INTO submission_verdict (verdict) VALUES ('Runtime Error');
INSERT INTO submission_verdict (verdict) VALUES ('Compilation Error');

USE Ikodave;
INSERT INTO code_language (language) VALUES ('Java');
INSERT INTO code_language (language) VALUES ('CPP');
INSERT INTO code_language (language) VALUES ('Python');
INSERT INTO code_language (language) VALUES ('C');

USE Ikodave;

INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    time_limit,
    memory_limit,
    input_spec,
    output_spec,
    create_date
) VALUES
-- 1
('sum-of-two-numbers',
 'Given two integers a and b, output their sum.',
 1,
 1000,
 128,
 'Two integers a and b separated by a space. Bounds: -2,000,000,000 ≤ a, b ≤ 2,000,000,000.',
 'A single integer, the sum of a and b.',
 NOW()),

-- 2
('maximum-of-two-numbers',
 'Given two integers a and b, find and output the maximum of the two numbers.',
 1,
 1000,
 128,
 'Two integers a and b separated by a space. Bounds: -2,000,000,000 ≤ a, b ≤ 2,000,000,000.',
 'A single integer, the maximum of a and b.',
 NOW()),

-- 3
('factorial-calculation',
 'Given a non-negative integer n, calculate and output n! (n factorial). Note: 0! = 1.',
 1,
 1000,
 128,
 'A single non-negative integer n. Bounds: 0 ≤ n ≤ 12.',
 'A single integer: n! (n factorial).',
 NOW()),

-- 4
('array-sum',
 'Given an array of n integers, calculate and output the sum of all elements.',
 1,
 1000,
 128,
 'First line: integer n (1 ≤ n ≤ 100). Second line: n integers separated by spaces, each -10,000 ≤ aᵢ ≤ 10,000.',
 'A single integer: the sum of the array.',
 NOW()),

-- 5
('find-maximum-in-array',
 'Given an array of n integers, find and output the maximum element.',
 1,
 2000,
 128,
 'First line: integer n (1 ≤ n ≤ 100). Second line: n integers separated by spaces, each -10,000 ≤ aᵢ ≤ 10,000.',
 'A single integer: the maximum element of the array.',
 NOW()),

-- 6
('climbing-stairs',
 'It takes n steps to reach the top. Each time you can climb 1 or 2 steps. How many distinct ways?',
 1,
 2000,
 128,
 'A single integer n (1 ≤ n ≤ 45).',
 'A single integer: number of distinct ways.',
 NOW()),

-- 7
('reverse-string',
 'Write a function that reverses a string in-place (array of chars), using O(1) extra memory.',
 1,
 2000,
 128,
 'A single string s of length 1 to 10⁵.',
 'The reversed string.',
 NOW()),

-- 8
('valid-anagram',
 'Given two strings s and t, return true if t is an anagram of s; false otherwise.',
 1,
 2000,
 128,
 'Two lines: s then t, each length 1 to 10⁵, lowercase letters.',
 'Output "true" or "false".',
 NOW()),

-- 9
('best-time-to-buy-and-sell-stock',
 'Given prices[i] as the stock price on day i, choose one day to buy and one later to sell for max profit; return 0 if none.',
 1,
 2000,
 128,
 'A single line of space‑separated integers prices (1 ≤ length ≤ 10⁵).',
 'A single integer: the maximum profit.',
 NOW()),

-- 10
('palindrome-check',
 'Given a string, determine if it is a palindrome. A palindrome reads the same forwards and backwards. Consider only alphanumeric characters and ignore case.',
 2,
 2000,
 128,
 'A single string s of up to 100 characters (letters, digits, spaces).',
 'Output "true" if s is a palindrome, "false" otherwise.',
 NOW()),

-- 11
('two-sum',
 'Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target. You may assume exactly one solution, and not reuse an element.',
 2,
 2000,
 128,
 'First line: integer n (2 ≤ n ≤ 100). Second line: n integers (‑1000 ≤ aᵢ ≤ 1000). Third line: integer target (‑2000 ≤ target ≤ 2000).',
 'Two integers: the indices (0‑based) of the numbers that add up to target.',
 NOW()),

-- 12
('valid-parentheses',
 'Given a string s containing just the characters ''('', '')'', ''{'', ''}'', ''['' and '']'', determine if it is valid by matching bracket type and order.',
 2,
 2000,
 128,
 'A single string s of length 1 to 1000 containing only the six bracket characters.',
 'Output "true" if s is valid, "false" otherwise.',
 NOW()),

-- 13
('merge-sorted-arrays',
 'Given two sorted arrays nums1 and nums2, merge them into one sorted array in ascending order.',
 2,
 2000,
 128,
 'First line: m and n. Second line: m sorted integers. Third line: n sorted integers.',
 'One line: m+n integers in ascending order.',
 NOW()),

-- 14
('binary-tree-inorder-traversal',
 'Given the root of a binary tree, return the inorder traversal of its node values.',
 2,
 2000,
 128,
 'A comma‑separated list with "null" for absent children.',
 'Values visited in inorder, space‑separated.',
 NOW()),

-- 15
('longest-substring-without-repeating-characters',
 'Given a string s, find the length of the longest substring without repeating characters.',
 3,
 2000,
 128,
 'A single string s of length 0 to 10⁵.',
 'A single integer: the length of the longest substring without duplicates.',
 NOW());

USE Ikodave;
INSERT INTO problem_topic (topic) VALUES ('Implementation');
INSERT INTO problem_topic (topic) VALUES ('Array');
INSERT INTO problem_topic (topic) VALUES ('Hash Table');
INSERT INTO problem_topic (topic) VALUES ('Graph');
INSERT INTO problem_topic (topic) VALUES ('Greedy');
INSERT INTO problem_topic (topic) VALUES ('String');
INSERT INTO problem_topic (topic) VALUES ('Two Pointers');
INSERT INTO problem_topic (topic) VALUES ('Dynamic Programming');
INSERT INTO problem_topic (topic) VALUES ('Stack');
INSERT INTO problem_topic (topic) VALUES ('Tree');
INSERT INTO problem_topic (topic) VALUES ('Binary Tree');
INSERT INTO problem_topic (topic) VALUES ('Depth-First Search');
INSERT INTO problem_topic (topic) VALUES ('Breadth-First Search');
INSERT INTO problem_topic (topic) VALUES ('Sorting');
INSERT INTO problem_topic (topic) VALUES ('Math');
INSERT INTO problem_topic (topic) VALUES ('Bit Manipulation');
INSERT INTO problem_topic (topic) VALUES ('Sliding Window');
INSERT INTO problem_topic (topic) VALUES ('Binary Search');
INSERT INTO problem_topic (topic) VALUES ('Linked List');
INSERT INTO problem_topic (topic) VALUES ('Recursion');
INSERT INTO problem_topic (topic) VALUES ('Backtracking');
INSERT INTO problem_topic (topic) VALUES ('Design');
INSERT INTO problem_topic (topic) VALUES ('Heap');
INSERT INTO problem_topic (topic) VALUES ('Queue');
INSERT INTO problem_topic (topic) VALUES ('Union Find');

USE Ikodave;

-- Problem 1: sum-of-two-numbers
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (1, 1);  -- Implementation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (1, 15); -- Math

-- Problem 2: maximum-of-two-numbers
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (2, 1);  -- Implementation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (2, 15); -- Math

-- Problem 3: factorial-calculation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (3, 1);  -- Implementation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (3, 15); -- Math
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (3, 20); -- Recursion

-- Problem 4: array-sum
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (4, 1);  -- Implementation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (4, 2);  -- Array
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (4, 15); -- Math

-- Problem 5: find-maximum-in-array
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (5, 1);  -- Implementation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (5, 2);  -- Array

-- Problem 6: climbing-stairs
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (6, 1);  -- Implementation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (6, 8);  -- Dynamic Programming
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (6, 15); -- Math

-- Problem 7: reverse-string
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (7, 1);  -- Implementation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (7, 6);  -- String

-- Problem 8: valid-anagram
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (8, 1);  -- Implementation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (8, 6);  -- String
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (8, 3);  -- Hash Table

-- Problem 9: best-time-to-buy-and-sell-stock
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (9, 1);   -- Implementation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (9, 2);   -- Array
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (9, 17);  -- Sliding Window

-- Problem 10: palindrome-check
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (10, 1);  -- Implementation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (10, 6);  -- String

-- Problem 11: two-sum
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (11, 1);  -- Implementation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (11, 2);  -- Array
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (11, 3);  -- Hash Table

-- Problem 12: valid-parentheses
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (12, 1);  -- Implementation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (12, 6);  -- String
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (12, 9);  -- Stack

-- Problem 13: merge-sorted-arrays
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (13, 1);  -- Implementation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (13, 2);  -- Array
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (13, 14); -- Sorting

-- Problem 14: binary-tree-inorder-traversal
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (14, 1);  -- Implementation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (14, 10); -- Tree
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (14, 11); -- Binary Tree
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (14, 12); -- Depth-First Search

-- Problem 15: longest-substring-without-repeating-characters
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (15, 1);  -- Implementation
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (15, 6);  -- String
INSERT INTO problem_many_to_many_topic (problem_id, topic_id) VALUES (15, 17); -- Sliding Window

USE ikodave_test;

INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '1 2', '3', 1);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '-1000 1000', '0', 2);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '-2000000000 2000000000', '0', 3);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '123456789 987654321', '1111111110', 4);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '999999999 1', '1000000000', 5);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '-999999999 -1', '-1000000000', 6);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '0 0', '0', 7);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '1000000 1000000', '2000000', 8);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '42 -17', '25', 9);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '-2000000000 -1', '-2000000001', 10);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '0 1234567890', '1234567890', 11);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '123 456', '579', 12);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '-1 -1', '-2', 13);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '999999999 999999999', '1999999998', 14);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '-123456789 -987654321', '-1111111110', 15);

INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '1 2', '2', 1);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '1000 1000', '1000', 2);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '-100 -200', '-100', 3);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '-1 0', '0', 4);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '2000000000 -2000000000', '2000000000', 5);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '-2000000000 2000000000', '2000000000', 6);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '0 0', '0', 7);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '999 1000', '1000', 8);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '1000 999', '1000', 9);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '123456789 987654321', '987654321', 10);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '-999999999 -1', '-1', 11);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '-1 -1', '-1', 12);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '0 999999999', '999999999', 13);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '42 42', '42', 14);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '2147483647 -2147483648', '2147483647', 15);



INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '0', '1', 1);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '1', '1', 2);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '2', '2', 3);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '3', '6', 4);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '4', '24', 5);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '5', '120', 6);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '6', '720', 7);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '7', '5040', 8);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '8', '40320', 9);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '9', '362880', 10);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '10', '3628800', 11);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '11', '39916800', 12);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '12', '479001600', 13);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '11', '39916800', 14);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '10', '3628800', 15);






INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '1\n1', '1', 1);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '3\n1 2 3', '6', 2);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '3\n0 0 0', '0', 3);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '3\n10 -5 -5', '0', 4);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '100\n1 2 3 ... 100', '5050', 5);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '4\n-1 -2 -3 -4', '-10', 6);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '100\n10000 10000 ...', '1000000', 7);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '100\n0 1 0 1 ...', '50', 8);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '4\n100 200 300 400', '1000', 9);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '100\n1 1 1 ...', '100', 10);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '100\n-10000 ...', '-1000000', 11);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '100\n100 99 98 ... 1', '5050', 12);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '20\n5 5 5 ...', '100', 13);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '100\n0 0 ... 9999', '9999', 14);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '100\n1 -1 1 -1 ...', '0', 15);






INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '1\n1', '1', 1);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '3\n1 2 3', '3', 2);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '3\n0 -1 -2', '0', 3);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '100\n10000 10000 ...', '10000', 4);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '100\n-50 -49 ... 49', '49', 5);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '100\n10 ... 9999', '9999', 6);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '100\n1 1 ...', '1', 7);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '3\n-10000 -9999 -8888', '-8888', 8);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '51\n-10 ... 0', '0', 9);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '100\n100 99 ... 1', '100', 10);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '100\n5 5 5 ...', '5', 11);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '4\n10 9 8 11', '11', 12);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '4\n7 8 9 10', '10', 13);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '4\n-1 -1 -1 -1', '-1', 14);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '100\n0 0 ... 1', '1', 15);




INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '1', '1', 1);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '2', '2', 2);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '3', '3', 3);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '4', '5', 4);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '5', '8', 5);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '6', '13', 6);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '10', '89', 7);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '15', '987', 8);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '20', '10946', 9);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '25', '121393', 10);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '30', '1346269', 11);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '35', '14930352', 12);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '40', '165580141', 13);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '44', '1134903170', 14);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '45', '1836311903', 15);





INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'a', 'a', 1);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'ab', 'ba', 2);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'abc', 'cba', 3);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'abcd', 'dcba', 4);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'palindrome', 'emordnilap', 5);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'racecar', 'racecar', 6);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'hello world', 'dlrow olleh', 7);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, '1234567890', '0987654321', 8);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'OpenAI GPT', 'TPG IAnepO', 9);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'reverse this sentence', 'ecnetnes siht esrever', 10);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'a b c d', 'd c b a', 11);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'longinputtexttest', 'tsettxettupnignol', 12);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'zzzzzzzzzz', 'zzzzzzzzzz', 13);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'ABCDE', 'EDCBA', 14);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, '9876543210abcdef', 'fedcba0123456789', 15);




INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'abc\ncba', 'true', 1);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'rat\ntar', 'true', 2);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'listen\nsilent', 'true', 3);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'triangle\nintegral', 'true', 4);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'apple\nppale', 'true', 5);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'anagram\nnagaram', 'true', 6);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'abcd\nabce', 'false', 7);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'aabbcc\nabcabc', 'true', 8);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'abcd\ndcba', 'true', 9);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'zzzz\zzzz', 'true', 10);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'hello\olelh', 'true', 11);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'aaa\aaa', 'true', 12);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'aaa\aab', 'false', 13);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'abc\abc', 'true', 14);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'abcd\abdc', 'true', 15);



INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES
                                                                                    (9, '1 2 3 4 5', '4', 1),
                                                                                    (9, '7 1 5 3 6 4', '5', 2),
                                                                                    (9, '7 6 4 3 1', '0', 3),
                                                                                    (9, '2 4 1', '2', 4),
                                                                                    (9, '3 3 3 3 3', '0', 5),
                                                                                    (9, '1 2', '1', 6),
                                                                                    (9, '2 1', '0', 7),
                                                                                    (9, '1 100', '99', 8),
                                                                                    (9, '100 1', '0', 9),
                                                                                    (9, '1 2 3 0 2', '3', 10),
                                                                                    (9, '5 4 3 2 1 2 3 4 5', '4', 11),
                                                                                    (9, '1', '0', 12),
                                                                                    (9, '10 22 5 75 65 80', '75', 13),
                                                                                    (9, '2 1 2 1 2 1 2', '1', 14),
                                                                                    (9, '1 2 4 2 5 7 2 4 9 0', '9', 15);







INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES
                                                                                    (10, 'racecar', 'true', 1),
                                                                                    (10, 'A man a plan a canal Panama', 'true', 2),
                                                                                    (10, 'No lemon, no melon', 'true', 3),
                                                                                    (10, 'hello', 'false', 4),
                                                                                    (10, 'Was it a car or a cat I saw', 'true', 5),
                                                                                    (10, '12321', 'true', 6),
                                                                                    (10, '123456', 'false', 7),
                                                                                    (10, 'a', 'true', 8),
                                                                                    (10, 'ab', 'false', 9),
                                                                                    (10, 'Able was I ere I saw Elba', 'true', 10),
                                                                                    (10, ' ', 'true', 11),
                                                                                    (10, '0P', 'false', 12),
                                                                                    (10, 'Madam, in Eden, I’m Adam', 'true', 13),
                                                                                    (10, 'step on no pets', 'true', 14),
                                                                                    (10, 'Eva, can I see bees in a cave?', 'true', 15);









INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES
                                                                                    (11, '4\n2 7 11 15\n9', '0 1', 1),
                                                                                    (11, '3\n3 2 4\n6', '1 2', 2),
                                                                                    (11, '2\n3 3\n6', '0 1', 3),
                                                                                    (11, '5\n1 2 3 4 5\n9', '3 4', 4),
                                                                                    (11, '6\n-1 -2 -3 -4 -5 -6\n-8', '2 5', 5),
                                                                                    (11, '3\n0 4 3\n3', '0 2', 6),
                                                                                    (11, '5\n2 5 5 11 15\n10', '1 2', 7),
                                                                                    (11, '2\n1000 1000\n2000', '0 1', 8),
                                                                                    (11, '4\n-3 4 3 90\n0', '0 2', 9),
                                                                                    (11, '5\n1 2 3 4 4\n8', '3 4', 10),
                                                                                    (11, '3\n1 2 3\n4', '0 2', 11),
                                                                                    (11, '2\n-1000 1000\n0', '0 1', 12),
                                                                                    (11, '3\n1 1 2\n3', '1 2', 13),
                                                                                    (11, '4\n2 7 11 15\n18', '1 2', 14),
                                                                                    (11, '2\n-1 1\n0', '0 1', 15);



INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES
                                                                                    (12, '()', 'true', 1),
                                                                                    (12, '()[]{}', 'true', 2),
                                                                                    (12, '(]', 'false', 3),
                                                                                    (12, '([)]', 'false', 4),
                                                                                    (12, '{[]}', 'true', 5),
                                                                                    (12, '(', 'false', 6),
                                                                                    (12, ')', 'false', 7),
                                                                                    (12, '', 'true', 8),
                                                                                    (12, '((({{{[[[]]]}}})))', 'true', 9),
                                                                                    (12, '([{}])', 'true', 10),
                                                                                    (12, '([)', 'false', 11),
                                                                                    (12, '([]{})', 'true', 12),
                                                                                    (12, '(((', 'false', 13),
                                                                                    (12, '[]]', 'false', 14),
                                                                                    (12, '{}[]()', 'true', 15);








INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES
                                                                                    (13, '3 3\n1 2 3\n2 5 6', '1 2 2 3 5 6', 1),
                                                                                    (13, '2 3\n1 2\n3 4 5', '1 2 3 4 5', 2),
                                                                                    (13, '1 1\n0\n0', '0 0', 3),
                                                                                    (13, '2 2\n2 4\n1 3', '1 2 3 4', 4),
                                                                                    (13, '3 2\n1 4 7\n2 6', '1 2 4 6 7', 5),
                                                                                    (13, '0 3\n\n1 2 3', '1 2 3', 6),
                                                                                    (13, '3 0\n1 2 3\n', '1 2 3', 7),
                                                                                    (13, '5 5\n1 3 5 7 9\n2 4 6 8 10', '1 2 3 4 5 6 7 8 9 10', 8),
                                                                                    (13, '1 2\n-1\n0 1', '-1 0 1', 9),
                                                                                    (13, '2 2\n0 0\n0 0', '0 0 0 0', 10),
                                                                                    (13, '2 2\n1 2\n1 2', '1 1 2 2', 11),
                                                                                    (13, '3 3\n-3 -2 -1\n1 2 3', '-3 -2 -1 1 2 3', 12),
                                                                                    (13, '1 1\n1000\n-1000', '-1000 1000', 13),
                                                                                    (13, '2 2\n-2 2\n-1 1', '-2 -1 1 2', 14),
                                                                                    (13, '4 4\n1 3 5 7\n2 4 6 8', '1 2 3 4 5 6 7 8', 15);



INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES
                                                                                    (14, '1,2,3', '2 1 3', 1),
                                                                                    (14, '1,null,2,3', '1 3 2', 2),
                                                                                    (14, '', '', 3),
                                                                                    (14, '1', '1', 4),
                                                                                    (14, '1,2', '2 1', 5),
                                                                                    (14, '1,null,2', '1 2', 6),
                                                                                    (14, '3,1,4,null,2', '1 2 3 4', 7),
                                                                                    (14, '5,3,6,2,4,null,null,1', '1 2 3 4 5 6', 8),
                                                                                    (14, '1,2,3,4,5,6,7', '4 2 5 1 6 3 7', 9),
                                                                                    (14, '1,null,2,null,3', '1 2 3', 10),
                                                                                    (14, '2,1,3', '1 2 3', 11),
                                                                                    (14, '1,2,null,3', '3 2 1', 12),
                                                                                    (14, '1,null,2,3,4', '1 3 4 2', 13),
                                                                                    (14, '1,2,3,null,null,4,5', '2 1 4 3 5', 14),
                                                                                    (14, '1,2,3,4,null,null,5', '4 2 1 3 5', 15);





INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES
                                                                                    (15, 'abcabcbb', '3', 1),
                                                                                    (15, 'bbbbb', '1', 2),
                                                                                    (15, 'pwwkew', '3', 3),
                                                                                    (15, '', '0', 4),
                                                                                    (15, ' ', '1', 5),
                                                                                    (15, 'au', '2', 6),
                                                                                    (15, 'dvdf', '3', 7),
                                                                                    (15, 'anviaj', '5', 8),
                                                                                    (15, 'tmmzuxt', '5', 9),
                                                                                    (15, 'abba', '2', 10),
                                                                                    (15, 'abcdefg', '7', 11),
                                                                                    (15, 'aab', '2', 12),
                                                                                    (15, 'abcabcbbabc', '3', 13),
                                                                                    (15, 'a', '1', 14),
                                                                                    (15, 'abccba', '3', 15);
USE Ikodave;
INSERT INTO users (role_id, username, password, register_date) VALUES
    (
        1,
        'admin',
        '$2a$10$GZcnnxdMw8MYt4.cgqUkiuN3cWFkTLL.kYpB7o7sA.1V2Q5BYhj0e',
        NOW()
    );

-- password = 'admin'