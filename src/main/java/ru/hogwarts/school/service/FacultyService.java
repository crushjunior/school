package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface FacultyService {
    public Faculty addFaculty(Faculty faculty);

    public Faculty findFaculty(long id);

    public Faculty editFaculty(long id, Faculty faculty);

    public Faculty deleteFaculty(long id);

    public Collection<Faculty> findByColor(String color);

}
