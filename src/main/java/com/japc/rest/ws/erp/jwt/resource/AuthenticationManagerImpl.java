package com.japc.rest.ws.erp.jwt.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.japc.rest.ws.erp.enumerator.UserStateEnum;
import com.japc.rest.ws.erp.model.Password;
import com.japc.rest.ws.erp.model.User;
import com.japc.rest.ws.erp.repository.PasswordJpaRepository;
import com.japc.rest.ws.erp.repository.UserJpaRepository;

@Service
public class AuthenticationManagerImpl implements AuthenticationManager {

	@Autowired
	private UserJpaRepository userJpaRepository;
	
	@Autowired
	private PasswordJpaRepository passwordJpaRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal() + "";
		String password = authentication.getCredentials() + "";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		Optional<User> user = userJpaRepository.findById(username);
		Password userPassword = passwordJpaRepository.findCurrentByUsername(username);
		
		if (!user.isPresent()) {
			throw new BadCredentialsException("0004");
		}
		if (user.get().getUserState().equalsIgnoreCase(UserStateEnum.DISABLED.getState())) {
			throw new DisabledException("0005");
		}
		if (user.get().getUserState().equalsIgnoreCase(UserStateEnum.LOCKED.getState())) {
			throw new DisabledException("0006");
		}
		if (!encoder.matches(password, userPassword.getPasswordText())) {
			throw new BadCredentialsException("0900");
		}

		return new UsernamePasswordAuthenticationToken(username, password);
	}
}
