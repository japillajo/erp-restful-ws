package com.japc.rest.ws.erp.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

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

import com.japc.rest.ws.erp.dto.Detail;
import com.japc.rest.ws.erp.dto.Header;
import com.japc.rest.ws.erp.dto.Response;
import com.japc.rest.ws.erp.enumerator.LogTypeEnum;
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

	private static final Logger LOGGER = Logger.getLogger(UserJpaResource.class.getName());

	private Header header = null;
	private Detail detail = null;
	private Response response = null;

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

		String uuid = UUID.randomUUID().toString();

		User requestUser = userJpaRepository.findById(username).get();

		try {
			Optional<User> user = userJpaRepository.findById(id);
			if (user.isPresent()) {
				Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.INFO, null, user.get().toString(),
						logJpaRepository);
				header = new Header(uuid, HttpStatus.OK,
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
				detail = new Detail(returnCode, msg.getString("app.return." + returnCode), user.get());
				response = new Response(header, detail);
				return new ResponseEntity<Response>(response, HttpStatus.OK);
			} else {
				returnCode = "9998"; // Resource not found
				Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.WARNING, null,
						msg.getString("app.return." + returnCode), logJpaRepository);
				header = new Header(uuid, HttpStatus.NOT_FOUND,
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
				detail = new Detail(returnCode, msg.getString("app.return." + returnCode), null);
				response = new Response(header, detail);
				return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			returnCode = "9999";
			Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.SEVERE, e,
					msg.getString("app.return." + returnCode), logJpaRepository);
			header = new Header(uuid, HttpStatus.INTERNAL_SERVER_ERROR,
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			detail = new Detail(returnCode, msg.getString("app.return." + returnCode), Utilities.getStackTrace(e));
			response = new Response(header, detail);
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{username}/admin/erp")
	public ResponseEntity<Response> getAllUsers(@PathVariable String username) {
		String returnCode = "0000";

		String uuid = UUID.randomUUID().toString();

		User requestUser = userJpaRepository.findById(username).get();

		try {
			List<User> users = userJpaRepository.findAll();
			if (users.size() > 0) {
				Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.INFO, null, users.toString(),
						logJpaRepository);
				header = new Header(uuid, HttpStatus.OK,
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
				detail = new Detail(returnCode, msg.getString("app.return." + returnCode), users);
				response = new Response(header, detail);
				return new ResponseEntity<Response>(response, HttpStatus.OK);
			} else {
				returnCode = "9998"; // Resource not found
				Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.WARNING, null,
						msg.getString("app.return." + returnCode), logJpaRepository);
				header = new Header(uuid, HttpStatus.NOT_FOUND,
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
				detail = new Detail(returnCode, msg.getString("app.return." + returnCode), null);
				response = new Response(header, detail);
				return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			returnCode = "9999";
			Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.SEVERE, e,
					msg.getString("app.return." + returnCode), logJpaRepository);
			header = new Header(uuid, HttpStatus.INTERNAL_SERVER_ERROR,
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			detail = new Detail(returnCode, msg.getString("app.return." + returnCode), Utilities.getStackTrace(e));
			response = new Response(header, detail);
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{username}/admin/erp")
	public ResponseEntity<Response> createUser(@PathVariable String username, @RequestBody Password password) {
		String returnCode = "0000";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String uuid = UUID.randomUUID().toString();

		User requestUser = username.equalsIgnoreCase("register") ? null : userJpaRepository.findById(username).get();

		try {
			User user = password.getUser();

			if (userJpaRepository.findById(user.getUserId()).isPresent()) { // User's username already exists
				returnCode = "0100";
				Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.WARNING, null,
						msg.getString("app.return." + returnCode), logJpaRepository);
				header = new Header(uuid, HttpStatus.CONFLICT,
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
				detail = new Detail(returnCode, msg.getString("app.return." + returnCode), null);
				response = new Response(header, detail);
				return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
			}
			if (userJpaRepository.findByUserEmail(user.getUserEmail()) != null) { // User's email already exists
				returnCode = "0101";
				Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.WARNING, null,
						msg.getString("app.return." + returnCode), logJpaRepository);
				header = new Header(uuid, HttpStatus.CONFLICT,
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
				detail = new Detail(returnCode, msg.getString("app.return." + returnCode), null);
				response = new Response(header, detail);
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
			Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.INFO, null, uri.toString(), logJpaRepository);
			header = new Header(uuid, HttpStatus.CREATED,
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			detail = new Detail(returnCode, msg.getString("app.return." + returnCode), uri);
			response = new Response(header, detail);
			return new ResponseEntity<Response>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			returnCode = "9999";
			Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.SEVERE, e,
					msg.getString("app.return." + returnCode), logJpaRepository);
			header = new Header(uuid, HttpStatus.INTERNAL_SERVER_ERROR,
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			detail = new Detail(returnCode, msg.getString("app.return." + returnCode), Utilities.getStackTrace(e));
			response = new Response(header, detail);
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{username}/admin/erp")
	public ResponseEntity<Response> updateUser(@PathVariable String username, @RequestBody Password password) {
		String returnCode = "0000";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String uuid = UUID.randomUUID().toString();

		User requestUser = userJpaRepository.findById(username).get();

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
			Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.INFO, null, userUpdated.toString(),
					logJpaRepository);
			header = new Header(uuid, HttpStatus.OK, ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			detail = new Detail(returnCode, msg.getString("app.return." + returnCode), userUpdated);
			response = new Response(header, detail);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		} catch (Exception e) {
			returnCode = "9999";
			Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.SEVERE, e,
					msg.getString("app.return." + returnCode), logJpaRepository);
			header = new Header(uuid, HttpStatus.INTERNAL_SERVER_ERROR,
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			detail = new Detail(returnCode, msg.getString("app.return." + returnCode), Utilities.getStackTrace(e));
			response = new Response(header, detail);
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{username}/admin/erp/{id}")
	public ResponseEntity<Response> disableUser(@PathVariable String username, @PathVariable String id,
			@RequestBody User user) {
		String returnCode = "0000";

		String uuid = UUID.randomUUID().toString();

		User requestUser = userJpaRepository.findById(username).get();

		try {
			returnCode = "0005";
			User userToDisable = userJpaRepository.findById(id).get();
			userToDisable.setUserState(UserStateEnum.DISABLED.getState());
			userJpaRepository.save(userToDisable);
			Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.INFO, null, userToDisable.toString(),
					logJpaRepository);
			header = new Header(uuid, HttpStatus.OK, ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			detail = new Detail(returnCode, msg.getString("app.return." + returnCode), userToDisable);
			response = new Response(header, detail);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		} catch (Exception e) {
			returnCode = "9999";
			Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.SEVERE, e,
					msg.getString("app.return." + returnCode), logJpaRepository);
			header = new Header(uuid, HttpStatus.INTERNAL_SERVER_ERROR,
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			detail = new Detail(returnCode, msg.getString("app.return." + returnCode), Utilities.getStackTrace(e));
			response = new Response(header, detail);
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{username}/admin/erp/{id}")
	public ResponseEntity<Response> deleteUser(@PathVariable String username, @PathVariable String id) {
		String returnCode = "0000";

		String uuid = UUID.randomUUID().toString();

		User requestUser = userJpaRepository.findById(username).get();

		try {
			returnCode = "0002";
			List<Password> passwordsToDelete = passwordJpaRepository.findAllByUsername(id);
			for (Password p : passwordsToDelete) {
				passwordJpaRepository.deleteById(p.getPasswordId());
			}

			User userToDelete = userJpaRepository.findById(id).get();
			userJpaRepository.delete(userToDelete);
			Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.INFO, null, userToDelete.toString(),
					logJpaRepository);
			header = new Header(uuid, HttpStatus.OK, ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			detail = new Detail(returnCode, msg.getString("app.return." + returnCode), userToDelete);
			response = new Response(header, detail);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		} catch (Exception e) {
			returnCode = "9999";
			Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.SEVERE, e,
					msg.getString("app.return." + returnCode), logJpaRepository);
			header = new Header(uuid, HttpStatus.INTERNAL_SERVER_ERROR,
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			detail = new Detail(returnCode, msg.getString("app.return." + returnCode), Utilities.getStackTrace(e));
			response = new Response(header, detail);
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
