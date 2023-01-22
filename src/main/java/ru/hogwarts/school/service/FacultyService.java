package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    public Faculty addFaculty(Faculty faculty);

    public Faculty findFaculty(long id);

    public Faculty editFaculty(Faculty faculty);

    public void deleteFaculty(long id);

//    public Collection<Faculty> findByColor(String color);

    public Collection<Faculty> getAllFaculties();

}
