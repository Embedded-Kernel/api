package com.edu.smartstudentcard.model;


import com.edu.smartstudentcard.enums.ERoleName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(length = 60)
	private ERoleName name;

	public Role(ERoleName name) {
		this.name = name;
	}
}
