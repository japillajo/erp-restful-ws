package com.japc.rest.ws.erp.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
import com.japc.rest.ws.erp.model.Menu;
import com.japc.rest.ws.erp.model.Module;
import com.japc.rest.ws.erp.model.Role;
import com.japc.rest.ws.erp.model.User;
import com.japc.rest.ws.erp.model.UserMenu;
import com.japc.rest.ws.erp.repository.LogJpaRepository;
import com.japc.rest.ws.erp.repository.MenuJpaRepository;
import com.japc.rest.ws.erp.repository.ModuleJpaRepository;
import com.japc.rest.ws.erp.repository.RoleJpaRepository;
import com.japc.rest.ws.erp.repository.UserJpaRepository;
import com.japc.rest.ws.erp.repository.UserMenuJpaRepository;
import com.japc.rest.ws.erp.util.MessageCoreUtil;
import com.japc.rest.ws.erp.util.Utilities;

@CrossOrigin(origins = "${spring.cors.origin}")
@RestController
@RequestMapping("modules")
public class ModuleJpaResource {

	private static final Logger LOGGER = Logger.getLogger(ModuleJpaResource.class.getName());

	private Header header = null;
	private Detail detail = null;
	private Response response = null;

	@Autowired
	private MessageCoreUtil msg;

	@Autowired
	private MenuJpaRepository menuJpaRepository;

	@Autowired
	private ModuleJpaRepository moduleJpaRepository;

	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private UserMenuJpaRepository userMenuJpaRepository;

	@Autowired
	private RoleJpaRepository roleJpaRepository;

	@Autowired
	private LogJpaRepository logJpaRepository;

	@GetMapping("/{username}/admin/erp")
	public ResponseEntity<Response> getModulesByUser(@PathVariable String username) {
		String returnCode = "0000";

		String uuid = UUID.randomUUID().toString();

		User requestUser = userJpaRepository.findById(username).get();

		List<Module> newModules = new ArrayList<>();
		List<Menu> menus = new ArrayList<>();
		List<UserMenu> specialMenus = new ArrayList<>();
		Optional<Menu> menuTemp = null;
		try {
			Role userRole = roleJpaRepository.findByUsername(username);
			List<Module> modules = moduleJpaRepository.findAllByRoleId(userRole.getRoleId());

			if (modules.size() > 0) {
				returnCode = "0000";

				// Get a list of menus with true state by modules
				for (Module mod : modules) {
					List<Menu> menuListTemp = menuJpaRepository.findAllByModuleId(mod.getModuleId());
					menus.addAll(menuListTemp);
				}

				// Retrieve special menus from USER_MENU by username
				specialMenus = userMenuJpaRepository.findAllByUsername(username);// Revisar si hace falta validacion de
																					// null

				// Add or remove special menus from menus list
				for (UserMenu um : specialMenus) {
					menuTemp = menuJpaRepository.findById(um.getId().getMenuId());
					if (menus.contains(menuTemp.get())) {
						if (!um.isUserMenuEnabled()) {
							menus.remove(menuTemp.get());
						}
					} else {
						if (um.isUserMenuEnabled()) {
							if (menuTemp.get().isMenuState())
								menus.add(menuTemp.get());
						}
					}
				}

				// Create new modules list with the final menus list
				for (Menu men : menus) {
					Module moduleTemp = men.getModule();
					if (newModules.contains(moduleTemp)) {
						Module m = null;
						for (Module mod : newModules) {
							if (mod.equals(moduleTemp)) {
								m = mod;
								newModules.remove(mod);
								break;
							}
						}
						if (!m.getMenus().contains(men)) {
							m.getMenus().add(men);
						}
						newModules.add(m);
					} else {
						moduleTemp.setMenus(new ArrayList<>());
						moduleTemp.getMenus().add(men);
						newModules.add(moduleTemp);
					}
				}

				// Order modules by id asc
				Collections.sort(newModules);

				// Order module's menus by id asc
				for (Module mod : newModules) {
					Collections.sort(mod.getMenus());
				}

				Utilities.printSaveLog(LOGGER, uuid, requestUser, LogTypeEnum.INFO, null, newModules.toString(),
						logJpaRepository);
				header = new Header(uuid, HttpStatus.OK,
						ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
				detail = new Detail(returnCode, msg.getString("app.return." + returnCode), newModules);
				response = new Response(header, detail);
				return new ResponseEntity<Response>(response, HttpStatus.OK);
			} else {
				returnCode = "9998"; // No records found
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
