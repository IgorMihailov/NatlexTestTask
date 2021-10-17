package com.task.GeologicalREST.service;

import com.task.GeologicalREST.entity.GeologicalClass;
import com.task.GeologicalREST.entity.Section;

import java.util.List;

public interface GeologicalClassService {

    GeologicalClass saveGeoClass(GeologicalClass geoClass);
    GeologicalClass findGeoClassById(long id);
    List<GeologicalClass> findAllGeoClasses();
    void deleteGeoClassById(long id);
    void deleteAllGeoClasses();
    boolean updateGeoClass(Long id, GeologicalClass geologicalClass);
    List<Section> findSectionsByGeoClassesCode(String code);
}
