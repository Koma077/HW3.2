package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findeStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(long id, Student student) {
        return studentRepository.save(student);
    }

    public void removeStudent(long id) {
        studentRepository.deleteById(id);

    }

    public List<Student> findByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findStudentByAgeBetween(min, max);
    }

    public Faculty findStudentsByFacultyId(Long id) {
        Student student = (Student) studentRepository.findStudentsByFacultyId(id);
        if (student == null) {
            return null;
        }
        return student.getFaculty();
    }

    public Integer getAllStudents() {
        return studentRepository.getAllStudent();
    }

    public Integer getAvgAgeOfStudents() {
        return studentRepository.getAvgAgeOfStudents();
    }

    public List<Student> getFiveStudents() {
        return studentRepository.getFiveStudents();
    }


}
