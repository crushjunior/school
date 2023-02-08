package ru.hogwarts.school;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.net.URI;
import java.security.cert.CertPathBuilder;
import java.util.Collection;
import java.util.Set;

import static javax.xml.xpath.XPathFactory.newInstance;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // с помощью этой аннтоации данные в БД будут обнуляться после каждого теста
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentTests {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createStudent() {
        Student student = givenStudent(1L, "Rob", 19);
        ResponseEntity<Student> response = whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student);
        thenStudentHasBeenCreated(response);
    }

    @Test
    public void getStudentById() {
        Student student = givenStudent(1L, "Rob", 19);
        ResponseEntity<Student> response = whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student);
        thenStudentHasBeenCreated(response);
        Student createdStudent = response.getBody();
        thenStudentWithIdHasBeenFound(createdStudent.getId(), createdStudent);
    }

    @Test
    public void findStudentByAge() {
        Student student19 = givenStudent(1L, "Rob", 19);
        Student student20 = givenStudent(2L, "Bor", 20);
        Student student21 = givenStudent(3L, "Sam", 21);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student19);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student20);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student21);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("age", "20");
        thenStudentsAreFoundByAge(queryParams, student20);
    }

    @Test
    public void findStudentByAgeBetween() {
        Student student19 = givenStudent(1L, "Rob", 19);
        Student student20 = givenStudent(2L, "Bor", 20);
        Student student21 = givenStudent(3L, "Sam", 21);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student19);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student20);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student21);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("min", "20");
        queryParams.add("max", "25");
        thenStudentsAreFoundByAgeForBetween(queryParams, student20, student21);
    }

    @Test
    public void testUpdate() {
        Student student = givenStudent(1L, "Rob", 19);
        ResponseEntity<Student> responseEntity = whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student);
        thenStudentHasBeenCreated(responseEntity);
        Student createdStudent = responseEntity.getBody();

        whenUpdatingStudent(createdStudent, 29, "Zohan");
        thenStudentHasBeenUpdated(createdStudent, 29, "Zohan");
    }

    @Test
    public void testDelete() {
        Student student = givenStudent(1L, "Rob", 19);
        ResponseEntity<Student> responseEntity = whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student);
        thenStudentHasBeenCreated(responseEntity);
        Student createdStudent = responseEntity.getBody();

        whenDeletingStudent(createdStudent);
        thenStudentNotFound(createdStudent);
    }

    @Test
    public void findAllStudents() {
        Student student19 = givenStudent(1L, "Rob", 19);
        Student student20 = givenStudent(2L, "Bor", 20);
        Student student21 = givenStudent(3L, "Sam", 21);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student19);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student20);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student21);

        thenAllStudentsAreFound(student19, student20, student21);
    }



    @Test
    public void findFacultyByStudentId() {
        Student studentRq = givenStudent(0L, "Rob", 19);
        Faculty facultyRq = new Faculty(0L, "asda", "wweew");

        // создаем факультет и получаем тот, что пришел в ответе, т.к. id может быть другим
        Faculty createdFaculty = whenSendingCreateFacultyRequest(getUriBuilder2().build().toUri(), facultyRq).getBody();


        // сеттим факультет в студента
        studentRq.setFaculty(createdFaculty);

        // создаем студента через контроллер
        Student createdStudent = studentController.createStudent(studentRq).getBody();

        // выполняем проверки
        thenFacultyOfStudentWasFound(createdStudent, createdFaculty);
    }

    private void thenFacultyOfStudentWasFound(Student student, Faculty faculty) {
        URI uri = getUriBuilder().path("/facultyByStudent/{id}").buildAndExpand(student.getId()).toUri();
        ResponseEntity<Faculty> foundFaculty = restTemplate.getForEntity(uri, Faculty.class);

        Assertions.assertThat(foundFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(foundFaculty.getBody()).isEqualTo(faculty);
    }

    private void thenAllStudentsAreFound(Student... students) {
        URI uri = getUriBuilder().path("/all").build().toUri();
        ResponseEntity<Set<Student>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<Student>>() {
                });

        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Set<Student> result = response.getBody();
        Assertions.assertThat(result).containsExactlyInAnyOrder(students);
    }

    private void thenStudentNotFound(Student createdStudent) {
        URI getUri = getUriBuilder().path("{id}").buildAndExpand(createdStudent.getId()).toUri();
        ResponseEntity<Student> emptyStudent = restTemplate.getForEntity(getUri, Student.class);

        Assertions.assertThat(emptyStudent.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private void whenDeletingStudent(Student createdStudent) {
        restTemplate.delete(getUriBuilder().path("{id}").buildAndExpand(createdStudent.getId()).toUri());
    }

    private void thenStudentHasBeenUpdated(Student createdStudent, int newAge, String newName) {
        URI getUri = getUriBuilder().cloneBuilder().path("/{id}").buildAndExpand(createdStudent.getId()).toUri();
        ResponseEntity<Student> updatedStudent = restTemplate.getForEntity(getUri, Student.class);

        Assertions.assertThat(updatedStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(updatedStudent.getBody()).isNotNull();
        Assertions.assertThat(updatedStudent.getBody().getAge()).isEqualTo(newAge);
        Assertions.assertThat(updatedStudent.getBody().getName()).isEqualTo(newName);
    }

    private void whenUpdatingStudent(Student student, int newAge, String newName) {
        student.setAge(newAge);
        student.setName(newName);
        restTemplate.put(getUriBuilder().build().toUri(), student);
    }

    private void thenStudentsAreFoundByAge(MultiValueMap<String, String> queryParams, Student... students) {
        URI uri = getUriBuilder().queryParams(queryParams).build().toUri();
        ResponseEntity<Set<Student>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<Student>>() {
                });

        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Set<Student> result = response.getBody();
        //resetIds(result);
        Assertions.assertThat(result).containsExactlyInAnyOrder(students);
    }

    private void thenStudentsAreFoundByAgeForBetween(MultiValueMap<String, String> queryParams, Student... students) {
        URI uri = getUriBuilder().path("/between").queryParams(queryParams).build().toUri();
        ResponseEntity<Set<Student>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<Student>>() {
                });

        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Set<Student> result = response.getBody();
        //resetIds(result);
        Assertions.assertThat(result).containsExactlyInAnyOrder(students);
    }

//    private void resetIds(Collection<Student> students) {
//        students.forEach(it -> it.setId(null));
//    }

    private void thenStudentWithIdHasBeenFound(long id, Student student) {
        URI uri = getUriBuilder().path("/{id}").buildAndExpand(id).toUri();
        ResponseEntity<Student> response = restTemplate.getForEntity(uri, Student.class);
        Assertions.assertThat(response.getBody()).isEqualTo(student);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private void thenStudentHasBeenCreated(ResponseEntity<Student> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
    }

    private UriComponentsBuilder getUriBuilder() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("/student");
    }

    private UriComponentsBuilder getUriBuilder2() { // билдер для Факультета
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("/faculty");
    }

    private Student givenStudent(Long id, String name, int age) {
        return new Student(id, name, age);
    }

    private ResponseEntity<Student> whenSendingCreateStudentRequest(URI uri, Student student) {
        return restTemplate.postForEntity(uri, student, Student.class);
    }
    private ResponseEntity<Faculty> whenSendingCreateFacultyRequest(URI uri, Faculty faculty) {
        return restTemplate.postForEntity(uri, faculty, Faculty.class);
    } // отправка реквеста для факультета

//    @Autowired
//    private StudentController studentController;

//    @Test
//    public void contextLoad() throws Exception {
//        Assertions.assertThat(studentController).isNotNull();
//    }
//
//    @Test
//    public void getAllStudents() throws Exception {
//        Assertions
//                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all", String.class))
//                .isNotNull();
//    }
//
//    @Test
//    public void testPostStudent() throws Exception {
//        Student student = new Student();
//        student.setId(1L);
//        student.setName("Bobik");
//        student.setAge(21);
//
//        Assertions
//                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
//                .isNotNull();
//    }
}
