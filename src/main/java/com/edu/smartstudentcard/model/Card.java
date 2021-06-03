package com.edu.smartstudentcard.model;

import com.edu.smartstudentcard.enums.ECardStatus;
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
    private String id;
    @OneToOne
    private User user;

    private ECardStatus status;

    private Float amount;

    public void setAutoStatus() {
        this.status = ECardStatus.ACTIVE;
    }
}
