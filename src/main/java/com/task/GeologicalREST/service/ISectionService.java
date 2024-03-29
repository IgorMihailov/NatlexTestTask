package com.task.GeologicalREST.service;
import com.task.GeologicalREST.entity.Section;

import java.util.List;

public interface ISectionService {
    Section saveSection(Section section);
    Section findSectionById(long id);
    List<Section> findAllSection();
    void deleteSectionById(long id);
    void deleteAllSections();
    Section updateSection(Long id, Section newData);
}
