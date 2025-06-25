USE Ikodave;

-- 1) seed user_rank
INSERT INTO user_rank (id, user_rank)
VALUES (1, 'Member'),
       (2, 'Moderator'),
       (3, 'Admin');

-- 2) difficulties
INSERT INTO problem_difficulty (id, difficulty)
VALUES (1, 'Easy'),
       (2, 'MEDIUM'),
       (3, 'HARD');

-- 3) topics
INSERT INTO problem_topic (id, topic)
VALUES (1, 'dp'),
       (2, 'greedy'),
       (3, 'graphs'),
       (4, 'trees');

-- 4) statuses
INSERT INTO problem_status (id, status)
VALUES (1, 'ACCEPTED'),
       (2, 'WRONG'),
       (3, 'PENDING'),
       (4, 'TO-DO');

-- 5) problems (now with create_date and explicit time_limit)
INSERT INTO problems (id, problem_title, problem_description, difficulty_id, create_date, time_limit)
VALUES (1, 'Ants', 'there are n nodes', 1, '2025-06-20', 20000),
       (2, 'xorificator', 'xors of x y', 2, '2025-06-21', 20000),
       (3, 'cool artem', 'there are n elements', 3, '2025-06-22', 20000),
       (4, 'nice', 'there are n strings', 2, '2025-06-23', 20000),
       (5, 'hard', 'there are n graph', 2, '2025-06-24', 20000);

-- 6) problem ↔ topic mapping
INSERT INTO problem_many_to_many_topic (id, problem_id, topic_id)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 1, 4),
       (4, 2, 3),
       (5, 3, 1),
       (6, 3, 3),
       (7, 4, 1),
       (8, 4, 2),
       (9, 5, 2),
       (10, 5, 3),
       (12, 5, 4),
       (13, 5, 1);

-- 7) users (now with rank_id and register_date)
INSERT INTO users (id, rank_id, username, password, register_date)
VALUES (1, 1, 'bolkvadze', 'sbolk23', '2025-06-10'),
       (2, 1, 'losaberidze', 'slosa23', '2025-06-11'),
       (3, 2, 'endeladze', 'kende23', '2025-06-12'),
       (4, 3, 'metreveli', 'nmetr23', '2025-06-13');

-- 8) submissions (now with submit_date and empty log)
INSERT INTO submissions (id, user_id, problem_id, status_id, solution_code, submit_date, log)
VALUES (1, 1, 1, 1, '+', '2025-06-25', ''),
       (2, 1, 2, 2, '++', '2025-06-25', ''),
       (3, 1, 3, 2, '+++', '2025-06-25', ''),
       (4, 1, 5, 1, '+.', '2025-06-25', ''),
       (5, 2, 1, 2, '-.', '2025-06-25', ''),
       (6, 2, 2, 1, '...', '2025-06-25', ''),
       (7, 2, 3, 2, '...', '2025-06-25', ''),
       (8, 3, 1, 1, '...', '2025-06-25', ''),
       (9, 3, 2, 1, '...', '2025-06-25', ''),
       (10, 3, 4, 2, '...', '2025-06-25', ''),
       (11, 4, 1, 1, '...', '2025-06-25', ''),
       (12, 4, 5, 1, '...', '2025-06-25', ''),
       (13, 4, 4, 2, '...', '2025-06-25', '');