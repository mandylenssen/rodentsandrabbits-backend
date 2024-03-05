INSERT INTO users (username, password, first_name, last_name, phone_number, enabled, email) VALUES ('user@test.com','$2a$10$7Q1mCx4OYCDlTRkTQyJyBeHLtB7TMrQK3DtxEfoUWfh8ep8OdFNde', 'Emily', 'Johnson', '061529522', TRUE, 'user@test.com');
INSERT INTO users (username, password, first_name, last_name, phone_number, enabled, email) VALUES ('user2@test.com','$2a$10$7Q1mCx4OYCDlTRkTQyJyBeHLtB7TMrQK3DtxEfoUWfh8ep8OdFNde', 'Jessica', 'Davis', '0612329451', TRUE, 'user2@test.com');
INSERT INTO users (username, password, email, enabled) VALUES ('admin@rodentsandrabbits.com', '$2a$10$odVxkLdFNRHjEo3769iV8O4XvQU0yjhe8d5zhKjGdl/cNufO6sbOC', 'admin@rodentsandrabbits.com', TRUE);


INSERT INTO authorities (username, authority) VALUES ('user@test.com', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('user2@test.com', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin@rodentsandrabbits.com', 'ROLE_ADMIN');

INSERT INTO logbooks (user_name) VALUES ('user@test.com');
INSERT INTO logbooks (user_name) VALUES ('user2@test.com');


INSERT INTO pets (name, birthday, species, gender, details, diet, owner) VALUES ('Squeek', '2019-01-01', 'rat', 'male', 'Loves to play with toys and enjoys being handled', 'pellets, nuts, grains, vegetables', 'user@test.com'),
                                                                                ('Whiskers', '2019-03-21', 'rat', 'male', 'Very curious and energetic, enjoys exploring new environments', 'pellets, nuts, grains, vegetables', 'user@test.com'),
                                                                                ('Nimbus', '2020-07-30', 'rat', 'male', 'Affectionate and social, loves cuddling with its cage mates', 'pellets, nuts, grains, vegetables', 'user@test.com');

INSERT INTO pets (name, birthday, species, gender, details, diet, owner) VALUES ('Nibbles', '2019-01-01', 'mouse', 'female', 'Friendly and playful, enjoys running on a wheel', 'seeds, fruits, vegetables', 'user2@test.com'),
                                                                                ('Peanut', '2020-05-15', 'mouse', 'female', 'Sweet-natured and gentle, loves nibbling on treats', 'seeds, fruits, vegetables', 'user2@test.com');




INSERT INTO bookings (start_date, end_date, additional_info, is_confirmed) VALUES ('2024-02-20', '2024-03-23', 'The rats will be brought in their own spacious cage, equipped with bedding, food, and water containers', false),
                                                                                    ('2024-02-10', '2024-03-10', 'The mice will be brought in their own comfortable enclosure, furnished with bedding, food, and water bowls', true);


INSERT INTO bookings_pets (bookings_id, pets_id) VALUES (1, 1), (1, 2), (1, 3), (2, 4), (2, 5);



INSERT INTO logbook_logs (date, logbook_id, entry) VALUES   ('2024-02-20', 1, 'The rats were brought in their cage, and they are doing well. They are eating and drinking normally, and they seem to be adjusting to their new environment.'),
                                                                ('2021-02-21', 1, 'The rats are very active and playful. They are enjoying their time in the play area, and they are getting along well with each other.'),
                                                                ('2021-02-22', 1, 'The rats are doing well. They are eating and drinking normally, and they are very friendly and social.'),
                                                                ('2021-02-23', 1, 'The rats are doing well. They are eating and drinking normally, and they are very friendly and social.'),
                                                                ('2021-02-24', 1, 'The rats are doing well. They are eating and drinking normally, and they are very friendly and social.'),
                                                                ('2021-02-25', 1, 'The rats are doing well. They are eating and drinking normally, and they are very friendly and social.'),
                                                                ('2021-02-26', 1, 'The rats are doing well. They are eating and drinking normally, and they are very friendly and social.');

INSERT INTO logbook_log_pets (logbook_log_id, pet_id) VALUES (1,1), (1,2), (1,3);
