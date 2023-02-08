-- liquibase formatted sql

--changeset vcharushnikov:1
CREATE INDEX faculty_name_color_index ON faculty (name, color);

--changeset vcharushnikov:2
CREATE INDEX student_name_index ON student (name);