package com.edu.smartstudentcard.dto;

import com.edu.smartstudentcard.enums.EGender;
import com.edu.smartstudentcard.enums.ERoleName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignUpRequest {

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private EGender gender;

	@NotBlank
	private String mobile;

	@Email
	private String email;

	@NotBlank
	private String password;

	private ERoleName roleName;

	
}
