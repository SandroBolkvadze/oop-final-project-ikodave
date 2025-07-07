USE ikodave;
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






