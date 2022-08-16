package com.task.GeologicalREST.controller;

import com.task.GeologicalREST.entity.Section;
import com.task.GeologicalREST.service.IGeologicalClassService;
import com.task.GeologicalREST.service.ISectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    private final ISectionService sectionService;

    private final IGeologicalClassService IGeologicalClassService;

    public SectionController(ISectionService sectionService, com.task.GeologicalREST.service.IGeologicalClassService IGeologicalClassService) {
        this.sectionService = sectionService;
        this.IGeologicalClassService = IGeologicalClassService;
    }

    @PostMapping
    public Section addSection(@Valid @RequestBody Section section) {
        return sectionService.saveSection(section);
    }

    @GetMapping
    public List<Section> getAllSections(){
        return sectionService.findAllSection();
    }

    @GetMapping("/by-code")
    public List<Section> getSectionByName(@RequestParam("code") String code){
        return IGeologicalClassService.findSectionsByGeoClassesCode(code);
    }

    @GetMapping("/{id}")
    public Section getSectionByName(@PathVariable long id){
        return sectionService.findSectionById(id);
    }

    @PutMapping("/{id}")
    public Section updateSection(@PathVariable("id") long id, @RequestBody Section section) {
        return sectionService.updateSection(id, section);
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

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllSections() {
        try {
            sectionService.deleteAllSections();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
