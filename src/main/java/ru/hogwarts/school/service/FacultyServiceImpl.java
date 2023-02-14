package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.Stream;

@Service
public class FacultyServiceImpl implements FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for add faculty");
        Faculty newFaculty = facultyRepository.save(faculty);
        logger.debug("{} was added to application", newFaculty);
        return newFaculty;
    }

    @Override
    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for find faculty");
        Faculty foundFaculty = facultyRepository.findById(id).get();
        logger.debug("User want to find by id {} ", foundFaculty);
        return foundFaculty;
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        Faculty editFaculty = facultyRepository.save(faculty);
        logger.debug("User want to edit {} ", editFaculty);
        return editFaculty;
    }

    @Override
    public void deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty");
        logger.debug("User want to delete {} ", facultyRepository.findById(id));
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        logger.info("Was invoked method for find faculties by color");
        Collection<Faculty> foundFaculties = facultyRepository.findFacultiesByColor(color);
        logger.debug("User want to find these faculties: {} ", foundFaculties);
        return foundFaculties;
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for get all faculties");
        Collection<Faculty> foundFaculties = facultyRepository.findAll();
        logger.debug("User want to get all faculties: {} ", foundFaculties);
        return foundFaculties;
    }

    @Override
    public Collection<Student> getStudentsByFacultyId(long id) {
        logger.info("Was invoked method for get students by faculty");
        Collection<Student> studentsOfFaculty = facultyRepository.findById(id).get().getStudents();
        logger.debug("User want to get all students of {}: {} ", facultyRepository.findById(id), studentsOfFaculty);
        return studentsOfFaculty;
    }

    public Faculty findByColorOrName(String color, String name) {
        logger.info("Was invoked method for get faculty by color or name");
        Faculty faculty = facultyRepository.findFacultyByColorIgnoreCaseOrNameIgnoreCase(color, name);
        logger.debug("User want to get faculty: {} ", faculty);
        return faculty;
    }

    public String findLongestNameOfFaculties() {
        logger.info("Was invoked method for get longest name of faculty");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("Not found");
    }

    public Integer testParallelStream() {
        logger.info("Was invoked method for test parallel Stream");
        int sum = Stream.iterate(1, a -> a +1) .limit(1_000_000) .parallel() .reduce(0, (a, b) -> a + b );
        return sum;
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
