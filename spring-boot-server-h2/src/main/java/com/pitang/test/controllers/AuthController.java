package com.pitang.test.controllers;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.pitang.test.builders.UserResponseBuilder;
import com.pitang.test.models.ERole;
import com.pitang.test.models.Role;
import com.pitang.test.models.User;
import com.pitang.test.payload.request.LoginRequest;
import com.pitang.test.payload.request.SignupRequest;
import com.pitang.test.payload.response.MessageResponse;
import com.pitang.test.payload.response.UserResponse;
import com.pitang.test.repository.RoleRepository;
import com.pitang.test.repository.UserRepository;
import com.pitang.test.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pitang.test.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getlogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        User us = userRepository.findById(userDetails.getUserResponse().getId()).get();
        us.setDhLastLogin(new Date());
        userRepository.saveAndFlush(us);
        UserResponse usReturned = UserResponseBuilder.builder()
                .addId(us.getId())
                .addFirstName(us.getFirstName())
                .addLastName(us.getLastName())
                .addLogin(us.getLogin())
                .addPassword(us.getPassword())
                .addBirthday(us.getBirthday())
                .addEmail(us.getEmail())
                .addPhone(us.getPhone())
                .addCars(us.getCars())
                .addDhCreation(us.getDhCreation())
                .addDhLastUpdate(us.getDhLastUpdate())
                .addDhLastLogin(us.getDhLastLogin())
                .addToken(jwt)
                .addRole(roles)
                .build();
        //JwtResponse resp = new JwtResponse(jwt,us.getId(),us.getLogin(),us.getEmail(),usReturned.getRoles());
        return ResponseEntity.ok(usReturned);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws ParseException {
        if (userRepository.existsByLogin(signUpRequest.getLogin())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Login is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getLogin(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        if (!signUpRequest.getBirthday().isEmpty()) {
            user.setBirthday(signUpRequest.getBirthday());
        }
        if (!signUpRequest.getPhone().isEmpty()) {
            user.setPhone(signUpRequest.getPhone());
        }

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        user.setDhCreation(new Date());
        user.setDhLastUpdate(new Date());
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
