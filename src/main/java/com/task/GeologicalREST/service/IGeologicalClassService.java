package com.task.GeologicalREST.service;

import com.task.GeologicalREST.entity.GeologicalClass;
import com.task.GeologicalREST.entity.Section;
import javassist.NotFoundException;

import java.util.List;

public interface IGeologicalClassService {
    GeologicalClass saveGeoClass(GeologicalClass geoClass, long sectionId) throws NotFoundException;
    GeologicalClass findGeoClassById(long id);
    List<GeologicalClass> findAllGeoClasses();
    GeologicalClass updateGeoClass(Long id, GeologicalClass geologicalClass);
    List<Section> findSectionsByGeoClassesCode(String code);
}
