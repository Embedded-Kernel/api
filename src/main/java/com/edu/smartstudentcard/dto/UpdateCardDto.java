package com.edu.smartstudentcard.dto;

import com.edu.smartstudentcard.enums.ECardStatus;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCardDto {

    @NotNull
    private String id;

    @NotNull
    private Long userId;

    @NotNull
    private ECardStatus cardStatus;
}
