package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface FacultyService {
    public Faculty addFaculty(Faculty faculty);

    public Faculty findFaculty(long id);

    public Faculty editFaculty(Faculty faculty);

    public void deleteFaculty(long id);

    public Collection<Faculty> getAllFaculties();

    public Collection<Student> getStudentsByFacultyId(long id);



    //    public Collection<Faculty> findByColor(String color);

}
