CREATE TABLE tms.tasks
(
    id   BIGINT NOT NULL PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(255),
    status VARCHAR(50),
    priority VARCHAR(50),
    comment VARCHAR(255),
);

CREATE TABLE tms.roles
(
    id   BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(20)
);


CREATE TABLE tms.users
(
    id         BIGINT       NOT NULL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    login      VARCHAR(20)  NOT NULL UNIQUE,
    email      VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(120) NOT NULL
);


CREATE TABLE tms.client_roles
(
    role_id   BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_clients_roles PRIMARY KEY (role_id, client_id),
    CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES bank.roles (id),
    CONSTRAINT fk_userol_on_client FOREIGN KEY (client_id) REFERENCES bank.clients (id)
);