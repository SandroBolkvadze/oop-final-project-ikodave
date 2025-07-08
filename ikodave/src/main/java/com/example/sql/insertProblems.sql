USE Ikodave;
INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    create_date,
    time_limit
) VALUES (
 'sum-of-two-numbers',
 'Given two integers a and b, output their sum.',
 1,
 NOW(),
 2000
);

INSERT INTO test_cases
(
     problem_id,
     problem_input,
     problem_output,
     test_number
) VALUES
(1, '1 2', '3', 1),
(1, '10 20', '30', 2),
(1, '-5 5', '0', 3),
(1, '100 200', '300', 4),
(1, '0 0', '0', 5),
(1, '-10 -20', '-30', 6),
(1, '123 456', '579', 7),
(1, '9999 1', '10000', 8),
(1, '-1000 500', '-500', 9),
(1, '2147483647 -1', '2147483646', 10);



INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    create_date,
    time_limit
) VALUES (
             'maximum-of-two-numbers',
             'Given two integers a and b, find and output the maximum of the two numbers.',
             1,
             NOW(),
             2000
         );

INSERT INTO test_cases
(
    problem_id,
    problem_input,
    problem_output,
    test_number
) VALUES
      (2, '5 3', '5', 1),
      (2, '10 20', '20', 2),
      (2, '-5 -3', '-3', 3),
      (2, '0 0', '0', 4),
      (2, '100 50', '100', 5);



INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    create_date,
    time_limit
) VALUES (
             'factorial-calculation',
             'Given a non-negative integer n, calculate and output n! (n factorial). Note: 0! = 1.',
             1,
             NOW(),
             2000
         );

INSERT INTO test_cases
(
    problem_id,
    problem_input,
    problem_output,
    test_number
) VALUES
      (3, '0', '1', 1),
      (3, '1', '1', 2),
      (3, '5', '120', 3),
      (3, '3', '6', 4),
      (3, '4', '24', 5);



INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    create_date,
    time_limit
) VALUES (
             'array-sum',
             'Given an array of n integers, calculate and output the sum of all elements.',
             1,
             NOW(),
             2000
         );

INSERT INTO test_cases
(
    problem_id,
    problem_input,
    problem_output,
    test_number
) VALUES
      (4, '3\n1 2 3', '6', 1),
      (4, '5\n1 2 3 4 5', '15', 2),
      (4, '1\n10', '10', 3),
      (4, '4\n-1 -2 -3 -4', '-10', 4),
      (4, '3\n0 0 0', '0', 5);



INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    create_date,
    time_limit
) VALUES (
             'find-maximum-in-array',
             'Given an array of n integers, find and output the maximum element.',
             1,
             NOW(),
             2000
         );

INSERT INTO test_cases
(
    problem_id,
    problem_input,
    problem_output,
    test_number
) VALUES
      (5, '3\n1 2 3', '3', 1),
      (5, '5\n5 2 8 1 9', '9', 2),
      (5, '1\n10', '10', 3),
      (5, '4\n-1 -5 -3 -2', '-1', 4),
      (5, '3\n0 0 0', '0', 5);



INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    create_date,
    time_limit
) VALUES (
             'palindrome-check',
             'Given a string, determine if it is a palindrome. A palindrome reads the same forwards and backwards. Consider only alphanumeric characters and ignore case.',
             2,
             NOW(),
             2000
         );

INSERT INTO test_cases
(
    problem_id,
    problem_input,
    problem_output,
    test_number
) VALUES
      (6, 'racecar', 'true', 1),
      (6, 'hello', 'false', 2),
      (6, 'A man a plan a canal Panama', 'true', 3),
      (6, '12321', 'true', 4),
      (6, 'abc', 'false', 5);



INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    create_date,
    time_limit
) VALUES (
             'two-sum',
             'Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target. You may assume that each input would have exactly one solution, and you may not use the same element twice.',
             2,
             NOW(),
             2000
         );

INSERT INTO test_cases
(
    problem_id,
    problem_input,
    problem_output,
    test_number
) VALUES
      (7, '4\n2 7 11 15\n9', '0 1', 1),
      (7, '3\n3 2 4\n6', '1 2', 2),
      (7, '2\n3 3\n6', '0 1', 3),
      (7, '5\n1 5 8 12 15\n20', '2 4', 4),
      (7, '4\n-1 -2 -3 -4\n-7', '2 3', 5);




INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    create_date,
    time_limit
) VALUES (
             'valid-parentheses',
             'Given a string s containing just the characters ''('', '')'', ''{'', ''}'', ''['' and '']'', determine if the input string is valid. An input string is valid if: 1) Open brackets must be closed by the same type of brackets. 2) Open brackets must be closed in the correct order.',
             2,
             NOW(),
             2000
         );

