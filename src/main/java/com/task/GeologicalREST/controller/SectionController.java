package com.task.GeologicalREST.controller;

import com.task.GeologicalREST.entity.Section;
import com.task.GeologicalREST.service.IGeologicalClassService;
import com.task.GeologicalREST.service.ISectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    @Autowired
    private ISectionService sectionService;

    @Autowired
    private IGeologicalClassService IGeologicalClassService;

    @PostMapping
    public ResponseEntity<String> addSection(@Valid @RequestBody Section section) {

        Section savedSection = sectionService.saveSection(section);
        return new ResponseEntity("Saved with id: " + savedSection.getId(), HttpStatus.OK);

    }

    @GetMapping("/")
    public ResponseEntity<List<Section>> getAllSections(){

        return ResponseEntity.ok(sectionService.findAllSection());

    }

    @GetMapping("/by-code")
    public ResponseEntity<List<Section>> getSectionByName(@RequestParam("code") String code){

        return ResponseEntity.ok(IGeologicalClassService.findSectionsByGeoClassesCode(code));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Section> getSectionByName(@PathVariable long id){

        return ResponseEntity.ok(sectionService.findSectionById(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSection(@PathVariable("id") long id, @RequestBody Section section) {

        boolean updResult = sectionService.updateSection(id, section);

        if (updResult) {
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Section not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSection(@PathVariable("id") long id) {
        try {
            sectionService.deleteSectionById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<HttpStatus> deleteAllSections() {
        try {
            sectionService.deleteAllSections();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
