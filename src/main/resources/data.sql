INSERT INTO users (username, password, first_name, last_name, phone_number, enabled, email) VALUES ('mandy@gmail.com','$2a$10$7Q1mCx4OYCDlTRkTQyJyBeHLtB7TMrQK3DtxEfoUWfh8ep8OdFNde', 'Mandy', 'Lenssen', '061529522', TRUE, 'mandy@gmail.com');
INSERT INTO users (username, password, first_name, last_name, phone_number, enabled, email) VALUES ('piet@gmail.com','$2a$10$7Q1mCx4OYCDlTRkTQyJyBeHLtB7TMrQK3DtxEfoUWfh8ep8OdFNde', 'Piet', 'Verdriet', '0612329451', TRUE, 'piet@gmail.com');
INSERT INTO logbooks (user_name) VALUES ('mandy@gmail.com');
INSERT INTO logbooks (user_name) VALUES ('piet@gmail.com');


INSERT INTO pets (name, birthday, species, gender, details, diet, owner) VALUES ('Squeek', '2019-01-01', 'rat', 'male', 'cute rat', 'pellets', 'mandy@gmail.com'),
                                                                                ('Whiskers', '2019-03-21', 'rat', 'male', 'silly rat', 'pellets', 'mandy@gmail.com'),
                                                                                ('Nimbus', '2020-07-30', 'rat', 'male', 'weird rat', 'pellets', 'mandy@gmail.com');

INSERT INTO pets (name, birthday, species, gender, details, diet, owner) VALUES ('Fluffy', '2019-01-01', 'hamster', 'female', 'sleeps all day', 'dried fruit', 'piet@gmail.com');


INSERT INTO users (username, password, email, enabled) VALUES ('admin@rodentsandrabbits.nl', '$2a$10$odVxkLdFNRHjEo3769iV8O4XvQU0yjhe8d5zhKjGdl/cNufO6sbOC', 'admin@rodentsandrabbits.nl', TRUE);

INSERT INTO authorities (username, authority) VALUES ('mandy@gmail.com', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('piet@gmail.com', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin@rodentsandrabbits.nl', 'ROLE_ADMIN');

