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

USE Ikodave;

USE Ikodave;

-- Additional test cases for Problem 1: sum-of-two-numbers
-- Edge cases and boundary conditions
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '0 0', '0', 16);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '2147483647 1', '2147483648', 17);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '-2147483648 -1', '-2147483649', 18);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '2147483647 2147483647', '4294967294', 19);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '-2147483648 -2147483648', '-4294967296', 20);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '1000000000 1000000000', '2000000000', 21);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '-1000000000 -1000000000', '-2000000000', 22);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '999999999 1', '1000000000', 23);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '-999999999 -1', '-1000000000', 24);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (1, '0 2147483647', '2147483647', 25);

-- Additional test cases for Problem 2: maximum-of-two-numbers
-- Edge cases and boundary conditions
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '2147483647 2147483646', '2147483647', 16);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '-2147483648 -2147483647', '-2147483647', 17);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '2147483647 -2147483648', '2147483647', 18);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '-2147483648 2147483647', '2147483647', 19);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '0 2147483647', '2147483647', 20);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '0 -2147483648', '0', 21);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '2147483647 0', '2147483647', 22);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '-2147483648 0', '0', 23);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '1000000000 999999999', '1000000000', 24);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (2, '-1000000000 -999999999', '-999999999', 25);

-- Additional test cases for Problem 3: factorial-calculation
-- Edge cases and boundary conditions
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '0', '1', 16);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '1', '1', 17);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '2', '2', 18);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '3', '6', 19);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '4', '24', 20);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '5', '120', 21);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '6', '720', 22);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '7', '5040', 23);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '8', '40320', 24);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '9', '362880', 25);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '10', '3628800', 26);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '11', '39916800', 27);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (3, '12', '479001600', 28);

-- Additional test cases for Problem 4: array-sum
-- Edge cases with various array sizes and values
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '1\n0', '0', 16);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '1\n10000', '10000', 17);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '1\n-10000', '-10000', 18);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '2\n5000 -5000', '0', 19);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '2\n-5000 5000', '0', 20);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '5\n1 2 3 4 5', '15', 21);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '5\n-1 -2 -3 -4 -5', '-15', 22);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '10\n1 1 1 1 1 1 1 1 1 1', '10', 23);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '10\n-1 -1 -1 -1 -1 -1 -1 -1 -1 -1', '-10', 24);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (4, '50\n1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50', '1275', 25);

-- Additional test cases for Problem 5: find-maximum-in-array
-- Edge cases with various array sizes and values
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '1\n0', '0', 16);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '1\n10000', '10000', 17);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '1\n-10000', '-10000', 18);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '2\n-10000 10000', '10000', 19);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '2\n10000 -10000', '10000', 20);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '5\n1 2 3 4 5', '5', 21);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '5\n5 4 3 2 1', '5', 22);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '5\n-5 -4 -3 -2 -1', '-1', 23);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '10\n1 2 3 4 5 6 7 8 9 10', '10', 24);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '10\n10 9 8 7 6 5 4 3 2 1', '10', 25);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (5, '50\n1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50', '50', 26);

-- Additional test cases for Problem 6: climbing-stairs
-- Edge cases and boundary conditions
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '1', '1', 16);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '2', '2', 17);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '3', '3', 18);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '4', '5', 19);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '5', '8', 20);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '6', '13', 21);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '7', '21', 22);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '8', '34', 23);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '9', '55', 24);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '10', '89', 25);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '11', '144', 26);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '12', '233', 27);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '13', '377', 28);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '14', '610', 29);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (6, '15', '987', 30);

-- Additional test cases for Problem 7: reverse-string
-- Edge cases with various string types and lengths
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, '', '', 16);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'x', 'x', 17);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'xx', 'xx', 18);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'xyz', 'zyx', 19);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, '12345', '54321', 20);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'abcdefghij', 'jihgfedcba', 21);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, '!@#$%^&*()', ')(*&^%$#@!', 22);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'a b c d e', 'e d c b a', 23);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'test case', 'esac tset', 24);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'A1B2C3D4E5', '5E4D3C2B1A', 25);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'racecar', 'racecar', 26);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'deed', 'deed', 27);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'noon', 'noon', 28);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'level', 'level', 29);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (7, 'civic', 'civic', 30);

