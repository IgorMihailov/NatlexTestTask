package com.task.GeologicalREST.service.impl;

import com.task.GeologicalREST.entity.GeologicalClass;
import com.task.GeologicalREST.entity.Section;
import com.task.GeologicalREST.repository.GeologicalClassRepository;
import com.task.GeologicalREST.repository.SectionRepository;
import com.task.GeologicalREST.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    GeologicalClassRepository geologicalClassRepository;

    @Override
    public Section saveSection(Section section) {

        for (GeologicalClass geoClass : section.getGeologicalClasses()) {
            geoClass.setSection(section);
        }

        return sectionRepository.save(section);
    }

    @Override
    public Section findSectionById(long id) {
        return sectionRepository.findById(id).get();
    }

    @Override
    public List<Section> findAllSection() {
        return sectionRepository.findAll();
    }

    @Override
    public void deleteSectionById(long id) {
        sectionRepository.deleteById(id);
    }

    @Override
    public void deleteAllSections() {
        sectionRepository.deleteAll();
    }

    @Override
    public boolean updateSection(Long id, Section newSection) {

        Optional<Section> oldData = sectionRepository.findById(id);

        if (oldData.isEmpty()) {
            return false;
        }

        Section oldSection = oldData.get();
        oldSection.setName(newSection.getName());
        saveSection(oldSection);

        return true;
    }

}
