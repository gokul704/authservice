package com.college.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.college.authservice.DTO.AuthRequestDTO;
import com.college.authservice.DTO.AuthResponseDTO;
import com.college.authservice.entity.User;
import com.college.authservice.enums.Role;
import com.college.authservice.exception.UserAlreadyExistsException;
import com.college.authservice.repository.UserRepository;
import com.college.authservice.util.JWTUtil;


@Service
public class AuthService {

	@Autowired
    private  UserRepository userRepository;
	@Autowired
    private  PasswordEncoder passwordEncoder;
	@Autowired
    private  JWTUtil jwtUtil;

    public AuthResponseDTO register(AuthRequestDTO request, Role role) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
        	throw new UserAlreadyExistsException("User already exists with username: " + request.getUsername());        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .rollno(request.getRollno())
                .role(role)
                .enabled(true)
                .build();

        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getUsername(),user.getRole().name());

        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername(),user.getRole().name());

        return new AuthResponseDTO(token);
    }

}
