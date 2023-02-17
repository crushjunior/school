package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }



    @Override
    public Student addStudent(Student student) {
        logger.info("Was invoked method for add student");
        Student newStudent = studentRepository.save(student);
        logger.debug("{} was added to application", newStudent);
        return newStudent;
    }

    @Override
    public Student findStudent(long id) {
        logger.info("Was invoked method for find student");
        Student student = studentRepository.findById(id).get();
        logger.debug("User want to find student: {}", student);
        return student;
    }

    @Override
    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student");
        Student editStudent = studentRepository.save(student);
        logger.debug("User want to edit student: {}", editStudent);
        return editStudent;
    }

    @Override
    public void deleteStudent(long id) {
        logger.info("Was invoked method for delete student");
        logger.debug("User want to delete {} ", studentRepository.findById(id));
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        logger.info("Was invoked method for find students by age");
        Collection<Student> students = studentRepository.findStudentsByAge(age);
        logger.debug("User want to find students with {}: {}", age, students);
        return students;
    }

    @Override
    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for get all students");
        Collection<Student> students = studentRepository.findAll();
        logger.debug("User want to get all students: {}", students);
        return students;
    }

    @Override
    public Faculty getFacultyByStudentId(long id) {
        logger.info("Was invoked method for get faculty by student");
        Faculty foundFaculty = studentRepository.findById(id).get().getFaculty();
        logger.debug("User want to get faculty by student: {}", foundFaculty);
        return foundFaculty;
    }

    public Collection<Student> getAgeBetween(int min, int max) {
        logger.info("Was invoked method for find students by age between");
        Collection<Student> students = studentRepository.findStudentsByAgeBetween(min, max);
        logger.debug("User want to get students: {}", students);
        return students;
    }

    public Integer getCountOfStudents() {
        logger.info("Was invoked method for get count of students");
        Integer count = studentRepository.getCountOfAllStudents();
        logger.debug("User want to get count of students: {}", count);
        return count;
    }


    public Double getAvgOfAge() {
        logger.info("Was invoked method for get average of student's age");
        Double count = studentRepository.getAvgAgeOfStudents();
        logger.debug("User want to get average of student's age: {}", count);
        return count;
    }

    public Collection<Student> get5LastStudents() {
        logger.info("Was invoked method for get 5 last students");
        Collection<Student> students = studentRepository.get5LastStudents();
        logger.debug("User want to get 5 last students: {}", students);
        return students;
    }

    public Collection<Student> getStudentsByName(String name) {
        logger.info("Was invoked method for get students by name");
        Collection<Student> students = studentRepository.getStudentsByNameIgnoreCase(name);
        logger.debug("User want to get students by name: {}", students);
        return students;
    }

    public List<String> getStudentsByNameStartA() {
        logger.info("Was invoked method for get average of student's age");
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(w -> w.startsWith("А"))
                .sorted()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    public Double getAvgOfStudentsAge() {
        logger.info("Was invoked method for get average of student's age by stream API");
        return studentRepository.findAll().stream()
                .map(Student::getAge)
                .mapToDouble(v -> v.doubleValue())
                .average()
                .orElse(0.0);
    }

    public void getAllStudentsForConsoleNotSync() {
        System.out.println(studentRepository.findAll().get(0));
        System.out.println(studentRepository.findAll().get(1));

        new Thread(() -> {
            System.out.println(studentRepository.findAll().get(2));
            System.out.println(studentRepository.findAll().get(3));
        } ).start();

        new Thread(() -> {
            System.out.println(studentRepository.findAll().get(4));
            System.out.println(studentRepository.findAll().get(5));
        } ).start();

    }

    public void getAllStudentsForConsoleSync() {

        System.out.println(studentRepository.findAll().get(0));
        System.out.println(studentRepository.findAll().get(1));

        new Thread(() -> {
            synchronized (StudentServiceImpl.class) {
                System.out.println(studentRepository.findAll().get(2));
                System.out.println(studentRepository.findAll().get(3));
            }
        } ).start();

        new Thread(() -> {
            synchronized (StudentServiceImpl.class) {
                System.out.println(studentRepository.findAll().get(4));
                System.out.println(studentRepository.findAll().get(5));
            }
        } ).start();

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
