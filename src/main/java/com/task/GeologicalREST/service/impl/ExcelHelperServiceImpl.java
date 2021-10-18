package com.task.GeologicalREST.service.impl;

import com.task.GeologicalREST.entity.GeologicalClass;
import com.task.GeologicalREST.entity.Job;
import com.task.GeologicalREST.entity.Section;
import com.task.GeologicalREST.enums.JobState;
import com.task.GeologicalREST.enums.JobTask;
import com.task.GeologicalREST.repository.JobRepository;
import com.task.GeologicalREST.repository.SectionRepository;
import com.task.GeologicalREST.service.ExcelHelperService;
import javassist.bytecode.ByteArray;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Service
public class ExcelHelperServiceImpl implements ExcelHelperService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    SectionRepository sectionRepository;

    ConcurrentHashMap<Long, ByteArrayOutputStream> exportFiles = new ConcurrentHashMap<>();

    @Async
    @Override
    public Future<Void> excelToSections(InputStream is, long jobId) {

        Job job = new Job();
        job.setState(JobState.IN_PROGRESS.name());
        job.setTask(JobTask.IMPORT.name());
        job.setId(jobId);

        job = jobRepository.save(job);

        try {

            //Thread.sleep(10000);
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rows = sheet.iterator();

            List<Section> sections = new LinkedList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // Skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Section section = new Section();
                List<GeologicalClass> sectionClasses = new LinkedList<>();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    // Skip empty cells
                    if (currentCell.getStringCellValue().isEmpty()) {
                        continue;
                    }

                    // Cell with section name
                    if (cellIdx == 0) {
                        section.setName(currentCell.getStringCellValue());
                        cellIdx++;
                        continue;
                    }

                    // Cell with geo class name
                    if (cellIdx % 2 == 1) {
                        GeologicalClass geoClass = new GeologicalClass();
                        geoClass.setName(currentCell.getStringCellValue());
                        geoClass.setSection(section);
                        sectionClasses.add(geoClass);
                    }

                    // Cell with geo class code
                    if (cellIdx % 2 == 0) {

                        // Take last added element and set its code
                        sectionClasses.get(sectionClasses.size() - 1).setCode(currentCell.getStringCellValue());
                    }

                    cellIdx++;
                }

                section.setGeologicalClasses(sectionClasses);
                sections.add(section);
            }

            workbook.close();

            sectionRepository.saveAll(sections);
            job.setState(JobState.DONE.name());
            jobRepository.save(job);
            return new AsyncResult<>(null);

        } catch (IOException e) {
            job.setState(JobState.ERROR.name());
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    @Async
    @Override
    public Future<Void> sectionsToExcel(List<Section> sections, long jobId){

        Job job = new Job();
        job.setState(JobState.IN_PROGRESS.name());
        job.setTask(JobTask.EXPORT.name());
        job.setId(jobId);

        job = jobRepository.save(job);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

            // Create a new Excel sheet
            Sheet sheet = workbook.createSheet("Sections");
            sheet.setDefaultColumnWidth(30);

            // Create header row
            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("Section name");

            Integer maxGeoCount = sections.stream()
                    .map(s -> s.getGeologicalClasses().size())
                    .max(Integer::compareTo).get();

            int classNum = 1;
            for (int i = 1; i < maxGeoCount * 2; i+=2) {
                header.createCell(i).setCellValue("Class " + classNum + " name");
                header.createCell(i+1).setCellValue("Class " + classNum + " code");
                classNum++;
            }

            int rowIdx = 1;

            for (Section section : sections) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(section.getName());

                int cellIdx = 1;
                for (GeologicalClass geoClass : section.getGeologicalClasses()) {
                    row.createCell(cellIdx++).setCellValue(geoClass.getName());
                    row.createCell(cellIdx++).setCellValue(geoClass.getCode());
                }

            }

            workbook.write(out);
            exportFiles.put(jobId, out);
            job.setState(JobState.DONE.name());
            jobRepository.save(job);

            return new AsyncResult<>(null);
        } catch (IOException e) {
            job.setState(JobState.ERROR.name());
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    @Override
    public ByteArrayOutputStream getFileById(long jobId) {

        Optional<Job> job = jobRepository.findById(jobId);
        if (job.get().getState().equals(JobState.DONE.name())) {
            return exportFiles.get(jobId);
        } else {
            throw new RuntimeException("File isn't ready!");
        }
    }
}

