-- liquibase formatted sql

-- changeset csa21472001:create-student-entity-table
CREATE TABLE student (
  id serial PRIMARY KEY,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  course_id integer REFERENCES course(id) ON DELETE CASCADE
);
--changeset csa21472001:insert basic values
INSERT INTO student (first_name, last_name, course_id)
VALUES ('Иван', 'Иванов', 1);