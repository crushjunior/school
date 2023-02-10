package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentsByAgeBetween(int min, int max);

    Collection<Student> findStudentsByAge(int age);

    Collection<Student> getStudentsByNameIgnoreCase(String name);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getCountOfAllStudents();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Double getAvgAgeOfStudents();

    @Query(value = "SELECT * FROM STUDENT ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> get5LastStudents();

//    Collection<Student> findStudentsByFacultyId(long id);
}
