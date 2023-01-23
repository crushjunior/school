package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findFacultiesByColor(color);
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Student> getStudentsByFacultyId(long id) {
        Faculty foundFaculty = facultyRepository.findById(id).get();
        return foundFaculty.getStudents();
    }

    public Faculty findByColorOrName(String color, String name) {
        return facultyRepository.findFacultyByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

//    public Faculty findFacultyByStudentId(long id) {
//        return facultyRepository.findFacultyByStudentsId(id);
//    }




//    @Override
//    public Collection<Faculty> findByColor(String color) {
//        List<Faculty> sortedFaculties = new ArrayList<>();
//        for (Faculty faculty : facultyRepository.findAll()) {
//            if (faculty.getColor().equals(color)) {
//                sortedFaculties.add(faculty);
//            }
//        }
//        return sortedFaculties;
//    }
}
