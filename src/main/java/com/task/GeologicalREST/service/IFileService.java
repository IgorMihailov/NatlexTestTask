package com.task.GeologicalREST.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

public interface IFileService {
    String getJobState(long id, String task);
    int importFile(MultipartFile file);
    int exportFile();
    ByteArrayOutputStream getFile(long id);
}
