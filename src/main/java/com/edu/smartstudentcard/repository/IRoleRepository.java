package com.edu.smartstudentcard.repository;

import com.example.classc.enums.ERoleName;
import com.example.classc.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IRoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByName(ERoleName roleName);

}
