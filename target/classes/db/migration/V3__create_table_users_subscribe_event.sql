CREATE TABLE IF NOT EXISTS users_subscribe_event (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(75) NOT NULL ,
    activity_title varchar(50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE INDEX idx_user_email ON users_subscribe_event (user_email);

CREATE INDEX idx_activity_title ON users_subscribe_event (activity_title);