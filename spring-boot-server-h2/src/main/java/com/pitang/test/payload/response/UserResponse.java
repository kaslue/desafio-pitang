package com.pitang.test.payload.response;

import com.pitang.test.models.Car;
import com.pitang.test.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class UserResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String birthday;
    private String login;
    private String password;
    private String phone;
    private List<String> roles;
    private List<Car> cars;
    private Date dhCreation;
    private Date dhLastUpdate;
    private Date dhLastLogin;

    public String getFirstName() {
        return firstName;
    }

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setBirthday(Date birthday) {
        if (birthday != null) {
            this.birthday = Util.dateFormat.format(birthday);
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Date getDhCreation() {
        return dhCreation;
    }

    public void setDhCreation(Date dhCreation) {
        this.dhCreation = dhCreation;
    }

    public Date getDhLastUpdate() {
        return dhLastUpdate;
    }

    public void setDhLastUpdate(Date dhLastUpdate) {
        this.dhLastUpdate = dhLastUpdate;
    }

    public Date getDhLastLogin() {
        return dhLastLogin;
    }

    public void setDhLastLogin(Date dhLastLogin) {
        this.dhLastLogin = dhLastLogin;
    }
}
