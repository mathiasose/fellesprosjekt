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
    description VARCHAR(140),
    canceled BOOLEAN,
    PRIMARY KEY (id)
);

CREATE TABLE Reservation (
    appointment_id INT NOT NULL,
    room_id INT NOT NULL,
    PRIMARY KEY (appointment_id, room_id),
    CONSTRAINT reservation_fk_appointment FOREIGN KEY (appointment_id) REFERENCES Appointment(id),
    CONSTRAINT reservation_fk_room FOREIGN KEY (room_id) REFERENCES Room(id)
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
   PRIMARY KEY (child_id, parent_id),
   CONSTRAINT subgroup_fk_child FOREIGN KEY (child_id) REFERENCES EmployeeGroup(id),
   CONSTRAINT subgroup_fk_parent FOREIGN KEY (parent_id) REFERENCES EmployeeGroup(id)
);

CREATE TABLE Member (
    employee_id INT NOT NULL,
    group_id INT NOT NULL,
    PRIMARY KEY (employee_id, group_id),
    CONSTRAINT member_fk_employee FOREIGN KEY (employee_id) REFERENCES Employee(id),
    CONSTRAINT member_fk_group FOREIGN KEY (group_id) REFERENCES EmployeeGroup(id)
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
    PRIMARY KEY (employee_id, appointment_id),
    CONSTRAINT invitation_fk_employee FOREIGN KEY (employee_id) REFERENCES Employee(id),
    CONSTRAINT invitation_fk_appointment FOREIGN KEY (appointment_id) REFERENCES Appointment(id),
    CONSTRAINT invitation_fk_notification FOREIGN KEY (notification_id) REFERENCES Notification(id)
);

INSERT INTO Employee (name, email, password) VALUES
  ("Knut Knutsen", "test@epost.com", "hunter99");
INSERT INTO Employee (name, email, password) VALUES
  ("Mathias Ose", "m@thiaso.se", "hunter2");
INSERT INTO Employee (name, email, password) VALUES
  ("Ken Lie", "kensivalie@gmail.com", "hunter6");
insert into Appointment(start_time, duration, location) values
    ('2014-06-18 12:00:00', '02:01:01', 'Scrum');
insert into Appointment(start_time, duration, location) values
    ('2014-11-18 12:00:00', '00:31:01', 'Meeting');
