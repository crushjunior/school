package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    public Student addStudent(Student student);

    public Student findStudent(long id);

    public Student editStudent(Student student);

    public void deleteStudent(long id);

    public Collection<Student> getAllStudents();

    public Faculty getFacultyByStudentId(long id);



    //    public Collection<Student> findByAge(int age);
}
