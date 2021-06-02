package com.edu.smartstudentcard.repository;

import com.edu.smartstudentcard.enums.ERoleName;
import com.edu.smartstudentcard.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IRoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByName(ERoleName roleName);

}
