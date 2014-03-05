CREATE TABLE Employee (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50),
    phone VARCHAR(16),
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL,
    invitationToAppointment INT,
    PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (invitationToAppointment) REFERENCES Appointment(id)
);

CREATE TABLE Group (
    id INT NOT NULL,
    member INT NOT NULL,
    subgroup INT,
    PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (member) REFERENCES Employee(id),
    CONSTRAINT FOREIGN KEY (subgroup) REFERENCES Group(id)
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

CREATE TABLE Notification (
    employee_id INT NOT NULL,
    appointment_id INT NOT NULL,
    PRIMARY KEY (employee_id, appointment_id),
    CONSTRAINT FOREIGN KEY (employee_id) REFERENCES Employee(id),
    CONSTRAINT FOREIGN KEY (appointment_id) REFERENCES Appointment(id)
);

CREATE TABLE Room (
    id INT NOT NULL AUTO_INCREMENT,
    capacity INT,
    PRIMARY KEY (id)
);

INSERT INTO Employee (name, email, password) VALUES
  ("Mathias Ose", "m@thiaso.se", "hunter2");