package com.task.GeologicalREST.service;

import com.task.GeologicalREST.entity.Section;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Future;

public interface IExcelHelperService {

    Future<Void> excelToSections(InputStream is, long jobId);
    Future<Void> sectionsToExcel(List<Section> sections, long jobId);
    ByteArrayOutputStream getFileById(long jobId);

}
