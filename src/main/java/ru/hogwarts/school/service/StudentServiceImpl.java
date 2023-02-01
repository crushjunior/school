package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        return studentRepository.findStudentsByAge(age);
    }

    @Override
    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Faculty getFacultyByStudentId(long id) {
        Student foundStudent = studentRepository.findById(id).get();
        return foundStudent.getFaculty();
    }

    public Collection<Student> getAgeBetween(int min, int max) {
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    public Integer getCountOfStudents() {
        return studentRepository.getCountOfAllStudents();
    }


    public Double getAvgOfAge() {
        return studentRepository.getAvgAgeOfStudents();
    }

    public Collection<Student> get5LastStudents() {
        return studentRepository.get5LastStudents();
    }
//    public Collection<Student> findStudentsByFacultyId(long id) {
//        return studentRepository.findStudentsByFacultyId(id);
//    }



    //    @Override
//    public Collection<Student> findByAge(int age) {
//        List<Student> sortedStudents = new ArrayList<>();
//        for (Student student : studentRepository.findAll()) {
//            if (student.getAge() == age) {
//                sortedStudents.add(student);
//            }
//        }
//        return sortedStudents;
//    }
}
