INSERT INTO `user` (`uid`, `create_date`, `name`, `pwd`, `update_date`) VALUES
('admin', '2020-01-08 00:00:00', 'Paggy', '$2a$10$JJ4OPpNGP3jWZuJrqcr6IO25qHA3OgDlo.6SZiUJVvNKY4/rTqy7u', NULL);

INSERT INTO `role` (`rid`, `name`) VALUES
('ROLE_ADMIN', '管理者'),
('ROLE_GENERAL', '一般使用者');

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
('admin', 'ROLE_ADMIN');