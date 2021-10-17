package com.task.GeologicalREST.service;

import com.task.GeologicalREST.entity.Job;
import com.task.GeologicalREST.repository.JobRepository;
import com.task.GeologicalREST.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FileService {

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    ExcelHelperService excelHelperService;

    @Autowired
    JobRepository jobRepository;

    AtomicInteger counter = new AtomicInteger();

    public String getJobState(long id, String task) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isPresent() && job.get().getTask().equals(task)) {
            return job.get().getState();
        } else {
            return "No such job for import!";
        }
    }

    public int importFile(MultipartFile file){
        int jobId = counter.incrementAndGet();

        try {
           excelHelperService.excelToSections(file.getInputStream(), jobId);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }

        return jobId;
    }

    public int exportFile() {
        int jobId = counter.incrementAndGet();

        try {
            excelHelperService.sectionsToExcel(sectionRepository.findAll(), jobId);
        } catch (Exception e) {
            throw new RuntimeException("fail to export excel data: " + e.getMessage());
        }

        return jobId;
    }

    public ByteArrayOutputStream getFile(long id) {
        return excelHelperService.getFileById(id);
    }

}
