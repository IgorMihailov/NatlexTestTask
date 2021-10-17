package com.task.GeologicalREST.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Job {

    @Id
    private long id;

    private String state;

    private String task;

}
