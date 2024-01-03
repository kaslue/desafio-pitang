package com.pitang.test.controllers;

import com.pitang.test.builders.UserResponseBuilder;
import com.pitang.test.models.Car;
import com.pitang.test.models.User;
import com.pitang.test.payload.request.SessionUserIdRequest;
import com.pitang.test.payload.request.UserRequest;
import com.pitang.test.payload.response.UserResponse;
import com.pitang.test.repository.CarRepository;
import com.pitang.test.repository.UserRepository;
import com.pitang.test.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AccessController {
	@Autowired
    UserRepository userRepository;

	@Autowired
    CarRepository carRepository;

	@Autowired
	PasswordEncoder encoder;

	@GetMapping("/test/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/test/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/test/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/test/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}

	@GetMapping("/users")
	public ResponseEntity<?> listUsers() {

		List<User> lsUs = userRepository.findAll();
		List<UserResponse> lsUsReturn = new ArrayList<UserResponse>();
		for (User us: lsUs){
			UserResponse usReturntemp = UserResponseBuilder.builder()
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
					.build();
			if (us.getDhLastLogin() != null) {
				usReturntemp.setDhLastLogin(us.getDhLastLogin());
			}
			lsUsReturn.add(usReturntemp);
		}
		return ResponseEntity.ok(lsUsReturn);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		User us = userRepository.findById(id).get();
		UserResponse usReturn = UserResponseBuilder.builder()
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
				.build();
		if (us.getDhLastLogin() != null) {
			usReturn.setDhLastLogin(us.getDhLastLogin());
		}
		return ResponseEntity.ok(usReturn);
	}

	@PostMapping("/users/update/{id}")
	public ResponseEntity<?> updateUserById(@RequestBody UserRequest userRequest, @PathVariable Long id) throws ParseException {
		User us = userRepository.findById(id).get();
		us.setFirstName(userRequest.getFirstName());
		us.setLastName(userRequest.getLastName());
		if (!userRequest.getBirthday().equals("null")) {
			us.setBirthday(userRequest.getBirthday());
		}
		us.setEmail(userRequest.getEmail());
		us.setLogin(userRequest.getLogin());
		if (us.getPassword().equals(userRequest.getPassword())) {
			us.setPassword(userRequest.getPassword());
		} else {
			us.setPassword(encoder.encode(userRequest.getPassword()));
		}
		us.setPhone(userRequest.getPhone());
		//us.setCars(new HashSet<>(userRequest.getCars()));
		us.setDhLastUpdate(new Date());
		userRepository.saveAndFlush(us);
		//String msgRet = "{ \"message\": \"User with id "+id+" updated.\", \"msgCode\": 2 }";
		UserResponse usReturn = UserResponseBuilder.builder()
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
				.build();
		if (us.getDhLastLogin() != null) {
			usReturn.setDhLastLogin(us.getDhLastLogin());
		}
		return ResponseEntity.ok(usReturn);
	}
	@PostMapping("/users/remove/{id}")
	public ResponseEntity<?> removeUserById(@PathVariable Long id) {

		User userToDelete = userRepository.findById(id).get();
		userRepository.delete(userToDelete);

		String msgRet = "{ \"message\": \"User with id "+id+" removed.\", \"msgCode\": 3 }";
		return ResponseEntity.ok(msgRet);
	}

	@GetMapping("/cars")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> listCars(@RequestBody SessionUserIdRequest userRequest) {

		User us =  userRepository.findById(userRequest.getId()).get();
		UserResponse usReturn = UserResponseBuilder.builder()
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
				.build();
		if (us.getDhLastLogin() != null) {
			usReturn.setDhLastLogin(us.getDhLastLogin());
		}
		return ResponseEntity.ok(usReturn.getCars());
	}

	@PostMapping("/cars")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> addCarToUser(@RequestBody SessionUserIdRequest userRequest) {
		User us = userRepository.findById(userRequest.getId()).get();
		if (userRequest.getCar() != null) {
			Car car = userRequest.getCar();
			car = carRepository.saveAndFlush(car);
			us.getCars().add(car);
			us.setDhLastUpdate(new Date());
			userRepository.saveAndFlush(us);
		}
		UserResponse usReturn = UserResponseBuilder.builder()
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
				.build();
		if (us.getDhLastLogin() != null) {
			usReturn.setDhLastLogin(us.getDhLastLogin());
		}
		return ResponseEntity.ok(usReturn);
	}

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> showMyInfo(@RequestBody SessionUserIdRequest userRequest) {

		User us= userRepository.findById(userRequest.getId()).get();
		UserResponse usReturn = UserResponseBuilder.builder()
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
				.build();
		if (us.getDhLastLogin() != null) {
			usReturn.setDhLastLogin(us.getDhLastLogin());
		}
		return ResponseEntity.ok(usReturn);
	}

	@PostMapping("/cars/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getCarFromUser(@RequestBody SessionUserIdRequest userRequest, @PathVariable Long id) {

		User us = userRepository.findById(userRequest.getId()).get();
		Car carFiltered = Util.filterById(us.getCars(),id);
		return ResponseEntity.ok(carFiltered);
	}

	@PostMapping("/cars/update/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> updateCarFromUser(@RequestBody SessionUserIdRequest userRequest, @PathVariable Long id) {
		User us =  userRepository.findById(userRequest.getId()).get();
		Car car = Util.filterById(us.getCars(),id);
		if (car != null) {
			car = userRequest.getCar();
			car.setId(id);
			carRepository.saveAndFlush(car);
		}
		us =  userRepository.findById(userRequest.getId()).get();
		us.setDhLastUpdate(new Date());
		userRepository.saveAndFlush(us);
		UserResponse usReturn = UserResponseBuilder.builder()
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
				.build();
		if (us.getDhLastLogin() != null) {
			usReturn.setDhLastLogin(us.getDhLastLogin());
		}

		return ResponseEntity.ok(usReturn);
	}

	@PostMapping("/cars/remove/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> removeCarFromUser(@RequestBody SessionUserIdRequest userRequest, @PathVariable Long id) {
		User us = userRepository.findById(userRequest.getId()).get();
		Car car = Util.filterById(us.getCars(),id);
		us.setCars(Util.removeById(us.getCars(),id));
		userRepository.saveAndFlush(us);
		carRepository.delete(car);
		UserResponse usReturn = UserResponseBuilder.builder()
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
				.build();
		if (us.getDhLastLogin() != null) {
			usReturn.setDhLastLogin(us.getDhLastLogin());
		}
		return ResponseEntity.ok(usReturn);
	}
}
