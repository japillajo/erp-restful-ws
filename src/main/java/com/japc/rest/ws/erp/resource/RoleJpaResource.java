package com.japc.rest.ws.erp.resource;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.japc.rest.ws.erp.dto.Detail;
import com.japc.rest.ws.erp.dto.Header;
import com.japc.rest.ws.erp.dto.Response;
import com.japc.rest.ws.erp.enumerator.LogTypeEnum;
import com.japc.rest.ws.erp.model.Role;
import com.japc.rest.ws.erp.model.User;
import com.japc.rest.ws.erp.repository.LogJpaRepository;
import com.japc.rest.ws.erp.repository.RoleJpaRepository;
import com.japc.rest.ws.erp.repository.UserJpaRepository;
import com.japc.rest.ws.erp.util.MessageCoreUtil;
import com.japc.rest.ws.erp.util.Utilities;

@CrossOrigin(origins = "${spring.cors.origin}")
@RestController
@RequestMapping("roles")
public class RoleJpaResource {

	private static final Logger LOGGER = Logger.getLogger(ModuleJpaResource.class.getName());

	private Header header = null;
	private Detail detail = null;
	private Response response = null;

	@Autowired
	private MessageCoreUtil msg;

	@Autowired
	private RoleJpaRepository roleJpaRepository;

	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private LogJpaRepository logJpaRepository;

	@GetMapping("/{username}/admin/erp")
	public ResponseEntity<Response> getAllRoles(@PathVariable String username) {
		String returnCode = "0000";

		String uuid = UUID.randomUUID().toString();

		User requestUser = userJpaRepository.findById(username).get();

		try {
			List<Role> roles = roleJpaRepository.findAll();
			if (roles.size() > 0) {
				Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.INFO, null, roles.toString(),
						logJpaRepository);
				header = new Header(uuid, HttpStatus.OK,
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
				detail = new Detail(returnCode, msg.getString("app.return." + returnCode), roles);
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
}
