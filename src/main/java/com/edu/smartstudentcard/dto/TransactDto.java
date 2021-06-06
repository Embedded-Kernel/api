package com.edu.smartstudentcard.dto;

import com.edu.smartstudentcard.model.Card;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class TransactDto {

    private String cardId;

    @DateTimeFormat
    private Date dateOfTransaction;

    private Float amount;

}
