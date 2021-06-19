package com.edu.smartstudentcard.dto;

import com.edu.smartstudentcard.enums.ERoleName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Getter
@Setter
public class SignUpRequest {

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String userName;

	@NotBlank
	private String mobile;

	@Email
	private String email;

	@NotBlank
	private String password;

	@NotNull
	private ERoleName roleName;


}
