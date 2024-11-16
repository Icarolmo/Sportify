CREATE TABLE IF NOT EXISTS events
(
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    activity_title varchar(50) NOT NULL,
    creator_mail varchar(75) NOT NULL,
    type varchar(50) NOT NULL,
    description varchar(250),
    localization varchar(50) NOT NULL,
    date date NOT NULL,
    start_hour TIME NOT NULL,
    end_hour TIME NOT NULL,
    subscribes int,
    max_subscribes int,
    accept_subscribes int,
    status int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE INDEX idx_creator_mail ON events (creator_mail);

CREATE INDEX idx_date ON events (date);

CREATE INDEX idx_activity_title ON events (activity_title);
