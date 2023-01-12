package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    public Student addStudent(Student student);

    public Student findStudent(long id);

    public Student editStudent(long id, Student student);

    public Student deleteStudent(long id);

    public Collection<Student> findByAge(int age);
}
