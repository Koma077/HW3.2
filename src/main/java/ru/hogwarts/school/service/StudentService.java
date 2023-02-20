package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.context.Theme;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import java.util.OptionalDouble;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private Logger logger = LoggerFactory.getLogger(StudentService.class);

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

    public List<String> getStudentsWhoHaveTheLetterA() {
        List<Student> students = studentRepository.findAll();
        return students.stream().parallel().filter(s -> s.getName().substring(0, 1).equalsIgnoreCase("a"))
                .map(s -> s.getName().toUpperCase()).toList();
    }

    public OptionalDouble getAverageAgeOfAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().parallel().mapToDouble(Student::getAge).average();
    }


    public void  getStudentsByParallelThreads(){
        List<Student> students = new ArrayList<>();
        students.addAll(studentRepository.findAll());
        System.out.println(students.get(0));
        System.out.println(students.get(1));
        new Thread(() -> {
            System.out.println(students.get(2));
            System.out.println(students.get(3));
        }).start();
        new Thread(() -> {
            System.out.println(students.get(4));
            System.out.println(students.get(5));
        }).start();
    }


    public synchronized void getStudentsBySynchronizedParallelThreads() {
        List<Student> students = new ArrayList<>();
        students.addAll(studentRepository.findAll());
        printStudent(students.get(0));
        printStudent(students.get(1));
        new Thread(() -> {
            printStudent(students.get(2));
            printStudent(students.get(3));
        }).start();
        new Thread(() -> {
            printStudent(students.get(4));
            printStudent(students.get(5));
        }).start();
    }

    public void printStudent(Student student){
        synchronized (studentRepository){
            System.out.println(student);
        }
    }


}
