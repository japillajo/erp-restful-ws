package com.japc.rest.ws.erp.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.japc.rest.ws.erp.dto.Response;
import com.japc.rest.ws.erp.model.Role;
import com.japc.rest.ws.erp.repository.RoleJpaRepository;
import com.japc.rest.ws.erp.util.MessageCoreUtil;
import com.japc.rest.ws.erp.util.Utilities;

@CrossOrigin(origins = "${spring.cors.origin}")
@RestController
public class RoleJpaResource {

	@Autowired
	private MessageCoreUtil msg;

	@Autowired
	private RoleJpaRepository roleJpaRepository;

	@GetMapping("/roles/admin/erp")
	public ResponseEntity<Response> getAllRoles() {
		String returnCode = "0000";
		try {
			List<Role> roles = roleJpaRepository.findAll();
			if (roles.size() > 0) {
				Response response = new Response(HttpStatus.OK, returnCode, msg.getString("app.return." + returnCode),
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString(), roles);
				return new ResponseEntity<Response>(response, HttpStatus.OK);
			} else {
				returnCode = "9998"; // Resource not found
				Response response = new Response(HttpStatus.NOT_FOUND, returnCode,
						msg.getString("app.return." + returnCode),
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
				return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.err.println(Utilities.formatExceptionMessage(this, "getAllRoles", e));
			returnCode = "9999";
			Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR, returnCode,
					msg.getString("app.return." + returnCode), Utilities.getStackTrace(e),
					ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
