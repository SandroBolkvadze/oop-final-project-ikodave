INSERT INTO problem_difficulty (id, difficulty) VALUES (1, 'Easy');
INSERT INTO problem_difficulty (id, difficulty) VALUES (2, 'MEDIUM');
INSERT INTO problem_difficulty (id, difficulty) VALUES(3, 'HARD');

INSERT INTO problem_topic (id, topic) VALUES (1, 'dp');
INSERT INTO problem_topic (id, topic) VALUES (2, 'greedy');
INSERT INTO problem_topic (id, topic) VALUES (3, 'graphs');
INSERT INTO problem_topic (id, topic) VALUES (4, 'trees');

INSERT INTO problem_status (id, status) VALUES (1, 'ACCEPTED');
INSERT INTO problem_status (id, status) VALUES (2, 'WRONG');
INSERT INTO problem_status (id, status) VALUES (3, 'PENDING');

INSERT INTO problems (id, problem_title, problem_description, difficulty_id) VALUES (1, 'Ants', 'there are n nodes', 1);
INSERT INTO problems (id, problem_title, problem_description, difficulty_id) VALUES (2, 'xorificator', 'xors of x y', 2);
INSERT INTO problems (id, problem_title, problem_description, difficulty_id) VALUES (3, 'cool artem', 'there are n elements', 3);
INSERT INTO problems (id, problem_title, problem_description, difficulty_id) VALUES (4, 'nice', 'there are n strings', 2);
INSERT INTO problems (id, problem_title, problem_description, difficulty_id) VALUES (5, 'hard', 'there are n graph', 2);

INSERT INTO problem_many_to_many_topic(id, problem_id, topic_id) VALUES(1, 1,1);
INSERT INTO problem_many_to_many_topic(id, problem_id, topic_id) VALUES(2, 1,2);
INSERT INTO problem_many_to_many_topic(id, problem_id, topic_id) VALUES(3, 1,4);
INSERT INTO problem_many_to_many_topic(id, problem_id, topic_id) VALUES(4, 2,3);
INSERT INTO problem_many_to_many_topic(id, problem_id, topic_id) VALUES(5, 3,1);
INSERT INTO problem_many_to_many_topic(id, problem_id, topic_id) VALUES(6, 3,3);
INSERT INTO problem_many_to_many_topic(id, problem_id, topic_id) VALUES(7, 4,1);
INSERT INTO problem_many_to_many_topic(id, problem_id, topic_id) VALUES(8, 4,2);
INSERT INTO problem_many_to_many_topic(id, problem_id, topic_id) VALUES(9, 5,2);
INSERT INTO problem_many_to_many_topic(id, problem_id, topic_id) VALUES(10, 5,3);
INSERT INTO problem_many_to_many_topic(id, problem_id, topic_id) VALUES(12, 5,4);
INSERT INTO problem_many_to_many_topic(id, problem_id, topic_id) VALUES(13, 5,1);
INSERT INTO users (id, username, password) VALUES(1, 'bolkvadze','sbolk23');
INSERT INTO users (id, username, password) VALUES(2, 'losaberidze','slosa23');
INSERT INTO users (id, username, password) VALUES(3, 'endeladze','kende23');
INSERT INTO users (id, username, password) VALUES(4, 'metreveli','nmetr23');
INSERT INTO submissions (id, user_id, problem_id, status_id, solution_code) VALUES(1, 1, 1,1,'+');
INSERT INTO submissions (id, user_id, problem_id, status_id, solution_code) VALUES(2, 1, 2,2,'++');
INSERT INTO submissions (id, user_id, problem_id, status_id, solution_code) VALUES(3, 1, 3,2,'+++');
INSERT INTO submissions (id, user_id, problem_id, status_id, solution_code) VALUES(4, 1, 5,1,'+.');
INSERT INTO submissions (id, user_id, problem_id, status_id, solution_code) VALUES(5, 2, 1,2,'-.');
INSERT INTO submissions (id, user_id, problem_id, status_id, solution_code) VALUES(6, 2, 2,1,'...');
INSERT INTO submissions (id, user_id, problem_id, status_id, solution_code) VALUES(7, 2, 3,2,'...');
INSERT INTO submissions (id, user_id, problem_id, status_id, solution_code) VALUES(8, 3, 1,1,'...');
INSERT INTO submissions (id, user_id, problem_id, status_id, solution_code) VALUES(9, 3, 2,1,'...');
INSERT INTO submissions (id, user_id, problem_id, status_id, solution_code) VALUES(10, 3, 4,2,'...');
INSERT INTO submissions (id, user_id, problem_id, status_id, solution_code) VALUES(11, 4, 1,1,'...');
INSERT INTO submissions (id, user_id, problem_id, status_id, solution_code) VALUES(12, 4, 5,1,'...');
INSERT INTO submissions (id, user_id, problem_id, status_id, solution_code) VALUES(13, 4, 4,2,'...');
