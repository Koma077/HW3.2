package ru.hogwarts.school.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class AvatarController {
    private final AvatarService avatarService;

    private AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile file) throws IOException {
        avatarService.uploadAvatar(studentId, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Optional<Avatar> avatar = avatarService.findeAvatar(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(avatar.orElseThrow().getMediaType()));
        httpHeaders.setContentLength(avatar.get().getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(avatar.get().getData());

    }
    @GetMapping(value = "{id}/avatar-from-file")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Optional<Avatar> avatar = avatarService.findeAvatar(id);
        Path pathFile = Path.of(avatar.get().getFilePath());
        try(InputStream is = Files.newInputStream(pathFile)) {
            OutputStream os = response.getOutputStream();
            response.setStatus(200);
            response.setContentType(avatar.get().getMediaType());
            response.setContentLength((int) avatar.get().getFileSize());
            is.transferTo(os);
        }
    }
    @GetMapping(value = "page-of-avatars")
    public Collection<Avatar> getAvatarPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        return avatarService.getAvatarPage(page,size);
    }

}
