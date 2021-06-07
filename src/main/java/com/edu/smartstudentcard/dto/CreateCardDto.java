package com.edu.smartstudentcard.dto;

import com.edu.smartstudentcard.model.User;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToOne;

@Getter
@Setter
public class CreateCardDto {

    @NotNull
    private String id;

    @NotNull
    private String studentUserName;


}
