package com.task.GeologicalREST.service;

import com.task.GeologicalREST.entity.Section;
import com.task.GeologicalREST.helper.ExcelHelper;
import com.task.GeologicalREST.repository.SectionRepository;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    SectionRepository sectionRepository;

    public void importFile(MultipartFile file) {
        try {
            List<Section> sections = ExcelHelper.excelToSections(file.getInputStream());
            sectionRepository.saveAll(sections);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream exportFile() {
        try {
            List<Section> sections = sectionRepository.findAll();
            ByteArrayInputStream in = ExcelHelper.sectionsToExcel(sections);
            return in;

        } catch (Exception e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

}
