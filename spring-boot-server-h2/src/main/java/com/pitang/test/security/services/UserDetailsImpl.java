package com.pitang.test.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.pitang.test.payload.response.UserResponse;
import com.pitang.test.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private UserResponse userResponse;

	public UserDetailsImpl(Long id, String login, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
        userResponse = new UserResponse();
		this.userResponse.setId(id);
		this.userResponse.setLogin(login);
		this.userResponse.setEmail(email);
		this.userResponse.setPassword(password);
		this.userResponse.setAuthorities(authorities);
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(), 
				user.getLogin(),
				user.getEmail(),
				user.getPassword(), 
				authorities);
	}

	public UserResponse getUserResponse() {
		return userResponse;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.userResponse.getAuthorities();
	}

	@Override
	public String getPassword() {
		return this.userResponse.getPassword();
	}

	@Override
	public String getUsername() {
		return this.userResponse.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(this.userResponse.getId(), user.userResponse.getId());
	}

}
