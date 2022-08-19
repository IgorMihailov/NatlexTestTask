package com.task.GeologicalREST;

import static org.mockito.Mockito.*;
import com.task.GeologicalREST.entity.Section;
import com.task.GeologicalREST.repository.SectionRepository;
import com.task.GeologicalREST.service.ISectionService;
import com.task.GeologicalREST.service.impl.SectionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SectionServiceTest {

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private ISectionService sectionService;

    private Section section;

    private static final String SECTION_NAME = "Section";

    private static final String SECTION_NEW_NAME = "UpdatedSection";

    @BeforeEach
    public void init() {
        sectionService = new SectionService(sectionRepository);
        section = generateSection();
    }

    @Test
    public void createSection() {
        when(sectionRepository.save(section)).thenReturn(section);
        Section savedSection = sectionService.saveSection(section);
        verify(sectionRepository, times(1)).save(section);
        Assertions.assertEquals(section, savedSection);
    }

    @Test
    public void updateSection() {
        when(sectionRepository.save(section)).thenReturn(section);
        when(sectionRepository.findById(section.getId())).thenReturn(Optional.ofNullable(section));
        Section savedSection = sectionService.saveSection(section);
        savedSection.setName(SECTION_NEW_NAME);
        sectionService.updateSection(savedSection.getId(), savedSection);
        Optional<Section> updatedSection = sectionRepository.findById(savedSection.getId());
        Assertions.assertEquals(updatedSection.get().getName(), SECTION_NEW_NAME);
        verify(sectionRepository, times(2)).save(section);
        verify(sectionRepository, times(2)).findById(section.getId());
    }

    @Test
    public void deleteSection() {
        when(sectionRepository.save(section)).thenReturn(section);
        Section savedSection = sectionRepository.save(section);
        sectionService.deleteSectionById(savedSection.getId());
        verify(sectionRepository, times(1)).deleteById(section.getId());
    }

    private Section generateSection() {
        Section section = new Section();
        section.setName(SECTION_NAME);
        return section;
    }

}
