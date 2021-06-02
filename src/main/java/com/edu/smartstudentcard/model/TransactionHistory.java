package com.edu.smartstudentcard.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

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
