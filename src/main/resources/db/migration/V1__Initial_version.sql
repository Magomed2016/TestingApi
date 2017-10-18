CREATE TABLE Topic(
    TOPIC_ID int NOT NULL AUTO_INCREMENT,
    TOPIC_NAME TEXT NOT NULL,
    PRIMARY KEY (TOPIC_ID)
);

CREATE TABLE Question(
    QUESTION_ID int NOT NULL AUTO_INCREMENT,
    QUESTION_TEXT TEXT NOT NULL,
    QUESTION_TYPE int NOT NULL,
    PRIMARY KEY (QUESTION_ID)
);

CREATE TABLE topic_question(
    TOPIC_ID int NOT NULL,
    QUESTION_ID int NOT NULL
);

CREATE TABLE Answer(
    ANSWER_ID int NOT NULL AUTO_INCREMENT,
    QUESTION_ID int NOT NULL,
    TEXT TEXT NOT NULL,
    IS_RIGHT boolean NOT NULL,
    PRIMARY KEY(ANSWER_ID)
);

INSERT INTO Topic VALUES ('1', 'тема1');
INSERT INTO Topic VALUES ('2', 'тема2');

INSERT INTO Question VALUES('1', 'В каком году родился Пушкин?', '0');
INSERT INTO topic_question VALUES('1', '1');
INSERT INTO topic_question VALUES('1', '2');
INSERT INTO Answer VALUES(DEFAULT, '1', 'В 1799', 'true');
INSERT INTO Answer VALUES(DEFAULT, '1', 'В 1802', 'false');
INSERT INTO Answer VALUES(DEFAULT, '1', 'В 1376', 'false');
INSERT INTO Answer VALUES(DEFAULT, '1', 'В 1856', 'false');


INSERT INTO Question VALUES('2', 'В каком году родился Толстой?', '1');
INSERT INTO topic_question VALUES('1', '3');
INSERT INTO Answer VALUES(DEFAULT, '2', 'В 1735', 'false');
INSERT INTO Answer VALUES(DEFAULT, '2', 'В 1457', 'false');
INSERT INTO Answer VALUES(DEFAULT, '2', 'В 1376', 'false');
INSERT INTO Answer VALUES(DEFAULT, '2', 'В 1856', 'true');

INSERT INTO Question VALUES('3', 'Впишите год рождения Лермонтова?', '2');
INSERT INTO topic_question VALUES('1', '4');
INSERT INTO Answer VALUES(DEFAULT, '3', '1745', 'true');

INSERT INTO Question VALUES('4', 'В каком году родился Чехов?', '0');
INSERT INTO topic_question VALUES('1', '5');
INSERT INTO Answer VALUES(DEFAULT, '4', 'В 1565', 'false');
INSERT INTO Answer VALUES(DEFAULT, '4', 'В 1787', 'true');
INSERT INTO Answer VALUES(DEFAULT, '4', 'В 1456', 'false');
INSERT INTO Answer VALUES(DEFAULT, '4', 'В 1656', 'false');

INSERT INTO Question VALUES('5', 'В каком году потерял глаз Кутузов?', '2');
INSERT INTO topic_question VALUES('1', '6');
INSERT INTO Answer VALUES(DEFAULT, '5', '1657', 'true');

INSERT INTO Question VALUES('6', 'Когда он его нашёл?', '0');
INSERT INTO Answer VALUES(DEFAULT, '6', 'В 1565', 'false');
INSERT INTO Answer VALUES(DEFAULT, '6', 'В 1787', 'true');
INSERT INTO Answer VALUES(DEFAULT, '6', 'В 1456', 'false');
INSERT INTO Answer VALUES(DEFAULT, '6', 'В 1656', 'false');

