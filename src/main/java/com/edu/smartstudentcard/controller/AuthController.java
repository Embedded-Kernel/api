package com.edu.smartstudentcard.controller;

import com.edu.smartstudentcard.dto.LoginRequest;
import com.edu.smartstudentcard.dto.SignUpRequest;
import com.edu.smartstudentcard.enums.EAccountStatus;
import com.edu.smartstudentcard.enums.ERoleName;
import com.edu.smartstudentcard.model.Role;
import com.edu.smartstudentcard.model.User;
import com.edu.smartstudentcard.repository.IRoleRepository;
import com.edu.smartstudentcard.repository.IUserRepository;
import com.edu.smartstudentcard.security.JwtTokenProvider;
import com.edu.smartstudentcard.util.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Optional<User> findByEmail = userRepository.findByEmailOrMobileOrUsername(loginRequest.getLogin(),loginRequest.getLogin(),loginRequest.getLogin());
		if(findByEmail.isPresent()) {
			if(!(findByEmail.get().getStatus().equals(EAccountStatus.ACTIVE))) {
				ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid account status");
			}
		}else {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unknown account");
		}
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

		if (signUpRequest.getEmail() != null && userRepository.existsByEmail(signUpRequest.getEmail())) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Address already in use!");
		}

		if (userRepository.existsByMobile(signUpRequest.getMobile())) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone number already in use!");
		}
		
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getMobile(),
				signUpRequest.getEmail(), signUpRequest.getPassword());

		user.setUsername(user.getEmail());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setActivationCode(user.getMobile());
		user.setStatus(EAccountStatus.ACTIVE);
		user.setFullName(signUpRequest.getFirstName()+" "+signUpRequest.getLastName());
		
		Optional<Role> userRole = roleRepository.findByName(signUpRequest.getRoleName());

		System.out.println("User roles:          "+userRole.get());
		user.setRoles(Collections.singleton(userRole.get()));
//		user.setRoles(Collections.singleton( roleRepository.findByName(ERoleName.ROLE_ADMIN).get()));

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();

		return ResponseEntity.created(location).body("Successfully registered");
	}
}
