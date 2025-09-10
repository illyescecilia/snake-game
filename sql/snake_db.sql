create database SnakeGame;
create table SnakeGame.snake_highscores (
    id int primary key auto_increment,
    name varchar(255),
    score int,
    timestamp timestamp
);
