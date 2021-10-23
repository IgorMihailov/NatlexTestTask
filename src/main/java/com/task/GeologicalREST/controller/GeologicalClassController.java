package com.task.GeologicalREST.controller;

import com.task.GeologicalREST.entity.GeologicalClass;
import com.task.GeologicalREST.service.IGeologicalClassService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/geo_classes")
public class GeologicalClassController {

    @Autowired
    IGeologicalClassService geologicalClassService;

    @PostMapping("/{sectionId}")
    public ResponseEntity<String> addGeoClass(@Valid @RequestBody GeologicalClass geoClass, @PathVariable long sectionId) throws NotFoundException {

        geologicalClassService.saveGeoClass(geoClass, sectionId);
        return new ResponseEntity<>("Saved successfully", HttpStatus.OK);

    }

    @GetMapping("/")
    public ResponseEntity<List<GeologicalClass>> getAllGeoClasses(){
        return ResponseEntity.ok(geologicalClassService.findAllGeoClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeologicalClass> getGeoClassById(@PathVariable long id){
        return ResponseEntity.ok(geologicalClassService.findGeoClassById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSection(@PathVariable("id") long id, @RequestBody GeologicalClass geoClass) {

        boolean updResult = geologicalClassService.updateGeoClass(id, geoClass);

        if (updResult) {
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Geological class not found", HttpStatus.NOT_FOUND);
        }
    }

}
