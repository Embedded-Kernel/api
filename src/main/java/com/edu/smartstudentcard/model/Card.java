package com.edu.smartstudentcard.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
@Data
public class Card {
    @Id
    private Long id;
    @OneToOne
    private User user;

    private String status;

    private Float amount;


}
