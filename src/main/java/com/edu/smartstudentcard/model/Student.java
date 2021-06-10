package com.edu.smartstudentcard.model;

import com.edu.smartstudentcard.audits.TimestampAudit;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table
@Data
public class Student extends TimestampAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @NotBlank
    private String className;


    @NotBlank
    private String academicYear;

    private String dormNbr;

    private String tableNbr;

    private String religion;

    private String placeOfResidence;
}