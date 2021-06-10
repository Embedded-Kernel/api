package com.edu.smartstudentcard.services;

import com.edu.smartstudentcard.model.User;
import com.edu.smartstudentcard.repository.IUserRepository;
import com.edu.smartstudentcard.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getAllUsers(int page, int size) {
        Constants.validatePageNumberAndSize(page,size);
        Pageable pageable = (Pageable) PageRequest.of(page,size, Sort.Direction.ASC,"firstName");
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }
}
