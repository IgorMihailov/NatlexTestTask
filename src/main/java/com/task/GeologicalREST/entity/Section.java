package com.task.GeologicalREST.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "section")
public class Section  {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "section")
    private List<GeologicalClass> geologicalClasses;

}
