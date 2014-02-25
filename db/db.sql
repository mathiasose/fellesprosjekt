CREATE TABLE Person (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50),
    phone VARCHAR(16),
    epost VARCHAR(50) UNIQUE NOT NULL,
    PRIMARY KEY(id);
);

CREATE TABLE Employee (
    constraint Employee_fk references Person(id),
    password VARCHAR(20) NOT NULL -- security hole anyone?
);

CREATE TABLE Non-employee (
    constraint Non-employee_fk references Person(id)
);

CREATE TABLE GGroup (
    id INT NOT NULL AUTO_INCREMENT
);

CREATE TABlE Appointment (
    id INT NOT NULL AUTO_INCREMENT,
    start_time DATETIME2 NOT NULL,
    duration TIME NOT NULL,
    location VARCHAR(20),
    -- status?
);

CREATE TABLE Calendar (
    constraint Calendar_Employee references ?,
    constraint Calendar_Appointment references Appointment(id)
);

CREATE TABLE Notification (

);

CREATE TABLE Room (
    id INT NOT NULL AUTO_INCREMENT,
    capaticy INT
);


