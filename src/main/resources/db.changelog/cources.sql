-- liquibase formatted sql

-- changeset csa21472001:create-credentials-entity
CREATE TABLE course (
  id SERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  duration NUMERIC(10, 1) NOT NULL,
  price NUMERIC(10, 2) NOT NULL
);
-- changeset csa21472001:insert basic values
INSERT INTO course (title, duration, price)
VALUES ('Курс по программированию', 10.5, 500.00);
