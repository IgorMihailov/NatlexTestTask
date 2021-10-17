package com.task.GeologicalREST.controller;

import com.task.GeologicalREST.message.ResponseMessage;
import com.task.GeologicalREST.service.FileService;
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
    public ResponseEntity<ResponseMessage> launchImport(@RequestParam("file") MultipartFile file) {

        String message = "";

        try {

            int jobId = fileService.importFile(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(Integer.toString(jobId)));

        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }

    }

    @GetMapping("/import/{id}")
    public ResponseEntity<ResponseMessage> getImportJobState(@PathVariable long id) {

        String jobState = fileService.getJobState(id, "Import");
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(jobState));

    }

    @GetMapping("/export")
    public ResponseEntity<ResponseMessage> launchExport() {
        String message = "";

        try {
            int jobId = fileService.exportFile();
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(Integer.toString(jobId)));

        } catch (Exception e) {
            message = "Could not export to file the file!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/export/{id}")
    public ResponseEntity<ResponseMessage> getExportJobState(@PathVariable long id) {

        String jobState = fileService.getJobState(id, "Export");
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(jobState));

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
