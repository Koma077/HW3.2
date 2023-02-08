package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }


    @Test
    public void findStudentById() {
        Assertions.assertNotNull(testRestTemplate.getForObject("http://localhost:" +
                port + "/student/6", String.class));
    }

    @Test
    public void createStudent() {
        Student student = new Student();
        student.setName("name");
        student.setAge(20);
        assertThat(testRestTemplate.postForObject("http://localhost:" + port + "/", student,
                String.class)).isNotNull();
    }


    @Test
    public void deleteStudent() {
        testRestTemplate.delete("http://localhost:" + port + "/student/{id}", 19);
        Assertions.assertNull(testRestTemplate.getForObject("http://localhost:" +
                port + "/student/8", String.class));
    }
}
