package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FacultyController.class)
public class FacultyTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void saveAndFindStudent() throws Exception {
        final String name = "UGU";
        final String color = "grey";
        final long id = 1;

        Faculty faculty = new Faculty(id, name, color);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);


        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

   }

    @Test
    public void putStudent() throws Exception {
        final String name = "UGU";
        final String color = "grey";
        final long id = 1;
        final String newName = "sada";
        final String newColor = "safff";

        Faculty faculty = new Faculty(id, name, color);
        Faculty updatedFaculty = new Faculty(id, newName, newColor);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", newName);
        facultyObject.put("color", newColor);

        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(updatedFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.color").value(newColor));
    }

   @Test
    public void findFacultiesByColor() throws Exception {
       final String name = "UGU";
       final String color = "grey";
       final String name2 = "asd";
       final Long id2 = 2L;
       final Long id = 1L;

       Faculty faculty = new Faculty(id, name, color);
       Faculty faculty2 = new Faculty(id2, name2, color);

       when(facultyRepository.findFacultiesByColor(color)).thenReturn(Set.of(faculty, faculty2));

       mockMvc.perform(MockMvcRequestBuilders
                       .get("/faculty?color=" + color)
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(List.of(faculty, faculty2))));
   }

   @Test
    public void findFacultyByColorOrName() throws Exception {
       final Long id = 1L;
       final String name = "assa";
       final String caseName = "aSsA";
       final String color = "Red";
       final String caseColor = "reD";

       Faculty faculty = new Faculty(id, name, color);

       when(facultyRepository.findFacultyByColorIgnoreCaseOrNameIgnoreCase(caseColor, caseName)).thenReturn(faculty);

       mockMvc.perform(MockMvcRequestBuilders
                       .get("/faculty/allAndNameColor")
                       .queryParam("color", caseColor)
                       .queryParam("name", caseName)
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(faculty)));
   }

    @Test
    public void deleteFaculty() throws Exception {
        final String name = "UGU";
        final String color = "grey";
        final Long id = 1L;

        Faculty faculty = new Faculty(id, name, color);

        when(facultyRepository.getById(id)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(facultyRepository, atLeastOnce()).deleteById(id);
    }

    @Test
    public void findStudentsByFaculty() throws Exception {
        final String name = "UGU";
        final String color = "grey";
        final long id = 1;

        Faculty faculty = new Faculty(id, name, color);

//        JSONObject facultyObject = new JSONObject();
//        facultyObject.put("id", id);
//        facultyObject.put("name", name);
//        facultyObject.put("color", color);


        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/studentsByFaculty/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}

