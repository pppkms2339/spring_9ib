DROP TABLE todo IF EXISTS;
CREATE TABLE todo (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    created TIMESTAMP,
    updated TIMESTAMP,
    completed BOOLEAN
);