-- Additional test cases for Problem 8: valid-anagram
-- Edge cases with various string combinations
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, '\n', 'true', 16);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'a\na', 'true', 17);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'ab\nba', 'true', 18);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'abc\ncba', 'true', 19);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'abcd\ndcba', 'true', 20);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'a\nb', 'false', 21);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'ab\nac', 'false', 22);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'abc\nabd', 'false', 23);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'aab\naba', 'true', 24);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'aab\nabb', 'false', 25);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'aaa\naaa', 'true', 26);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'aaa\naab', 'false', 27);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'abab\nbaba', 'true', 28);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'abab\nabac', 'false', 29);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (8, 'abcdef\nfedcba', 'true', 30);

-- Additional test cases for Problem 9: best-time-to-buy-and-sell-stock
-- Edge cases with various price patterns
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (9, '1', '0', 16);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (9, '1 2', '1', 17);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (9, '2 1', '0', 18);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (9, '1 1 1 1 1', '0', 19);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (9, '1 2 3 4 5', '4', 20);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (9, '5 4 3 2 1', '0', 21);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (9, '1 5 2 8 3', '7', 22);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (9, '3 2 6 5 0 3', '4', 23);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (9, '2 4 1', '2', 24);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (9, '7 6 4 3 1', '0', 25);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (9, '1 2 3 0 2', '3', 26);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (9, '5 4 3 2 1 2 3 4 5', '4', 27);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (9, '1 100', '99', 28);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (9, '100 1', '0', 29);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (9, '1 2 4 2 5 7 2 4 9 0', '9', 30);

-- Additional test cases for Problem 10: palindrome-check
-- Edge cases with various string types and formats
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (10, '', 'true', 16);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (10, 'a', 'true', 17);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (10, 'ab', 'false', 18);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (10, 'aa', 'true', 19);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (10, 'aba', 'true', 20);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (10, 'abc', 'false', 21);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (10, 'Aba', 'true', 22);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (10, 'aBa', 'true', 23);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (10, 'a b a', 'true', 24);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (10, 'a,b,a', 'true', 25);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (10, 'a1a', 'true', 26);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (10, '1a1', 'true', 27);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (10, '12321', 'true', 28);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (10, '12345', 'false', 29);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (10, 'a1b2c3c2b1a', 'true', 30);

-- Additional test cases for Problem 11: two-sum
-- Edge cases with various array sizes and target values
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (11, '2\n1 1\n2', '0 1', 16);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (11, '2\n0 0\n0', '0 1', 17);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (11, '3\n1 2 3\n4', '0 2', 18);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (11, '3\n1 2 3\n5', '1 2', 19);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (11, '4\n1 2 3 4\n7', '2 3', 20);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (11, '4\n1 2 3 4\n6', '1 3', 21);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (11, '5\n1 2 3 4 5\n9', '3 4', 22);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (11, '5\n1 2 3 4 5\n8', '2 4', 23);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (11, '6\n-1 -2 -3 -4 -5 -6\n-8', '2 5', 24);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (11, '6\n-1 -2 -3 -4 -5 -6\n-10', '1 5', 25);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (11, '3\n0 4 3\n3', '0 2', 26);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (11, '3\n0 4 3\n7', '1 2', 27);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (11, '5\n2 5 5 11 15\n10', '1 2', 28);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (11, '5\n2 5 5 11 15\n16', '3 4', 29);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (11, '2\n1000 1000\n2000', '0 1', 30);