INSERT INTO test_cases
(
    problem_id,
    problem_input,
    problem_output,
    test_number
) VALUES
      (8, '()', 'true', 1),
      (8, '()[]{}', 'true', 2),
      (8, '(]', 'false', 3),
      (8, '([)]', 'false', 4),
      (8, '{[]}', 'true', 5);



INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    create_date,
    time_limit
) VALUES (
             'merge-sorted-arrays',
             'Given two sorted arrays nums1 and nums2, merge them into a single sorted array. The result should be sorted in ascending order.',
             2,
             NOW(),
             2000
         );

INSERT INTO test_cases
(
    problem_id,
    problem_input,
    problem_output,
    test_number
) VALUES
      (9, '3\n1 3 5\n3\n2 4 6', '1 2 3 4 5 6', 1),
      (9, '2\n1 2\n2\n3 4', '1 2 3 4', 2),
      (9, '0\n\n3\n1 2 3', '1 2 3', 3),
      (9, '2\n1 3\n0\n', '1 3', 4),
      (9, '3\n1 1 1\n2\n2 2', '1 1 1 2 2', 5);




INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    create_date,
    time_limit
) VALUES (
             'longest-substring-without-repeating-characters',
             'Given a string s, find the length of the longest substring without repeating characters.',
             3,
             NOW(),
             2000
         );

INSERT INTO test_cases
(
    problem_id,
    problem_input,
    problem_output,
    test_number
) VALUES
      (10, 'abcabcbb', '3', 1),
      (10, 'bbbbb', '1', 2),
      (10, 'pwwkew', '3', 3),
      (10, '', '0', 4),
      (10, 'abcdef', '6', 5);



INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    create_date,
    time_limit
) VALUES (
             'binary-tree-inorder-traversal',
             'Given the root of a binary tree, return the inorder traversal of its nodes'' values. Inorder traversal visits the left subtree, then the root, then the right subtree.',
             2,
             NOW(),
             2000
         );

INSERT INTO test_cases
(
    problem_id,
    problem_input,
    problem_output,
    test_number
) VALUES
      (11, '1,null,2,3', '1 3 2', 1),
      (11, '', '', 2),
      (11, '1', '1', 3),
      (11, '1,2,3,4,5', '4 2 5 1 3', 4),
      (11, '1,null,2,null,3', '1 2 3', 5);


INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    create_date,
    time_limit
) VALUES (
             'climbing-stairs',
             'You are climbing a staircase. It takes n steps to reach the top. Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?',
             1,
             NOW(),
             2000
         );

INSERT INTO test_cases
(
    problem_id,
    problem_input,
    problem_output,
    test_number
) VALUES
      (12, '2', '2', 1),
      (12, '3', '3', 2),
      (12, '4', '5', 3),
      (12, '1', '1', 4),
      (12, '5', '8', 5);


INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    create_date,
    time_limit
) VALUES (
             'reverse-string',
             'Write a function that reverses a string. The input string is given as an array of characters s. You must do this by modifying the input array in-place with O(1) extra memory.',
             1,
             NOW(),
             2000
         );

INSERT INTO test_cases
(
    problem_id,
    problem_input,
    problem_output,
    test_number
) VALUES
      (13, 'hello', 'olleh', 1),
      (13, 'world', 'dlrow', 2),
      (13, 'a', 'a', 3),
      (13, 'ab', 'ba', 4),
      (13, 'racecar', 'racecar', 5);



INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    create_date,
    time_limit
) VALUES (
             'valid-anagram',
             'Given two strings s and t, return true if t is an anagram of s, and false otherwise. An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.',
             1,
             NOW(),
             2000
         );

INSERT INTO test_cases
(
    problem_id,
    problem_input,
    problem_output,
    test_number
) VALUES
      (14, 'anagram\nnagaram', 'true', 1),
      (14, 'rat\ncar', 'false', 2),
      (14, 'listen\nsilent', 'true', 3),
      (14, 'hello\nworld', 'false', 4),
      (14, 'a\na', 'true', 5);



INSERT INTO problems (
    problem_title,
    problem_description,
    difficulty_id,
    create_date,
    time_limit
) VALUES (
             'best-time-to-buy-and-sell-stock',
             'You are given an array prices where prices[i] is the price of a given stock on the ith day. You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock. Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.',
             1,
             NOW(),
             2000
         );

INSERT INTO test_cases
(
    problem_id,
    problem_input,
    problem_output,
    test_number
) VALUES
      (15, '7 1 5 3 6 4', '5', 1),
      (15, '7 6 4 3 1', '0', 2),
      (15, '1 2 3 4 5', '4', 3),
      (15, '5 4 3 2 1', '0', 4),
      (15, '2 4 1', '2', 5);
