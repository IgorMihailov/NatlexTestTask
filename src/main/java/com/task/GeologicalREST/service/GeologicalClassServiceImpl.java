package com.task.GeologicalREST.service;

import com.task.GeologicalREST.entity.GeologicalClass;
import com.task.GeologicalREST.entity.Section;
import com.task.GeologicalREST.repository.GeologicalClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GeologicalClassServiceImpl implements GeologicalClassService {

    @Autowired
    GeologicalClassRepository geologicalClassRepository;

    @Override
    public GeologicalClass saveGeoClass(GeologicalClass geoClass) {
        return geologicalClassRepository.save(geoClass);
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
        saveGeoClass(oldGeoClass);

        return true;
    }

    @Override
    public List<Section> findSectionsByGeoClassesCode(String code) {

        // Select Geological Classes by code and convert its Sections to List
        return geologicalClassRepository.findByCode(code).stream()
                .map((g) -> g.getSection())
                .collect(Collectors.toList());
    }

}
