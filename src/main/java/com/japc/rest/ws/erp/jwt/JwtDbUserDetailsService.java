package com.japc.rest.ws.erp.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.japc.rest.ws.erp.model.Password;
import com.japc.rest.ws.erp.model.User;
import com.japc.rest.ws.erp.repository.PasswordJpaRepository;
import com.japc.rest.ws.erp.repository.UserJpaRepository;

@Service
public class JwtDbUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private PasswordJpaRepository passwordJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> findFirst = userJpaRepository.findById(username);

		if (!findFirst.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}

		Password password = passwordJpaRepository.findCurrentByUsername(username);
		JwtUserDetails jwtUserDetails = new JwtUserDetails(username, password.getPasswordText(),
				findFirst.get().getRole().getRoleName());

		return jwtUserDetails;
	}

}
