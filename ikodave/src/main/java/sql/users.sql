create table if not exsits Ikodave.users (
    id int auto_increment primary key,
    username varchar(50) not null unique,
    password varchar(255) not null,
)
