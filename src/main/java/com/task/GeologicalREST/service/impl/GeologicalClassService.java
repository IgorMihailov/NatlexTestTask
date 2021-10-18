package com.task.GeologicalREST.service.impl;

import com.task.GeologicalREST.entity.GeologicalClass;
import com.task.GeologicalREST.entity.Section;
import com.task.GeologicalREST.repository.GeologicalClassRepository;
import com.task.GeologicalREST.repository.SectionRepository;
import com.task.GeologicalREST.service.IGeologicalClassService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GeologicalClassService implements IGeologicalClassService {

    @Autowired
    GeologicalClassRepository geologicalClassRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Override
    public void saveGeoClass(GeologicalClass geoClass, long sectionId) throws NotFoundException {

        Optional<Section> section = sectionRepository.findById(sectionId);

        if (section.isPresent()) {

            // Add geoClass to section
            Section updSection = section.get();
            List<GeologicalClass> geoClasses = updSection.getGeologicalClasses();

            geoClass.setSection(updSection);

            geoClasses.add(geoClass);
            updSection.setGeologicalClasses(geoClasses);
            sectionRepository.save(updSection);

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
    public void deleteGeoClassById(long id) {
        geologicalClassRepository.deleteById(id);
    }

    @Override
    public void deleteAllGeoClasses() {
        geologicalClassRepository.deleteAll();
    }

    @Override
    public boolean updateGeoClass(Long id, GeologicalClass newGeoClass) {
        Optional<GeologicalClass> oldData = geologicalClassRepository.findById(id);

        if (oldData.isEmpty()) {
            return false;
        }

        GeologicalClass oldGeoClass = oldData.get();

        oldGeoClass.setName(newGeoClass.getName());
        oldGeoClass.setCode(newGeoClass.getName());
        geologicalClassRepository.save(oldGeoClass);

        return true;
    }

    @Override
    public List<Section> findSectionsByGeoClassesCode(String code) {

        // Select geological classes by code and convert its Sections to List
        return geologicalClassRepository.findByCode(code).stream()
                .map((g) -> g.getSection())
                .collect(Collectors.toList());
    }

}
