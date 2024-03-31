INSERT INTO users (username, password, first_name, last_name, phone_number, email) VALUES ('user@test.com','$2a$10$7Q1mCx4OYCDlTRkTQyJyBeHLtB7TMrQK3DtxEfoUWfh8ep8OdFNde', 'Emily', 'Johnson', '061529522', 'user@test.com');
INSERT INTO users (username, password, first_name, last_name, phone_number, email) VALUES ('user2@test.com','$2a$10$7Q1mCx4OYCDlTRkTQyJyBeHLtB7TMrQK3DtxEfoUWfh8ep8OdFNde', 'Jessica', 'Davis', '0612329451', 'user2@test.com');
INSERT INTO users (username, password, email) VALUES ('admin@rodentsandrabbits.com', '$2a$10$odVxkLdFNRHjEo3769iV8O4XvQU0yjhe8d5zhKjGdl/cNufO6sbOC', 'admin@rodentsandrabbits.com');


INSERT INTO authorities (username, authority) VALUES ('user@test.com', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('user2@test.com', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin@rodentsandrabbits.com', 'ROLE_ADMIN');

INSERT INTO logbooks (user_name) VALUES ('user@test.com');
INSERT INTO logbooks (user_name) VALUES ('user2@test.com');


INSERT INTO pets (name, birthday, species, gender, details, diet, owner, enabled) VALUES ('Squeek', '2019-01-01', 'rat', 'male', 'Loves to play with toys and enjoys being handled', 'pellets, nuts, grains, vegetables', 'user@test.com', TRUE),
                                                                                ('Whiskers', '2019-03-21', 'rat', 'male', 'Very curious and energetic, enjoys exploring new environments', 'pellets, nuts, grains, vegetables', 'user@test.com', TRUE),
                                                                                ('Nimbus', '2020-07-30', 'rat', 'male', 'Affectionate and social, loves cuddling with its cage mates', 'pellets, nuts, grains, vegetables', 'user@test.com', TRUE);

INSERT INTO pets (name, birthday, species, gender, details, diet, owner, enabled) VALUES ('Nibbles', '2019-01-01', 'mouse', 'female', 'Friendly and playful, enjoys running on a wheel', 'seeds, fruits, vegetables', 'user2@test.com', TRUE),
                                                                                ('Peanut', '2020-05-15', 'mouse', 'female', 'Sweet-natured and gentle, loves nibbling on treats', 'seeds, fruits, vegetables', 'user2@test.com', TRUE);




INSERT INTO bookings (start_date, end_date, additional_info, is_confirmed) VALUES ('2024-03-20', '2024-07-23', 'The rats will be brought in their own spacious cage, equipped with bedding, food, and water containers', false),
                                                                                    ('2024-03-10', '2024-07-10', 'The mice will be brought in their own comfortable enclosure, furnished with bedding, food, and water bowls', true);


INSERT INTO bookings_pets (bookings_id, pets_id) VALUES (1,1), (1, 2), (1, 3), (2, 4), (2, 5);



INSERT INTO logbook_logs (date, logbook_id, entry) VALUES   ('2024-03-10', 1, 'Today was a big day! We welcomed our new rat friends. They seemed a bit shy at first but quickly started sniffing around and settling into their new digs. It was adorable seeing how quickly they got comfortable. They also chowed down on their food, which is always a good sign..'),
                                                                ('2024-03-11', 1, 'The rats had the time of their lives in the play area today. They were climbing all over everything, and it seems they''ve taken a liking to the tunnel tubes. Watching them explore and play is just the best; it really brings the place to life. And we found out they absolutely love the special treats we gave them.'),
                                                                ('2024-03-12', 1, 'It’s becoming clearer by the day that these little guys are super social, both with us and each other. They now come to the cage door when we walk by, curious about what we’re up to. We spent some time today getting to know their personalities, and it''s surprising how unique each of them is.'),
                                                                ('2024-03-13', 1, 'We introduced some new toys into their cage today, and it was a hit! They were particularly fascinated by a hanging toy we put up. It’s heartwarming to see them play and look forward to new experiences. Also, we scheduled some extra cuddle time, and they seemed to really enjoy the contact.'),
                                                                ('2024-03-14', 1, 'Our little adventurers are getting bolder by the day. Today, they tried out a new climbing frame we set up for them, and it was a big success. It seems their curiosity knows no bounds, and we’re doing our best to keep providing them with new and exciting experiences. It’s amazing to see how they grow and develop in such a short time.');


INSERT INTO logbook_log_pets (logbook_log_id, pet_id) VALUES (1,1), (1,2), (1,3), (2,1), (2,2), (2,3), (3,1), (3,2), (3,3), (4,1), (4,2), (4,3), (5,1), (5,2), (5,3);
