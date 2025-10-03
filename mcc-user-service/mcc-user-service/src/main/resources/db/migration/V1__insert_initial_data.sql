
CREATE TABLE roles
(
    id                     BIGSERIAL PRIMARY KEY,
    name                   VARCHAR(255) NOT NULL UNIQUE,
    permission_description VARCHAR(255)
);

CREATE TABLE users
(
    id        BIGSERIAL PRIMARY KEY,
    email     VARCHAR(255) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    role_id   BIGINT REFERENCES roles (id)
);

-- ==============================
-- V1__insert_initial_data.sql
-- ==============================

-- Insert roles with descriptions
INSERT INTO roles (name, permission_description)
VALUES ('OWNER', 'Dueño de mascotas, puede registrar y administrar sus mascotas'),
       ('WALKER', 'Paseador de mascotas, puede aceptar y realizar paseos'),
       ('ADMIN', 'Administrador del sistema, puede gestionar usuarios y datos');

-- Insert users with hashed passwords
-- Password (password123) hashed using bcrypt
INSERT INTO users (email, password, full_name, role_id)
VALUES ('alice.owner@example.com', '$2a$10$Dow1SWlUabIDlkygPZBuqOGI8SgphLbAjq.zIu4r4U11bK3Dsk5nO', 'Alice Johnson',
        (SELECT id FROM roles WHERE name = 'OWNER')),
       ('bob.sm@example.com', '$2a$10$Dow1SWlUabIDlkygPZBuqOGI8SgphLbAjq.zIu4r4U11bK3Dsk5nO', 'Bob Smith',
        (SELECT id FROM roles WHERE name = 'OWNER')),
       ('frank.owner@example.com', '$2a$10$Dow1SWlUabIDlkygPZBuqOGI8SgphLbAjq.zIu4r4U11bK3Dsk5nO', 'Frank Taylor',
        (SELECT id FROM roles WHERE name = 'OWNER')),
       ('henry.owner@example.com', '$2a$10$Dow1SWlUabIDlkygPZBuqOGI8SgphLbAjq.zIu4r4U11bK3Dsk5nO', 'Henry Davis',
        (SELECT id FROM roles WHERE name = 'OWNER')),

       ('carol.walker@example.com', '$2a$10$Dow1SWlUabIDlkygPZBuqOGI8SgphLbAjq.zIu4r4U11bK3Dsk5nO', 'Carol White',
        (SELECT id FROM roles WHERE name = 'WALKER')),
       ('david.walker@example.com', '$2a$10$Dow1SWlUabIDlkygPZBuqOGI8SgphLbAjq.zIu4r4U11bK3Dsk5nO', 'David Brown',
        (SELECT id FROM roles WHERE name = 'WALKER')),
       ('grace.walker@example.com', '$2a$10$Dow1SWlUabIDlkygPZBuqOGI8SgphLbAjq.zIu4r4U11bK3Dsk5nO', 'Grace Miller',
        (SELECT id FROM roles WHERE name = 'WALKER')),
       ('irene.walker@example.com', '$2a$10$Dow1SWlUabIDlkygPZBuqOGI8SgphLbAjq.zIu4r4U11bK3Dsk5nO', 'Irene Wilson',
        (SELECT id FROM roles WHERE name = 'WALKER')),

       ('eva.admin@example.com', '$2a$10$Dow1SWlUabIDlkygPZBuqOGI8SgphLbAjq.zIu4r4U11bK3Dsk5nO', 'Eva Green',
        (SELECT id FROM roles WHERE name = 'ADMIN')),
       ('jack.admin@example.com', '$2a$10$Dow1SWlUabIDlkygPZBuqOGI8SgphLbAjq.zIu4r4U11bK3Dsk5nO', 'Jack Thompson',
        (SELECT id FROM roles WHERE name = 'ADMIN'));
