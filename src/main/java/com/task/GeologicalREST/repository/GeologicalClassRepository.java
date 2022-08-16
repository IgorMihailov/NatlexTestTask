package com.task.GeologicalREST.repository;

import com.task.GeologicalREST.entity.GeologicalClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeologicalClassRepository extends JpaRepository<GeologicalClass, Long> {
    List<GeologicalClass> findByCode(String code);
}
