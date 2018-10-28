DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (datetime,description, calories, user_id) values
  ('2018-10-10 14:00:00','полдник', 500, 100000),
  ('2018-10-12 07:00:00','завтрак', 600, 100000),
  ('2018-10-12 13:00:00','обед', 500, 100000),
  ('2018-10-11 14:00:00','недообед', 500, 100000),
  ('2018-10-12 12:00:00','хххх', 1500, 100001);