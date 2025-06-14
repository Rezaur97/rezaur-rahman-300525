--CREATE TABLE customer (
--	id BIGINT AUTO_INCREMENT PRIMARY KEY,
--	first_name varchar(100) not null,
--	last_name varchar(100) not null,
--	mobile_number varchar(15) not null
--);
--ALTER TABLE customer ADD CONSTRAINT customer_uk1 UNIQUE (mobile_number);

--CREATE SEQUENCE customer_seq START WITH 5 INCREMENT BY 1;

CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL
);

CREATE TABLE mobile_number (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mobile_number VARCHAR(15) NOT NULL UNIQUE,
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
);
