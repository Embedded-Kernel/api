package com.edu.smartstudentcard.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStudentDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String className;

    @NotNull
    private String academicYear;


    private String dormNumber;


    private String religion;


    private String placeOfResidence;

    private String tableNbr;

    @NotNull
    private String imageUrl;

    @NotNull
    private String username;

    @NotNull
    private String mobile;

    @NotNull
    private String email;

    @NotNull
    private String password;

}
