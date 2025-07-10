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
