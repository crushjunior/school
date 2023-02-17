package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> findStudent(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student studentEdit = studentService.editStudent(student);
        if (studentEdit == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(studentEdit);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findByAge(@RequestParam int age) {
        return ResponseEntity.ok(studentService.findByAge(age));
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Student>> getAll() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/between")
    public ResponseEntity<Collection<Student>> findByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return ResponseEntity.ok(studentService.getAgeBetween(min, max));
    }

    @GetMapping("facultyByStudent/{id}")
    public ResponseEntity<Faculty> findFacultyByStudent(@PathVariable long id) {
        return ResponseEntity.ok(studentService.getFacultyByStudentId(id));
    }

    @GetMapping("count-students")
    public ResponseEntity<Integer> getCountOfStudents() {
        return ResponseEntity.ok(studentService.getCountOfStudents());
    }

    @GetMapping("avg-age")
    public ResponseEntity<Double> getAvgOfAge() {
        return ResponseEntity.ok(studentService.getAvgOfAge());
    }

    @GetMapping("last-5-students")
    public ResponseEntity<Collection<Student>> get5LastStudents() {
        return ResponseEntity.ok(studentService.get5LastStudents());
    }

    @GetMapping("students-by-name/{name}")
    public ResponseEntity<Collection<Student>> getStudentsByName(@PathVariable String name) {
        return ResponseEntity.ok(studentService.getStudentsByName(name));
    }

    @GetMapping("name-of-students-start-by-a")
    public ResponseEntity<List<String>> getStudentsByNameStartA() {
        return ResponseEntity.ok(studentService.getStudentsByNameStartA());
    }

    @GetMapping("avg-age-students")
    public ResponseEntity<Double> getAvgOfAgeStudents() {
        return ResponseEntity.ok(studentService.getAvgOfStudentsAge());
    }

    @GetMapping("get-students-not-sync")
    public void getAllStudentsForConsoleNotSync() {
        studentService.getAllStudentsForConsoleNotSync();
    }

    @GetMapping("get-students-with-sync")
    public void getAllStudentsForConsoleSync() {
        studentService.getAllStudentsForConsoleSync();
    }
}
