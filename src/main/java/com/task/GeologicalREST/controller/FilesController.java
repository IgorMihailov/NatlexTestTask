package com.task.GeologicalREST.controller;

import com.task.GeologicalREST.enums.JobTask;
import com.task.GeologicalREST.service.impl.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api")
public class FilesController {

    @Autowired
    FileService fileService;

    @PostMapping("/import")
    public ResponseEntity<String> launchImport(@RequestParam("file") MultipartFile file) {

        try {
            int jobId = fileService.importFile(file);
            return ResponseEntity.status(HttpStatus.CREATED).body("Job ID: " + jobId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not upload the file: " + file.getOriginalFilename() + "!");
        }

    }

    @GetMapping("/import/{id}")
    public ResponseEntity<String> getImportJobState(@PathVariable long id) {

        String jobState = fileService.getJobState(id, JobTask.IMPORT.name());
        return ResponseEntity.status(HttpStatus.OK).body(jobState);

    }

    @GetMapping("/export")
    public ResponseEntity<String> launchExport() {

        try {
            int jobId = fileService.exportFile();
            return ResponseEntity.status(HttpStatus.CREATED).body("Job ID: " + jobId);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not export to file!");
        }
    }

    @GetMapping("/export/{id}")
    public ResponseEntity<String> getExportJobState(@PathVariable long id) {

        String jobState = fileService.getJobState(id, JobTask.EXPORT.name());
        return ResponseEntity.status(HttpStatus.OK).body(jobState);

    }

    @GetMapping("/export/file/{id}")
    public ResponseEntity<InputStreamResource> getExportedFile(@PathVariable long id) {

        InputStreamResource file = new InputStreamResource(new ByteArrayInputStream(fileService.getFile(id).toByteArray()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Sections.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

    }

}
