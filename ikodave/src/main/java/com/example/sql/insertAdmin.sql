USE Ikodave;
INSERT INTO users (role_id, username, password, register_date) VALUES
(
    1,
    'admin',
    '$2a$10$GZcnnxdMw8MYt4.cgqUkiuN3cWFkTLL.kYpB7o7sA.1V2Q5BYhj0e',
    NOW()
);

-- password = 'admin'