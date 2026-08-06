INSERT INTO employee(id, name, email, department, hire_date, active)
VALUES (1, 'Ana Costa', 'ana.costa@empresa.com', 'TI', '2022-03-15', TRUE);
INSERT INTO employee(id, name, email, department, hire_date, active)
VALUES (2, 'Bruno Lima', 'bruno.lima@empresa.com', 'RH', '2021-08-01', TRUE);
INSERT INTO employee(id, name, email, department, hire_date, active)
VALUES (3, 'Carla Souza', 'carla.souza@empresa.com', 'TI', '2020-01-10', FALSE);
ALTER TABLE employee ALTER COLUMN id RESTART WITH 4;