-- Additional test cases for Problem 12: valid-parentheses
-- Edge cases with various bracket combinations
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (12, '', 'true', 16);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (12, '(', 'false', 17);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (12, ')', 'false', 18);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (12, '()', 'true', 19);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (12, '[]', 'true', 20);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (12, '{}', 'true', 21);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (12, '()[]{}', 'true', 22);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (12, '(]', 'false', 23);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (12, '([)]', 'false', 24);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (12, '{[]}', 'true', 25);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (12, '(((', 'false', 26);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (12, ')))', 'false', 27);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (12, '[[[', 'false', 28);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (12, ']]]', 'false', 29);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (12, '{{{', 'false', 30);

-- Additional test cases for Problem 13: merge-sorted-arrays
-- Edge cases with various array sizes and combinations
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (13, '0 0\n\n', '', 16);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (13, '1 0\n1\n', '1', 17);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (13, '0 1\n\n1', '1', 18);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (13, '1 1\n1\n1', '1 1', 19);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (13, '2 1\n1 2\n3', '1 2 3', 20);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (13, '1 2\n1\n2 3', '1 2 3', 21);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (13, '3 2\n1 3 5\n2 4', '1 2 3 4 5', 22);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (13, '2 3\n1 3\n2 4 6', '1 2 3 4 6', 23);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (13, '4 4\n1 3 5 7\n2 4 6 8', '1 2 3 4 5 6 7 8', 24);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (13, '3 3\n-3 -2 -1\n1 2 3', '-3 -2 -1 1 2 3', 25);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (13, '2 2\n0 0\n0 0', '0 0 0 0', 26);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (13, '2 2\n1 2\n1 2', '1 1 2 2', 27);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (13, '1 1\n1000\n-1000', '-1000 1000', 28);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (13, '2 2\n-2 2\n-1 1', '-2 -1 1 2', 29);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (13, '4 4\n1 3 5 7\n2 4 6 8', '1 2 3 4 5 6 7 8', 30);

-- Additional test cases for Problem 14: binary-tree-inorder-traversal
-- Edge cases with various tree structures
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (14, '', '', 16);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (14, '1', '1', 17);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (14, '1,2', '2 1', 18);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (14, '1,null,2', '1 2', 19);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (14, '1,2,3', '2 1 3', 20);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (14, '1,null,2,null,3', '1 2 3', 21);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (14, '1,2,null,3', '3 2 1', 22);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (14, '1,2,3,4,5', '4 2 5 1 3', 23);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (14, '1,2,3,4,5,6,7', '4 2 5 1 6 3 7', 24);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (14, '1,2,3,4,5,6,7,8,9', '8 4 9 2 5 1 6 3 7', 25);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (14, '1,null,2,null,3,null,4', '1 2 3 4', 26);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (14, '1,2,null,3,null,4', '4 3 2 1', 27);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (14, '1,2,3,4,null,null,5', '4 2 1 3 5', 28);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (14, '1,2,3,null,4,5,6', '2 4 1 5 3 6', 29);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (14, '1,2,3,4,5,6,7,8,9,10,11,12,13,14,15', '8 4 9 2 10 5 11 1 12 6 13 3 14 7 15', 30);

-- Additional test cases for Problem 15: longest-substring-without-repeating-characters
-- Edge cases with various string patterns
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (15, '', '0', 16);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (15, 'a', '1', 17);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (15, 'aa', '1', 18);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (15, 'ab', '2', 19);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (15, 'abc', '3', 20);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (15, 'abca', '3', 21);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (15, 'abcabc', '3', 22);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (15, 'pwwkew', '3', 23);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (15, 'bbbbb', '1', 24);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (15, 'abcabcbb', '3', 25);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (15, 'abcdef', '6', 26);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (15, 'abcdefa', '6', 27);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (15, 'abcdefg', '7', 28);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (15, 'aab', '2', 29);
INSERT INTO test_cases (problem_id, problem_input, problem_output, test_number) VALUES (15, 'abccba', '3', 30);
USE Ikodave;
INSERT INTO users (role_id, username, password, register_date) VALUES
    (
        1,
        'admin',
        '$2a$10$GZcnnxdMw8MYt4.cgqUkiuN3cWFkTLL.kYpB7o7sA.1V2Q5BYhj0e',
        NOW()
    );

-- password = 'admin'