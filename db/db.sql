CREATE TABLE Room (
    id INT NOT NULL AUTO_INCREMENT,
    capacity INT,
    PRIMARY KEY (id)
);

CREATE TABLE Appointment (
    id INT NOT NULL AUTO_INCREMENT,
    start_time DATETIME NOT NULL,
    duration INT NOT NULL,
    location VARCHAR(20),
    description VARCHAR(140),
    canceled BOOLEAN,
    PRIMARY KEY (id)
);

CREATE TABLE Reservation (
    appointment_id INT NOT NULL,
    room_id INT NOT NULL,
    PRIMARY KEY (appointment_id, room_id),
    CONSTRAINT reservation_fk_appointment
        FOREIGN KEY (appointment_id)
        REFERENCES Appointment(id)
        ON DELETE CASCADE,
    CONSTRAINT reservation_fk_room
        FOREIGN KEY (room_id)
        REFERENCES Room(id)
        ON DELETE CASCADE
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
   CONSTRAINT subgroup_fk_child
        FOREIGN KEY (child_id)
        REFERENCES EmployeeGroup(id),
   CONSTRAINT subgroup_fk_parent
        FOREIGN KEY (parent_id)
        REFERENCES EmployeeGroup(id)
);

CREATE TABLE Member (
    employee_id INT NOT NULL,
    group_id INT NOT NULL,
    PRIMARY KEY (employee_id, group_id),
    CONSTRAINT member_fk_employee
        FOREIGN KEY (employee_id)
        REFERENCES Employee(id),
    CONSTRAINT member_fk_group
        FOREIGN KEY (group_id)
        REFERENCES EmployeeGroup(id)
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
    CONSTRAINT invitation_fk_employee
        FOREIGN KEY (employee_id)
        REFERENCES Employee(id),
    CONSTRAINT invitation_fk_appointment
        FOREIGN KEY (appointment_id)
        REFERENCES Appointment(id)
        ON DELETE CASCADE,
    CONSTRAINT invitation_fk_notification
        FOREIGN KEY (notification_id)
        REFERENCES Notification(id)
        ON DELETE CASCADE
);

INSERT INTO Employee (name, email, password) VALUES
    ("Knut Knutsen", "test@epost.com", "hunter99"),
    ("Mathias Ose", "m@thiaso.se", "hunter2"),
    ("Ken Lie", "kensivalie@gmail.com", "hunter6"),
    ("Fredrik Tørnvall", "freboto@gmail.com", "testtest"),
    ("Jens Jensen", "jjens@gmail.com", "hunter5"),
    ("Frode Thorsen", "thorsen@gmail.com", "hunter9");

insert into Appointment(start_time, duration, location, description, canceled) values
    ('2014-03-18 12:13:00', 120, '3. etg', 'Scrum møte med gruppe 4', '0'),
    ('2014-03-18 08:00:00', 30, 'Kontoret', 'Regional møte', '0'),
    ('2014-03-19 08:30:00', 30, 'Kontoret', 'Scrum status', '0'),
    ('2014-03-19 10:30:00', 30, 'Kontoret', 'Planlegging av reise', '0'),
    ('2014-03-19 12:00:00', 30, 'Kantina', 'Lunch', '0'),
    ('2014-03-20 08:30:00', 30, 'Kontoret', 'Scrum status', '0'),
    ('2014-03-21 08:30:00', 30, 'Kontoret', 'Scrum status', '0'),
    ('2014-03-22 08:30:00', 30, 'Kontoret', 'Scrum status', '0'),
    ('2014-04-01 08:30:00', 30, 'Kontoret', 'Scrum status', '0'),
    ('2014-04-02 08:30:00', 30, 'Kontoret', 'Scrum status', '0'),
    ('2014-04-03 08:30:00', 30, 'Kontoret', 'Scrum status', '0'),
    ('2014-04-04 08:30:00', 30, 'Kontoret', 'Scrum status', '0'),
    ('2014-04-05 08:30:00', 30, 'Kontoret', 'Scrum status', '0');

insert into Invitation (employee_id, appointment_id, creator, hidden) values
    (1, 1, 1, 0),
    (1, 3, 0, 0),
    (3, 1, 0, 0),
    (3, 3, 1, 0),
    (2, 1, 0, 0),
    (2, 2, 0, 0),
    (2, 3, 0, 0),
    (2, 4, 1, 0),
    (2, 5, 1, 0),
    (5, 4, 0, 0),
    (5, 5, 0, 0),
    (2, 9, 0, 0),
    (2, 10, 0, 0),
    (2, 11, 0, 0),
    (2, 12, 0, 0),
    (2, 13, 0, 0);



insert into Room (id, capacity) values
    (1, 15),
    (2, 10),
    (3, 20),
    (4, 10),
    (5, 10),
    (6, 10),
    (7, 50),
    (8, 100),
    (9, 6),
    (10, 13),
    (11, 10),
    (12, 20),
    (13, 20),
    (14, 15),
    (15, 13);

insert into Reservation (appointment_id, room_id) values 
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (7, 1),
    (8, 1),
    (9, 5),
    (10, 5),
    (11, 5),
    (12, 5),
    (13, 5);





