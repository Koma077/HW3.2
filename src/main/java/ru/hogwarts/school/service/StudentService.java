package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        logger.debug("addStudent");
        return studentRepository.save(student);
    }

    public Student findeStudent(long id) {
        logger.debug("findeStudent");
        return studentRepository.findById(id).get();
    }

    public Student editStudent(long id, Student student) {
        logger.debug("editStudent");
        return studentRepository.save(student);
    }

    public void removeStudent(long id) {
        logger.debug("removeStudent");
        studentRepository.deleteById(id);

    }

    public List<Student> findByAge(int age) {
        logger.debug("findByAge");
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween(int min, int max) {
        logger.debug("findByAgeBetween");
        return studentRepository.findStudentByAgeBetween(min, max);
    }

    public Faculty findStudentsByFacultyId(Long id) {
        logger.debug("findStudentsByFacultyId");
        Student student = (Student) studentRepository.findStudentsByFacultyId(id);
        if (student == null) {
            return null;
        }
        return student.getFaculty();
    }

    public Integer getAllStudents() {
        logger.debug("getAllStudents");
        return studentRepository.getAllStudent();
    }

    public Integer getAvgAgeOfStudents() {
        logger.debug("getAvgAgeOfStudents");
        return studentRepository.getAvgAgeOfStudents();
    }

    public List<Student> getFiveStudents() {
        logger.debug("getFiveStudents");
        return studentRepository.getFiveStudents();
    }


}
