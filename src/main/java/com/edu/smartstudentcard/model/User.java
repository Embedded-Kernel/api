package com.edu.smartstudentcard.model;

import com.edu.smartstudentcard.audits.TimestampAudit;
import com.edu.smartstudentcard.enums.EAccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
@Data
public class User extends TimestampAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "first name is compulsory")
	private String firstName;

	@NotNull(message = "last name is compulsory")
	private String lastName;

	private String fullName;

	private String imageUrl;

	@NotNull
	@Column(unique = true)
	private String username;

	@Column(unique = true)
	private String mobile;

	@NotNull
	@Email
	@Column(unique = true)
	private String email;

	@Enumerated(EnumType.STRING)
	private EAccountStatus status;

	@JsonIgnore
	@NotNull
	private String password;

	private String activationCode;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {

	}

	public User(String firstName,
				String lastName,
				String mobile,
				String email,
				String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobile = mobile;
		this.email = email;
		this.password = password;
		this.status = EAccountStatus.ACTIVE;
	}

	public User(String firstName,
				String lastName,
				String email,
				String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
}