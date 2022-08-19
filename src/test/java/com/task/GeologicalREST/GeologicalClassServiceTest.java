package com.task.GeologicalREST;

import static org.mockito.Mockito.*;
import com.task.GeologicalREST.entity.GeologicalClass;
import com.task.GeologicalREST.entity.Section;
import com.task.GeologicalREST.repository.GeologicalClassRepository;
import com.task.GeologicalREST.repository.SectionRepository;
import com.task.GeologicalREST.service.IGeologicalClassService;
import com.task.GeologicalREST.service.impl.GeologicalClassService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GeologicalClassServiceTest {

    @Mock
    private IGeologicalClassService geologicalClassService;

    @Mock
    private GeologicalClassRepository geologicalClassRepository;

    @Mock
    private SectionRepository sectionRepository;

    private Section section;

    private GeologicalClass geologicalClass;

    private static final String SECTION_NAME = "Section";

    private static final String GEOCLASS_NAME = "GeoClass";

    private static final String GEOCLASS_NEW_NAME = "GeoClass1";

    @BeforeEach
    public void init() {
        geologicalClassService = new GeologicalClassService(geologicalClassRepository, sectionRepository);
        section = generateSection();
        geologicalClass = generateGeoClass(section);
        geologicalClass.setSection(section);
    }

    @Test
    public void createGeologicalClass() throws NotFoundException {
        when(sectionRepository.findById(section.getId())).thenReturn(Optional.ofNullable(section));
        when(geologicalClassRepository.save(geologicalClass)).thenReturn(geologicalClass);

        geologicalClassService.saveGeoClass(geologicalClass, section.getId());
        verify(geologicalClassRepository).save(geologicalClass);
    }

    @Test
    public void createGeologicalClassWrongSection() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            geologicalClassService.saveGeoClass(geologicalClass, 123);
        });
    }

    @Test
    public void updateGeologicalClass() throws NotFoundException {
        when(sectionRepository.findById(section.getId())).thenReturn(Optional.ofNullable(section));
        when(geologicalClassRepository.save(geologicalClass)).thenReturn(geologicalClass);
        when(geologicalClassRepository.findById(geologicalClass.getId())).thenReturn(Optional.ofNullable(geologicalClass));
        GeologicalClass savedClass = geologicalClassService.saveGeoClass(geologicalClass, section.getId());
        savedClass.setName(GEOCLASS_NEW_NAME);
        geologicalClassService.updateGeoClass(savedClass.getId(), savedClass);
        Optional<GeologicalClass> updatedSection = geologicalClassRepository.findById(geologicalClass.getId());
        Assertions.assertEquals(updatedSection.get().getName(), GEOCLASS_NEW_NAME);
        verify(geologicalClassRepository, times(2)).save(geologicalClass);
        verify(geologicalClassRepository, times(2)).findById(geologicalClass.getId());
    }

    private Section generateSection() {
        Section section = new Section();
        section.setName(SECTION_NAME);
        List<GeologicalClass> geoClasses = new ArrayList<>();
        section.setGeologicalClasses(geoClasses);
        return section;
    }

    private GeologicalClass generateGeoClass(Section section) {
        GeologicalClass geoClass = new GeologicalClass();
        geoClass.setName(GEOCLASS_NAME);
        geoClass.setCode("GC1");
        geoClass.setSection(section);
        return geoClass;
    }

}
