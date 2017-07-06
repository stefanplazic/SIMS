package com.guide.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guide.dto.MessagesDTO;
import com.guide.dto.UserDTO;
import com.guide.model.Admin;
import com.guide.model.User;
import com.guide.service.UserService;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/reportedUsers", method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> reported(Principal principal) {
		User u = userService.findByUsername(principal.getName());
		if (!(u instanceof Admin))
			return new ResponseEntity<List<UserDTO>>(HttpStatus.UNAUTHORIZED);

		// get all users with reported state
		List<User> reportedUsers = userService.findByUserState(User.UserStates.Reported);
		List<UserDTO> usersDto = new ArrayList<UserDTO>();

		for (User user : reportedUsers)
			usersDto.add(new UserDTO(user));

		return new ResponseEntity<List<UserDTO>>(usersDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/blockUser", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<MessagesDTO> block(Principal principal, @RequestBody UserDTO userDTO) {
		User u = userService.findByUsername(principal.getName());

		MessagesDTO dto = new MessagesDTO();
		if (!(u instanceof Admin)) {
			dto.setError("Unauthorized!");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.UNAUTHORIZED);
		}

		// find if user exists and if it is reported
		User user = userService.findOne(userDTO.getId());

		if (user.getUserState() != User.UserStates.Reported) {
			dto.setError("User isn't reported, you cant block him!");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.BAD_REQUEST);
		} else if (user.getUserState() == User.UserStates.Blocked) {
			dto.setError("User is allready blocked!");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.BAD_REQUEST);
		}

		// change user status
		user.setUserState(User.UserStates.Blocked);
		// save user
		userService.save(user);
		dto.setMessage("User is now blocked");

		return new ResponseEntity<MessagesDTO>(dto, HttpStatus.OK);
	}
}
