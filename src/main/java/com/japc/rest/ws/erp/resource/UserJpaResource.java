package com.japc.rest.ws.erp.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.japc.rest.ws.erp.dto.Response;
import com.japc.rest.ws.erp.enumerator.RolesEnum;
import com.japc.rest.ws.erp.enumerator.UserStateEnum;
import com.japc.rest.ws.erp.model.Password;
import com.japc.rest.ws.erp.model.Role;
import com.japc.rest.ws.erp.model.User;
import com.japc.rest.ws.erp.repository.LogJpaRepository;
import com.japc.rest.ws.erp.repository.PasswordJpaRepository;
import com.japc.rest.ws.erp.repository.RoleJpaRepository;
import com.japc.rest.ws.erp.repository.UserJpaRepository;
import com.japc.rest.ws.erp.util.MessageCoreUtil;
import com.japc.rest.ws.erp.util.Utilities;

@CrossOrigin(origins = "${spring.cors.origin}")
@RestController
@RequestMapping("users")
public class UserJpaResource {

	@Autowired
	private MessageCoreUtil msg;

	@Autowired
	private PasswordJpaRepository passwordJpaRepository;

	@Autowired
	private RoleJpaRepository roleJpaRepository;

	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private LogJpaRepository logJpaRepository;

	@GetMapping("/{username}/admin/erp/{id}")
	public ResponseEntity<Response> getUser(@PathVariable String username, @PathVariable String id) {
		String returnCode = "0000";
		try {
			Optional<User> user = userJpaRepository.findById(id);
			if (user.isPresent()) {
				Response response = new Response(HttpStatus.OK, returnCode, msg.getString("app.return." + returnCode),
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString(), user.get());
				return new ResponseEntity<Response>(response, HttpStatus.OK);
			} else {
				returnCode = "9998"; // Resource not found
				Response response = new Response(HttpStatus.NOT_FOUND, returnCode,
						msg.getString("app.return." + returnCode),
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
				return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.err.println(Utilities.formatExceptionMessage(this, "getUser", e));
			returnCode = "9999";
			Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR, returnCode,
					msg.getString("app.return." + returnCode), Utilities.getStackTrace(e),
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{username}/admin/erp")
	public ResponseEntity<Response> getAllUsers(@PathVariable String username) {
		String returnCode = "0000";
		try {
			List<User> users = userJpaRepository.findAll();
			if (users.size() > 0) {
				Response response = new Response(HttpStatus.OK, returnCode, msg.getString("app.return." + returnCode),
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString(), users);
				return new ResponseEntity<Response>(response, HttpStatus.OK);
			} else {
				returnCode = "9998"; // Resource not found
				Response response = new Response(HttpStatus.NOT_FOUND, returnCode, msg.getString("app.return." + returnCode),
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
				return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.err.println(Utilities.formatExceptionMessage(this, "getAllUsers", e));
			returnCode = "9999";
			Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR, returnCode,
					msg.getString("app.return." + returnCode), Utilities.getStackTrace(e),
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{username}/admin/erp")
	public ResponseEntity<Response> createUser(@PathVariable String username, @RequestBody Password password) {
		String returnCode = "0000";
		User requestUser = userJpaRepository.findById(username).get();
		String uuid = UUID.randomUUID().toString();

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		try {
			User user = password.getUser();

			if (userJpaRepository.findById(user.getUserId()).isPresent()) { // User's username already exists
				returnCode = "0100";
				Response response = new Response(HttpStatus.CONFLICT, returnCode, msg.getString("app.return." + returnCode),
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
				return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
			}
			if (userJpaRepository.findByUserEmail(user.getUserEmail()) != null) { // User's email already exists
				returnCode = "0101";
				Response response = new Response(HttpStatus.CONFLICT, returnCode, msg.getString("app.return." + returnCode),
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
				return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
			}

			Optional<Role> defaultRole = roleJpaRepository.findById(RolesEnum.EMPLOYEE.getRoleCode());
			user.setRole(defaultRole.get());
			User newUser = userJpaRepository.save(user);

			password.setPasswordState(true);
			password.setUser(newUser);
			password.setPasswordText(encoder.encode(password.getPasswordText()));
			passwordJpaRepository.save(password);

			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getUserId())
					.toUri();
//			return ResponseEntity.created(uri).build();
			returnCode = "0001";
			Response response = new Response(HttpStatus.CREATED, returnCode, msg.getString("app.return." + returnCode),
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString(), uri);
			return new ResponseEntity<Response>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			System.err.println(Utilities.formatExceptionMessage(this, "createUser", e));
			Utilities.saveLogToDb(uuid, requestUser, "ERROR", this, "createUser", e, "", logJpaRepository);

			returnCode = "9999";
			Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR, returnCode,
					msg.getString("app.return." + returnCode), Utilities.getStackTrace(e),
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{username}/admin/erp")
	public ResponseEntity<Response> updateUser(@PathVariable String username, @RequestBody Password password) {
		String returnCode = "0000";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		try {
			returnCode = "0003";
			User userUpdated = userJpaRepository.save(password.getUser());
			if (!password.getPasswordText().isEmpty()) {
				Password oldPassword = passwordJpaRepository.findCurrentByUsername(password.getUser().getUserId());
				oldPassword.setPasswordState(false);
				passwordJpaRepository.save(oldPassword);

				password.setPasswordState(true);
				password.setUser(userUpdated);
				password.setPasswordText(encoder.encode(password.getPasswordText()));
				passwordJpaRepository.save(password);

			}
			Response response = new Response(HttpStatus.OK, returnCode, msg.getString("app.return." + returnCode),
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString(), userUpdated);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(Utilities.formatExceptionMessage(this, "updateUser", e));
			returnCode = "9999";
			Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR, returnCode,
					msg.getString("app.return." + returnCode), Utilities.getStackTrace(e),
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{username}/admin/erp/{id}")
	public ResponseEntity<Response> disableUser(@PathVariable String username, @PathVariable String id,
			@RequestBody User user) {
		String returnCode = "0000";
		try {
			returnCode = "0005";
			User userToDisable = userJpaRepository.findById(id).get();
			userToDisable.setUserState(UserStateEnum.DISABLED.getState());
			userJpaRepository.save(userToDisable);
			Response response = new Response(HttpStatus.OK, returnCode, msg.getString("app.return." + returnCode),
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString(), userToDisable);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(Utilities.formatExceptionMessage(this, "disableUser", e));
			returnCode = "9999";
			Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR, returnCode,
					msg.getString("app.return." + returnCode), Utilities.getStackTrace(e),
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{username}/admin/erp/{id}")
	public ResponseEntity<Response> deleteUser(@PathVariable String username, @PathVariable String id) {
		String returnCode = "0000";
		try {
			returnCode = "0002";
			List<Password> passwordsToDelete = passwordJpaRepository.findAllByUsername(id);
			for (Password p : passwordsToDelete) {
				passwordJpaRepository.deleteById(p.getPasswordId());
			}

			User userToDelete = userJpaRepository.findById(id).get();
			userJpaRepository.delete(userToDelete);
			Response response = new Response(HttpStatus.OK, returnCode, msg.getString("app.return." + returnCode),
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString(), userToDelete);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(Utilities.formatExceptionMessage(this, "deleteUser", e));
			returnCode = "9999";
			Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR, returnCode,
					msg.getString("app.return." + returnCode), Utilities.getStackTrace(e),
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
