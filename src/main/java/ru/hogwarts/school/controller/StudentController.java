package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Stream;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findeStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student, @PathVariable Long id) {
        Student student1 = studentService.editStudent(id, student);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("by-age")
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(required = false) int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("by-age-between")
    public Collection<Student> findByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("by-faculty-id")
    public ResponseEntity<String> findStudentsByFacultyId(@RequestParam Long id) {
        String faculty = String.valueOf(studentService.findStudentsByFacultyId(id));
        if (faculty != null) {
            return ResponseEntity.ok(faculty);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("all-student")
    public Integer getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("avg-age-of-students")
    public Integer getAvgAgeOfStudents() {
        return studentService.getAvgAgeOfStudents();
    }

    @GetMapping("five-students")
    public List<Student> getFiveStudents() {
        return studentService.getFiveStudents();
    }

    @GetMapping("students-by-letter-a")
    public List<String> getStudentsWhoHaveTheLetterA() {
        return studentService.getStudentsWhoHaveTheLetterA();
    }

    @GetMapping("average-age")
    public OptionalDouble getAverageAgeOfAllStudents() {
        return studentService.getAverageAgeOfAllStudents();
    }

    @GetMapping("sum")
    public int getSum() {
        return Stream.iterate(1, a -> a + 1).limit(1_000_000).parallel().mapToInt(Integer::intValue).sum();
    }

    @GetMapping("six-students-by-parallel-threads")
    public void getStudentsByParallelThreads() {
        studentService.getStudentsByParallelThreads();
    }

    @GetMapping("six-students-by-synchronized-parallel-threads")
    public void getStudentsBySynchronizedParallelThreads(){
        studentService.getStudentsBySynchronizedParallelThreads();
    }
}
