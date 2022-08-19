package com.task.GeologicalREST.service.impl;

import com.task.GeologicalREST.entity.GeologicalClass;
import com.task.GeologicalREST.entity.Section;
import com.task.GeologicalREST.repository.GeologicalClassRepository;
import com.task.GeologicalREST.repository.SectionRepository;
import com.task.GeologicalREST.service.IGeologicalClassService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GeologicalClassService implements IGeologicalClassService {

    private final GeologicalClassRepository geologicalClassRepository;

    private final SectionRepository sectionRepository;

    public GeologicalClassService(GeologicalClassRepository geologicalClassRepository, SectionRepository sectionRepository) {
        this.geologicalClassRepository = geologicalClassRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    public GeologicalClass saveGeoClass(GeologicalClass geoClass, long sectionId) throws NotFoundException {
        Optional<Section> section = sectionRepository.findById(sectionId);
        if (section.isPresent()) {
            Section updSection = section.get();
            List<GeologicalClass> geoClasses = updSection.getGeologicalClasses();
            geoClass.setSection(updSection);
            geoClasses.add(geoClass);
            updSection.setGeologicalClasses(geoClasses);
            sectionRepository.save(updSection);
            return geologicalClassRepository.save(geoClass);
        } else {
            throw new NotFoundException("Section not found");
        }
    }

    @Override
    public GeologicalClass findGeoClassById(long id) {
        return geologicalClassRepository.findById(id).get();
    }

    @Override
    public List<GeologicalClass> findAllGeoClasses() {
        return geologicalClassRepository.findAll();
    }

    @Override
    public GeologicalClass updateGeoClass(Long id, GeologicalClass geoClass) {
        Optional<GeologicalClass> oldData = geologicalClassRepository.findById(id);
        GeologicalClass oldGeoClass = oldData.get();

        oldGeoClass.setName(geoClass.getName());
        oldGeoClass.setCode(geoClass.getCode());
        return geologicalClassRepository.save(oldGeoClass);
    }

    @Override
    public List<Section> findSectionsByGeoClassesCode(String code) {

        // Select geological classes by code and convert their Sections to List
        return geologicalClassRepository.findByCode(code).stream()
                .map(GeologicalClass::getSection)
                .distinct()
                .collect(Collectors.toList());
    }

}
