package com.task.GeologicalREST.controller;

import com.task.GeologicalREST.entity.GeologicalClass;
import com.task.GeologicalREST.service.IGeologicalClassService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/geo_classes")
public class GeologicalClassController {

    private final IGeologicalClassService geologicalClassService;

    public GeologicalClassController(IGeologicalClassService geologicalClassService) {
        this.geologicalClassService = geologicalClassService;
    }

    @PostMapping("/{sectionId}")
    public GeologicalClass addGeoClass(@Valid @RequestBody GeologicalClass geoClass, @PathVariable long sectionId) throws NotFoundException {
        return geologicalClassService.saveGeoClass(geoClass, sectionId);
    }

    @GetMapping
    public List<GeologicalClass> getAllGeoClasses(){
        return geologicalClassService.findAllGeoClasses();
    }

    @GetMapping("/{id}")
    public GeologicalClass getGeoClassById(@PathVariable long id){
        return geologicalClassService.findGeoClassById(id);
    }

    @PutMapping("/{id}")
    public GeologicalClass updateSection(@PathVariable("id") long id, @RequestBody GeologicalClass geoClass) {
        return geologicalClassService.updateGeoClass(id, geoClass);
    }

}
