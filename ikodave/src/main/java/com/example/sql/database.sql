-- Assume:
-- users: alice = 1, bob = 2
-- problem: Two Sum = 1
-- verdicts: Accepted = 1, Wrong Answer = 2
-- code_language: Java = 1

INSERT INTO submissions (user_id, problem_id, verdict_id, solution_code, code_language_id, time, memory, submit_date, log)
VALUES
    (1, 1, 1, 'public class Solution { }', 1, 150, 64, NOW(), 'Passed all tests'),
    (1, 1, 2, 'public class Solution { }', 1, 120, 70, NOW(), 'Failed on test case 2'),
    (2, 1, 1, 'public class Solution { }', 1, 140, 60, NOW(), 'Passed all tests');
