package com.task.GeologicalREST.controller;

import com.task.GeologicalREST.entity.GeologicalClass;
import com.task.GeologicalREST.entity.Section;
import com.task.GeologicalREST.service.GeologicalClassService;
import com.task.GeologicalREST.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private GeologicalClassService geologicalClassService;

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

        return ResponseEntity.ok(geologicalClassService.findSectionsByGeoClassesCode(code));

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
