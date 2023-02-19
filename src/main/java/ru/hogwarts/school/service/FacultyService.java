package ru.hogwarts.school.service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;



@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    private Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.debug("addFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findeFaculty(long id) {
        logger.debug("findeFaculty");
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(long id, Faculty faculty) {
        logger.debug("editFaculty");
        return facultyRepository.save(faculty);
    }

    public void removeFaculty(long id) {
        logger.debug("removeFaculty");
        facultyRepository.deleteById(id);
    }

    public List<Faculty> findByColor(String color) {
        logger.debug("findByColor");
        return facultyRepository.findByColor(color);
    }

    public List<Faculty> findFacultiesByColorOrName(String color, String name) {
        logger.debug("findFacultiesByColorOrName");
        return facultyRepository.findFacultiesByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    public Collection<Student> findeFacultyByStudentId(String name) {
        logger.debug("findeFacultyByStudentId");
        Faculty faculty = facultyRepository.findFacultyByNameIgnoreCase(name);
        return faculty.getStudents();

    }

    public Faculty getLongestNameOfTheFaculty(){
        List<Faculty> faculties = facultyRepository.findAll();
        return faculties.stream().max(Comparator.comparing(faculty -> faculty.getName().length())).get();
    }

}
