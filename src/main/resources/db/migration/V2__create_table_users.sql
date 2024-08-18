CREATE TABLE users (
    login VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    role INTEGER NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    second_name VARCHAR(50),
    email VARCHAR(100) NOT NULL,
    course_name INTEGER NOT NULL,
    current_semester INTEGER NOT NULL,
);