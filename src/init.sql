INSERT INTO website.employees (name, address, birthday, education, working_since, position, email, is_admin)
VALUES
('Светлана Ищенко', 'г. Москва, улица Улица дом 5 кв. 25', '2000-01-15', 'высшее', '2024-09-10', 'директор', 'director@company.org', True),
('Екатерина Андреева', 'г. Москва, ...', '1999-04-04', 'высшее', '2025-01-15', 'менеджер', 'manager@company.org', True),
('Александр Погодин', 'г. Москва ...', '1995-12-15', 'высшее', '2023-09-10', 'программист', 'employee@company.org', False);

INSERT INTO website.projects (project_name, project_start, project_end, project_head)
VALUES
('проект1', '2025-02-15', '2025-05-01', 1),
('проект2', '2025-01-01', '2025-01-15', 1),
('проект3', '2025-01-17', '2025-02-27', 3);

INSERT INTO website.employees_projects (employee_id, project_id, position, appointed_at, quit_at)
VALUES
(1, 1, 'директор', '2025-02-15', NULL),
(2, 1, 'менеджер', '2025-02-15', NULL),
(3, 1, 'программист', '2025-02-15', NULL),
(1, 2, 'директор', '2025-01-01', '2025-01-15'),
(2, 2, 'менеджер', '2025-02-15', '2025-01-15'),
(2, 3, 'менеджер', '2025-02-15', NULL),
(3, 3, 'программист', '2025-02-15', NULL);

INSERT INTO website.bonuses (name, percentage)
VALUES
('Новогодняя премия', 200.00),
('Премия на день рождения', 50.00),
('Премия за усердную работу', 100.00);

INSERT INTO website.employees_payouts (employee_id, amount, paid_at)
VALUES
(1, 300000.00, '2025-01-25'),
(1, 450000.00, '2025-02-10'),
(2, 150000.00, '2025-01-25'),
(2, 100000.00, '2025-02-10'),
(3, 260000.00, '2025-01-25'),
(3, 130000.00, '2025-02-10');

INSERT INTO website.employees_bonuses (payout_id, bonus_id, amount)
VALUES
(2, 2, 150000.00),
(3, 2, 50000.00),
(5, 3, 130000.00);

COMMIT;
