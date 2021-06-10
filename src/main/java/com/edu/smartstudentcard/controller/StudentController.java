package com.edu.smartstudentcard.controller;

import com.edu.smartstudentcard.dto.CreateStudentDto;
import com.edu.smartstudentcard.enums.EAccountStatus;
import com.edu.smartstudentcard.enums.ERoleName;
import com.edu.smartstudentcard.model.Role;
import com.edu.smartstudentcard.model.Student;
import com.edu.smartstudentcard.model.User;
import com.edu.smartstudentcard.repository.IRoleRepository;
import com.edu.smartstudentcard.repository.IStudentRepository;
import com.edu.smartstudentcard.repository.IUserRepository;
import com.edu.smartstudentcard.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Student> getAll() {

        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value="id") Long id) {

        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()) {
            return ResponseEntity.ok(student.get()) ;
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Student());

    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CreateStudentDto studentDto){

        //Save the User first
        User newUser = new User();
        newUser.setFirstName(studentDto.getFirstName());
        newUser.setLastName(studentDto.getLastName());
        newUser.setUsername(studentDto.getUsername());
        newUser.setEmail(studentDto.getEmail());
        newUser.setMobile(studentDto.getMobile());
       // newUser.setAutoStatus();
        newUser.setPassword(passwordEncoder.encode(studentDto.getPassword()));

        Optional<Role> userRole = roleRepository.findByName(ERoleName.STUDENT);

        newUser.setRoles( Collections.singleton( roleRepository.findByName(ERoleName.STUDENT).get()) );
        newUser = userRepository.save(newUser);
        System.out.println("Returned User's Id: "+newUser.getId());

        //Save student now
        Student newStudent = new Student();
    //    newStudent.setUser(newUser);
        newStudent.setClassName(studentDto.getClassName());
        newStudent.setAcademicYear(studentDto.getAcademicYear());
        newStudent.setDormNbr(studentDto.getDormNumber());
        newStudent.setReligion(studentDto.getReligion());
        newStudent.setPlaceOfResidence(studentDto.getPlaceOfResidence());
        studentRepository.save(newStudent);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(newUser.getUsername()).toUri();

        return ResponseEntity.created(location).body("Successfully registered");

//        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudentById(@PathVariable Long id, @RequestBody CreateStudentDto studentDto){

        //Search for the student
        Optional<Student> StudentData = studentRepository.findById(id);

        //Search for user and check if he exists
    /*
        Optional<User> user = userRepository.findById(StudentData.get().getUser().getId());
        if(!user.isPresent()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse("User not found", false));
        }

     */
        //Update user credentials
     /*
        User userToUpdate = user.get();
        userToUpdate.setFirstName(studentDto.getFirstName());
        userToUpdate.setLastName(studentDto.getLastName());
        userToUpdate.setImageUrl(studentDto.getImageUrl());
        userToUpdate.setPassword(studentDto.getPassword());
        userRepository.save(userToUpdate);

      */

        //Update Student
        if(StudentData.isPresent()){
            Student _student = StudentData.get();
       //     _student.setUser(userToUpdate);
            _student.setClassName(studentDto.getClassName());
            _student.setAcademicYear(studentDto.getAcademicYear());
            _student.setDormNbr(studentDto.getDormNumber());
            _student.setTableNbr(studentDto.getTableNbr());
            _student.setReligion(studentDto.getReligion());
            _student.setPlaceOfResidence(studentDto.getPlaceOfResidence());

            return new ResponseEntity<>(studentRepository.save(_student),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete student by ID
    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable Long id){
        studentRepository.deleteById(id);
    }
}
