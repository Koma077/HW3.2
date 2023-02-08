package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    List<Student> findStudentByAgeBetween(int min, int max);

    List<Student> findStudentsByFacultyId(long id);

    @Query(value = "SELECT count(id) FROM student", nativeQuery = true)
    Integer getAllStudent();

    @Query(value = "SELECT avg (age) from student", nativeQuery = true)
    Integer getAvgAgeOfStudents();

    @Query(value = "select * from student order by id desc limit 5", nativeQuery = true)
    List<Student> getFiveStudents();


}
