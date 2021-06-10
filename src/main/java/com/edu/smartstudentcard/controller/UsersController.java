package com.edu.smartstudentcard.controller;

import com.edu.smartstudentcard.model.User;
import com.edu.smartstudentcard.repository.IUserRepository;
import com.edu.smartstudentcard.services.UserService;
import com.edu.smartstudentcard.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Page<User> getUsers(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
                               @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
        return userService.getAllUsers(page,size);
    }
}
