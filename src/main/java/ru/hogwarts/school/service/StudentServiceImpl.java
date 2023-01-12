package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final HashMap<Long, Student> students = new HashMap<>();
    private long id = 0;


    @Override
    public Student addStudent(Student student) {
        student.setId(++id);
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Student findStudent(long id) {
        return students.get(id);
    }

    @Override
    public Student editStudent(long id, Student student) {
        if (!students.containsKey(id)) {
            return null;
        }
        students.put(id, student);
        return student;
    }

    @Override
    public Student deleteStudent(long id) {
        return students.remove(id);
    }

    @Override
    public Collection<Student> findByAge(int age) {
        List<Student> sortedStudents = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getAge() == age) {
                sortedStudents.add(student);
            }
        }
        return sortedStudents;
    }
}
