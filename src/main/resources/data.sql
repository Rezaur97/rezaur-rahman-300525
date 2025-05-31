-- Inserting customers
INSERT INTO customer (first_name, last_name) VALUES ('Alan', 'Smith');
INSERT INTO customer (first_name, last_name) VALUES ('Joe', 'Turing');
INSERT INTO customer (first_name, last_name) VALUES ('John', 'Smith');
INSERT INTO customer (first_name, last_name) VALUES ('Kathy', 'Sierra');

-- Then inserting mobile numbers
INSERT INTO mobile_number (mobile_number, customer_id) VALUES ('9111111111', (SELECT id FROM customer WHERE first_name = 'Alan'));
INSERT INTO mobile_number (mobile_number, customer_id) VALUES ('9111111112', (SELECT id FROM customer WHERE first_name = 'Joe'));
INSERT INTO mobile_number (mobile_number, customer_id) VALUES ('9111111113', (SELECT id FROM customer WHERE first_name = 'John'));
INSERT INTO mobile_number (mobile_number, customer_id) VALUES ('9111111114', (SELECT id FROM customer WHERE first_name = 'Kathy'));
