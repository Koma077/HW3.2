package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;



@Service
public class FacultyService {
    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private long count = 0;

    public Faculty addFaculty(Faculty faculty){
        faculty.setId(count ++);
        faculty.setId(faculty.getId());
        return faculty;
    }
    public Faculty findeFaculty(long id){
        return faculties.get(id);
    }
    public Faculty editFaculty(long id, Faculty faculty){
        if(!faculties.containsKey(id)){
            return null;
        }
        faculties.put(id, faculty);
        return faculty;
    }
    public Faculty removeFaculty(long id){
        return faculties.remove(id);
    }
}
