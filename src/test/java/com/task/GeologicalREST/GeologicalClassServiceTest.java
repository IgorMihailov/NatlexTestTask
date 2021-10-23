package com.task.GeologicalREST;

import com.task.GeologicalREST.entity.GeologicalClass;
import com.task.GeologicalREST.entity.Section;
import com.task.GeologicalREST.repository.GeologicalClassRepository;
import com.task.GeologicalREST.repository.SectionRepository;
import com.task.GeologicalREST.service.IGeologicalClassService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class GeologicalClassServiceTest {

    @Autowired
    private IGeologicalClassService geologicalClassService;

    @Autowired
    private GeologicalClassRepository geologicalClassRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Test
    public void createGeologicalClass() throws NotFoundException {

        Section section = generateSection();
        sectionRepository.save(section);

        GeologicalClass geoClass = generateGeoClass();

        geologicalClassService.saveGeoClass(geoClass, section.getId());

    }

    @Test
    public void createGeologicalClassWrongSection() {

        Assertions.assertThrows(NotFoundException.class, () -> {
            GeologicalClass geoClass = generateGeoClass();
            geologicalClassService.saveGeoClass(geoClass, 123);
        });

    }

    @Test
    public void readGeologicalClass() {

        Section section = generateSection();
        sectionRepository.save(section);

        GeologicalClass geoClass = generateGeoClass();
        geoClass.setSection(section);

        geologicalClassRepository.save(geoClass);
        GeologicalClass savedGeoClass = geologicalClassService.findGeoClassById(geoClass.getId());

        Assertions.assertNotNull(savedGeoClass);

    }

    @Test
    public void updateGeologicalClass() {

        Section section = generateSection();
        sectionRepository.save(section);

        GeologicalClass geoClass = generateGeoClass();
        geoClass.setSection(section);

        geologicalClassRepository.save(geoClass);

        String newName = "UpdatedSection";
        geoClass.setName(newName);
        geologicalClassService.updateGeoClass(geoClass.getId(), geoClass);

        Optional<GeologicalClass> updatedGeoClass = geologicalClassRepository.findById(geoClass.getId());

        Assertions.assertEquals(newName, updatedGeoClass.get().getName());

    }

    @Test
    public void sectionsByGeoClassCode() {

        for (int i = 0; i < 5; i++) {

            Section section = generateSection();
            section.getGeologicalClasses().add(generateGeoClass());
            sectionRepository.save(section);

        }

        Section section = generateSection();
        GeologicalClass geoClass = generateGeoClass();
        geoClass.setCode("GC2");
        sectionRepository.save(section);

        List<Section> sections = geologicalClassService.findSectionsByGeoClassesCode("GC1");
        Assertions.assertEquals(5, sections.size());

    }

    private Section generateSection() {

        Section section = new Section();
        section.setName("Section1");

        List<GeologicalClass> geoClasses = new ArrayList<>();

        section.setGeologicalClasses(geoClasses);
        return  section;

    }

    private GeologicalClass generateGeoClass() {

        GeologicalClass geoClass = new GeologicalClass();
        geoClass.setName("GeoClass1");
        geoClass.setCode("GC1");

        return geoClass;

    }

}
