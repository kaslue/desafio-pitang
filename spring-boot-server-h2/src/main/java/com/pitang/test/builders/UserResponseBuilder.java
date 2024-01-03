package com.pitang.test.builders;

import com.pitang.test.models.Car;
import com.pitang.test.payload.response.UserResponse;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

public class UserResponseBuilder {
    private UserResponse userResponse;

    public UserResponseBuilder() {
        this.userResponse = new UserResponse();
    }

    public static UserResponseBuilder builder() {
        return new UserResponseBuilder();
    }
    public UserResponseBuilder addId(Long id) {
        this.userResponse.setId(id);
        return this;
    }

    public UserResponseBuilder addFirstName(String firstName) {
        this.userResponse.setFirstName(firstName);
        return this;
    }

    public UserResponseBuilder addLastName(String lastName) {
        this.userResponse.setLastName(lastName);
        return this;
    }

    public UserResponseBuilder addEmail(String email) {
        this.userResponse.setEmail(email);
        return this;
    }

    public UserResponseBuilder addBirthday(Date birthday) {
        this.userResponse.setBirthday(birthday);
        return this;
    }

    public UserResponseBuilder addLogin(String login) {
        this.userResponse.setLogin(login);
        return this;
    }

    public UserResponseBuilder addPassword(String password) {
        this.userResponse.setPassword(password);
        return this;
    }

    public UserResponseBuilder addPhone(String phone) {
        this.userResponse.setPhone(phone);
        return this;
    }

    public UserResponseBuilder addCars(Set<Car> cars) {
        if (cars != null) {
            List<Car> lsCars = new ArrayList<Car>();
            lsCars.addAll(cars);
            this.userResponse.setCars(lsCars);
        }
        return this;
    }

    public UserResponseBuilder addAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.userResponse.setAuthorities(authorities);
        return this;
    }

    public UserResponseBuilder addToken(String token) {
        this.userResponse.setAccessToken(token);
        return this;
    }

    public UserResponseBuilder addRole(List<String> Role) {
        this.userResponse.setRoles(Role);
        return this;
    }

    public UserResponseBuilder addDhCreation(Date dhCreation) {
        this.userResponse.setDhCreation(dhCreation);
        return this;
    }

    public UserResponseBuilder addDhLastUpdate(Date dhLastUpdate) {
        this.userResponse.setDhLastUpdate(dhLastUpdate);
        return this;
    }

    public UserResponseBuilder addDhLastLogin(Date dhLastLogin) {
        this.userResponse.setDhLastLogin(dhLastLogin);
        return this;
    }

    public UserResponse build(){
        return this.userResponse;
    }
}
