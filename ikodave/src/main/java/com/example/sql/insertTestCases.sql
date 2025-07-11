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