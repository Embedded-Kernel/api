package com.edu.smartstudentcard.repository;

import com.edu.smartstudentcard.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IStudentRepository extends JpaRepository<Student, Long>{

}
