package com.edu.smartstudentcard.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String className;
    @NotBlank
    private int academicYear;

    private String dormNbr;

    private String tableNbr;

    private String religion;

    private String placeOfResidence;



}
