-- liquibase formatted sql

-- changeset csa21472001:create-credentials-entity
CREATE TABLE credential (
  id SERIAL PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  phone_number VARCHAR(20) UNIQUE NOT NULL,
  student_id integer REFERENCES student(id) ON DELETE CASCADE
);
-- changeset csa21472001:insert basic values
INSERT INTO credential (email, phone_number, student_id)
VALUES ('ivanIvanov@gmail.com', '+12345678901', 1);