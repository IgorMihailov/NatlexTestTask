package com.task.GeologicalREST.service;

import com.task.GeologicalREST.entity.GeologicalClass;
import com.task.GeologicalREST.entity.Section;

import java.util.List;

public interface SectionService {

    Section saveSection(Section section);
    Section findSectionById(long id);
    List<Section> findAllSection();
    void deleteSectionById(long id);
    void deleteAllSections();
    boolean updateSection(Long id, Section newData);



}
