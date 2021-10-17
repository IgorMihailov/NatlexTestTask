package com.task.GeologicalREST.controller;

import com.task.GeologicalREST.entity.GeologicalClass;
import com.task.GeologicalREST.service.GeologicalClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geo_classes")
public class GeologicalClassController {

    @Autowired
    GeologicalClassService geologicalClassService;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGeoClass(@PathVariable("id") long id) {

        try {
            geologicalClassService.deleteGeoClassById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/")
    public ResponseEntity<HttpStatus> deleteAllGeoClasses() {

        try {
            geologicalClassService.deleteAllGeoClasses();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
