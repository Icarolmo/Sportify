CREATE TABLE IF NOT EXISTS events
(
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    activity_title varchar(50) NOT NULL,
    type varchar(50) NOT NULL,
    description varchar(250),
    localization varchar(50) NOT NULL,
    date date NOT NULL,
    start_hour timestamp NOT NULL,
    end_hour timestamp NOT NULL,
    privacy varchar(4) NOT NULL,
    number_of_person int,
    is_activity int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE INDEX idx_type ON events (type);

CREATE INDEX idx_activity_title ON events (activity_title);
