package com.edu.smartstudentcard.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Card card;

    @ManyToOne
    private Event event;

    private Date dateOfTransaction;

    private Float previousAmount;

    private Float amountRemained;
}
