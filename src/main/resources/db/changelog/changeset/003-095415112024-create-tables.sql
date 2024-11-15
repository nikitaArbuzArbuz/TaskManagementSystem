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

CREATE TABLE tms.tasks
(
    id          BIGINT NOT NULL PRIMARY KEY,
    title       VARCHAR(255),
    description VARCHAR(255),
    status      VARCHAR(50),
    priority    VARCHAR(50),
    author_id   BIGINT,
    executor_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES tms.users (id) ON DELETE CASCADE,
    FOREIGN KEY (executor_id) REFERENCES tms.users (id) ON DELETE CASCADE
);

CREATE TABLE tms.comments
(
    id        BIGINT NOT NULL PRIMARY KEY,
    text      VARCHAR(255),
    author_id BIGINT,
    task_id   BIGINT,
    FOREIGN KEY (author_id) REFERENCES tms.users (id) ON DELETE CASCADE,
    FOREIGN KEY (task_id) REFERENCES tms.tasks (id) ON DELETE CASCADE
);

CREATE TABLE tms.user_roles
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (role_id, user_id),
    CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES tms.roles (id),
    CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES tms.users (id)
);