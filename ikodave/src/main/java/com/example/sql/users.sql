create table if not exists Ikodave.users (
    id int auto_increment primary key,
    username varchar(50) not null unique,
    password varchar(255) not null
)
