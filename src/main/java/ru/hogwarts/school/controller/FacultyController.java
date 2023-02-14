package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyServiceImpl facultyService;

    public FacultyController(FacultyServiceImpl facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> findFaculty(@PathVariable long id) {
        Faculty foundFaculty = facultyService.findFaculty(id);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.addFaculty(faculty));
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty editFaculty = facultyService.editFaculty(faculty);
        if (editFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> findByColor(@RequestParam String color) {
        return ResponseEntity.ok(facultyService.findByColor(color));
    }

    @GetMapping("/allAndNameColor")
    public ResponseEntity findFacultyByColorOrName(@RequestParam(required = false) String color, @RequestParam(required = false) String name) {
        if (color != null && !color.isBlank() || name != null && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColorOrName(color, name));
        }
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @GetMapping("studentsByFaculty/{id}")
    public ResponseEntity<Collection<Student>> findStudentsByFacultyId(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.getStudentsByFacultyId(id));
    }

    @GetMapping("the-longest-faculty-name")
    public ResponseEntity<String> findLongestNameOfFaculties() {
        return ResponseEntity.ok(facultyService.findLongestNameOfFaculties());
    }

    @GetMapping("parallel-test")
    public ResponseEntity<Integer> testParallel() {
        return ResponseEntity.ok(facultyService.testParallelStream());
    }

//    @GetMapping("studentsByFaculty/{id}")
//    public ResponseEntity<Collection<Student>> findStudentsByFacultyId(@PathVariable long id) {
//        return ResponseEntity.ok(studentService.findStudentsByFacultyId(id));
//    }


    //    @GetMapping("/all")
//   public ResponseEntity<Collection<Faculty>> getAll() {
//       return ResponseEntity.ok(facultyService.getAllFaculties());
//    }
}
