CREATE TABLE Activity (
    activityName VARCHAR2(100) PRIMARY KEY,
    educativeLevel VARCHAR2(50),
    timeSlot VARCHAR2(50),
    maxAssistants NUMBER,
    neededMonitors NUMBER
);

CREATE TABLE Assistant (
    id NUMBER PRIMARY KEY,
    firstName VARCHAR2(50),
    lastName VARCHAR2(50),
    birthDate DATE,
    requireSpecialAttention CHAR(1)
);

CREATE TABLE Camp (
    campID NUMBER PRIMARY KEY,
    start DATE,
    end DATE,
    educativeLevel VARCHAR2(50),
    capacity NUMBER,
    principalMonitorId NUMBER,
    specialMonitorId NUMBER,
    FOREIGN KEY (principalMonitorId) REFERENCES Monitor(id),
    FOREIGN KEY (specialMonitorId) REFERENCES Monitor(id)
);

CREATE TABLE CampActivity (
    campId NUMBER,
    activityName VARCHAR2(100),
    PRIMARY KEY (campId, activityName),
    FOREIGN KEY (campId) REFERENCES Camp(campID),
    FOREIGN KEY (activityName) REFERENCES Activity(activityName)
);

CREATE TABLE Inscription (
    assistantId NUMBER,
    campId NUMBER,
    inscriptionDate DATE,
    price FLOAT,
    canBeCanceled CHAR(1),
    PRIMARY KEY (assistantId, campId),
    FOREIGN KEY (assistantId) REFERENCES Assistant(id),
    FOREIGN KEY (campId) REFERENCES Camp(campID)
);

CREATE TABLE MonitorActivity (
    monitorId NUMBER,
    activityName VARCHAR2(100),
    PRIMARY KEY (monitorId, activityName),
    FOREIGN KEY (monitorId) REFERENCES Monitor(id),
    FOREIGN KEY (activityName) REFERENCES Activity(activityName)
);

CREATE TABLE Monitor (
    id NUMBER PRIMARY KEY,
    firstName VARCHAR2(50),
    lastName VARCHAR2(50),
    specialEducator CHAR(1)
);