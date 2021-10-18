package com.task.GeologicalREST.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "job")
public class Job {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "state")
    private String state;

    @Column(name = "task")
    private String task;

}
