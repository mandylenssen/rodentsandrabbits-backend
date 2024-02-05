INSERT INTO users (username, password, first_name, last_name, phone_number, enabled, email) VALUES ('mandy@gmail.com','$2a$10$wTT6b3ugntsWYsAIPN/EoOeqlNXrtGfUScbbASJgtn.Ya2JUAYPHy', 'Mandy', 'Lenssen', '0612229522', TRUE, 'mandy@gmail.com');

INSERT INTO pets (id, name, birthday, species, gender, details, diet, owner) VALUES (1, 'Squeek', '2019-01-01', 'rat', 'male', 'cute rat', 'pellets', 'mandy@gmail.com'),
                                                                                    (2, 'Whiskers', '2019-03-21', 'rat', 'male', 'silly rat', 'pellets', 'mandy@gmail.com'),
                                                                                    (3, 'Numbers', '2020-07-30', 'rat', 'male', 'weird rat', 'pellets', 'mandy@gmail.com');



INSERT INTO users (username, password, email, enabled) VALUES ('admin', 'test123', 'admin@test.nl', TRUE);

INSERT INTO authorities (username, authority) VALUES ('mandy@gmail.com', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
