package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    @Value("${Avatar.cover.dir.path}")
    private String coversDir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    private final FacultyRepository facultyRepository;

    private Logger logger = LoggerFactory.getLogger(AvatarService.class);



    public AvatarService(StudentService studentService, AvatarRepository avatarRepository, FacultyRepository facultyRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
        this.facultyRepository = facultyRepository;
    }

    public Optional<Avatar> findAvatar(Long id) {
        logger.debug("findAvatar");
        return avatarRepository.findAvatarByStudentId(id);
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.debug("uploadAvatar");
        Student student = studentService.findeStudent(studentId);
        Path filePath = Path.of(coversDir, student + "." + getExtensions(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream()) {
            OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
            bis.transferTo(bos);

        }
        Avatar avatar = new Avatar();
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatar.setStudent(studentService.findeStudent(studentId));
        avatarRepository.save(avatar);

    }

    public Collection<Avatar> getAvatarPage(Integer page, Integer size) {
        logger.debug("getAvatarPage");
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return avatarRepository.findAll(pageRequest).getContent();
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")) + 1;
    }
}
