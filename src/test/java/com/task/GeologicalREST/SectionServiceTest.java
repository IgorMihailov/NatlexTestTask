package com.task.GeologicalREST;

import com.task.GeologicalREST.entity.GeologicalClass;
import com.task.GeologicalREST.entity.Section;
import com.task.GeologicalREST.repository.SectionRepository;
import com.task.GeologicalREST.service.impl.SectionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class SectionServiceTest {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private SectionRepository sectionRepository;

    @Test
    public void createSection() {

        Section section = generateSection();
        Section savedSection = sectionService.saveSection(section);

        Assertions.assertEquals(section, savedSection);

    }

    @Test
    public void readSection() {

        Section savedSection = sectionRepository.save(generateSection());
        Section readSection = sectionService.findSectionById(savedSection.getId());

        Assertions.assertNotNull(readSection);

    }

    @Test
    public void updateSection() {

        Section savedSection = sectionRepository.save(generateSection());
        savedSection.setName("UPDATED");
        boolean resultOfUpdate = sectionService.updateSection(savedSection.getId(), savedSection);

        Assertions.assertTrue(resultOfUpdate);

    }

    @Test
    public void deleteSection() {

        Section savedSection = sectionRepository.save(generateSection());
        sectionService.deleteSectionById(savedSection.getId());

        Optional<Section> deletedSection = sectionRepository.findById(savedSection.getId());

        Assertions.assertTrue(deletedSection.isEmpty());

    }

    private Section generateSection() {

        Section section = new Section();
        section.setName("Section1");

        List<GeologicalClass> geoClasses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {

            GeologicalClass geoClass = new GeologicalClass();
            geoClass.setName("GeoClass " + i);
            geoClass.setCode("GC" + i);
            geoClass.setSection(section);

            geoClasses.add(geoClass);

        }

        section.setGeologicalClasses(geoClasses);
        return  section;

    }

}
