package com.task.GeologicalREST.repository;

import com.task.GeologicalREST.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

}
