package com.edu.smartstudentcard.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table
@Data
public class AttendanceHistory {

    @Id
    private Long id;

    @ManyToOne
    @NotBlank
    private Card card;

    @OneToOne
    @NotBlank
    private Event event;

    @NotBlank
    private Date dateOfEvent;


}
