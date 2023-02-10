-- Составить первый JOIN-запрос, чтобы получить информацию обо всех студентах
--     (достаточно получить только имя и возраст студента) школы Хогвартс вместе с названиями факультетов.
--
-- Составить второй JOIN-запрос, чтобы получить только тех студентов, у которых есть аватарки.

SELECT student.name, student.age, faculty.name
FROM student
LEFT JOIN faculty ON student.faculty_id = faculty.id;

SELECT student.name, student.age, avatar.id
FROM student
INNER JOIN avatar ON student.avatar_id = avatar.id;