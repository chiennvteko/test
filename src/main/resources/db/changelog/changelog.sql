--liquibase formatted sql

--changeset chiennv:1

DROP TABLE IF EXISTS notes;
CREATE TABLE notes (
    id int primary key AUTO_INCREMENT,
    title varchar(64),
    content varchar(255),
    create_at DateTime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    edited_at DateTime NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--changeset chiennv:3

DROP TABLE IF EXISTS colors;
CREATE TABLE colors (
    id int primary key AUTO_INCREMENT,
    name varchar(64),
    code varchar(9),
    is_default tinyint(2)
);

--changeset chiennv:5

DROP TABLE IF EXISTS note_colors;
CREATE TABLE note_colors (
    id int primary key AUTO_INCREMENT,
    note_id int,
    color_id int,
    UNIQUE(note_id),
    FOREIGN KEY (color_id) REFERENCES colors(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (note_id) REFERENCES notes(id) ON DELETE CASCADE ON UPDATE CASCADE
);

--changset chienv:7

DROP TABLE IF EXISTS labels;
CREATE TABLE labels (
    id int primary key AUTO_INCREMENT,
    name varchar(64)
);

DROP TABLE IF EXISTS note_labels;
CREATE TABLE note_labels (
    id int primary key AUTO_INCREMENT,
    note_id int,
    label_id int,
    UNIQUE(label_id, note_id),
    FOREIGN KEY (label_id) REFERENCES labels(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (note_id) REFERENCES notes(id)ON DELETE CASCADE ON UPDATE CASCADE
);