CREATE TABLE Room (
    id INT NOT NULL AUTO_INCREMENT,
    capacity INT,
    PRIMARY KEY (id)
);

CREATE TABLE Appointment (
    id INT NOT NULL AUTO_INCREMENT,
    start_time DATETIME NOT NULL,
    duration TIME NOT NULL,
    location VARCHAR(20),
    reservation INT,
    canceled BOOLEAN,
    PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (reservation) REFERENCES Room(id)
);


CREATE TABLE Employee (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50),
    phone VARCHAR(16),
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE EmployeeGroup (
    id INT NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE Subgroup (
   child_id INT NOT NULL,
   parent_id INT NOT NULL,
   CONSTRAINT FOREIGN KEY (child_id) REFERENCES EmployeeGroup(id),
   CONSTRAINT FOREIGN KEY (parent_id) REFERENCES EmployeeGroup(id)
);

CREATE TABLE Member (
    employee_id INT NOT NULL,
    group_id INT NOT NULL,
    CONSTRAINT FOREIGN KEY (employee_id) REFERENCES Employee(id),
    CONSTRAINT FOREIGN KEY (group_id) REFERENCES EmployeeGroup(id)
);

CREATE TABLE Notification (
    id INT NOT NULL AUTO_INCREMENT,
    time DATETIME NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Invitation (
    employee_id INT NOT NULL,
    appointment_id INT NOT NULL,
    notification_id INT,
    attending BOOLEAN,
    creator BOOLEAN NOT NULL,
    hidden BOOLEAN NOT NULL,
    CONSTRAINT FOREIGN KEY (employee_id) REFERENCES Employee(id),
    CONSTRAINT FOREIGN KEY (appointment_id) REFERENCES Appointment(id),
    CONSTRAINT FOREIGN KEY (notification_id) REFERENCES Notification(id)
);

INSERT INTO Employee (name, email, password) VALUES
  ("Mathias Ose", "m@thiaso.se", "hunter2");
