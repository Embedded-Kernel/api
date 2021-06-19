package com.edu.smartstudentcard.model;

import com.edu.smartstudentcard.audits.TimestampAudit;
import com.edu.smartstudentcard.enums.ECardStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "card")
@Data
public class Card extends TimestampAudit {
    @Id
    private String id;
    @OneToOne
    private Student student;

    private ECardStatus status;

    private Double amount;

    public void setAutoStatus() {
        this.status = ECardStatus.ACTIVE;
    }

    public void setAutoAmount(){
        this.amount = 0.0;
    }
    public void withdraw(Float amountToWithdraw) {
        this.amount = amount - amountToWithdraw;
    }

    public void deposit(Float amountToDeposit) {
        this.amount = amount - amountToDeposit;
    }
}
