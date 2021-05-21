package com.example.fileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("http://localhost:8888/")
public class FileController {

    @Autowired
    private FileStorageService storageService;

    private final FileDBUsuariosRepository repository;

    FileController(FileDBUsuariosRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            FileDB a = storageService.store(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename() + ". Share via id: " + a.getId();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }


    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileDB fileDB = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    @GetMapping("/Usuarios")
    List<FileDbUsuarios> all() {
        return repository.findAll();
    }

    @PostMapping("/Usuarios")
    FileDbUsuarios newUser(@RequestBody FileDbUsuarios newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/Usuarios/{id}")
    FileDbUsuarios one(@PathVariable String id) {

        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/Usuarios/{id}")
    FileDbUsuarios replaceEmployee(@RequestBody FileDbUsuarios newUser, @PathVariable String id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newUser.getName());
                    employee.setGrupo(newUser.getGrupo());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/Usuarios/{id}")
    void deleteEmployee(@PathVariable String id) {
        repository.deleteById(id);
    }
}
