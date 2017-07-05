package com.guide.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guide.dto.LoginDTO;
import com.guide.dto.MessagesDTO;
import com.guide.dto.TourDTO;
import com.guide.dto.UserDTO;
import com.guide.model.Admin;
import com.guide.model.City;
import com.guide.model.Event;
import com.guide.model.Guide;
import com.guide.model.LocationInfo;
import com.guide.model.Tour;
import com.guide.model.Tourist;
import com.guide.model.User;
import com.guide.security.TokenUtils;
import com.guide.service.CityService;
import com.guide.service.GuideService;
import com.guide.service.LocationInfoService;
import com.guide.service.TourService;
import com.guide.service.TouristService;
import com.guide.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	private GuideService guideService;

	@Autowired
	private TouristService touristService;

	@Autowired
	private UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private TourService tourService;

	@Autowired
	private LocationInfoService locationInfoService;

	@Autowired
	private CityService cityService;

	/**
	 * Used for user login, it uses POST Method , and requires LoginDTO
	 * 
	 * @see LoginDTO
	 * @param loginDTO
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<MessagesDTO> login(@RequestBody LoginDTO loginDTO) {

		try {

			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
					loginDTO.getPassword());
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Reload user details so we can generate token
			UserDetails details = userDetailsService.loadUserByUsername(loginDTO.getUsername());

			User user = userService.findByUsername(details.getUsername());

			MessagesDTO m = new MessagesDTO();
			m.setId(user.getId());
			m.setJwt(tokenUtils.generateToken(details));

			if (user instanceof Admin)
				m.setRola("ADMIN");
			else if (user instanceof Tourist)
				m.setRola("TOURIST");
			else
				m.setRola("GUIDE");

			return new ResponseEntity<MessagesDTO>(m, HttpStatus.OK);
		} catch (Exception ex) {
			MessagesDTO m = new MessagesDTO();
			m.setError("Wrong login");
			return new ResponseEntity<MessagesDTO>(m, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/register/{type}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<MessagesDTO> register(@RequestBody UserDTO userDTO, @PathVariable String type) {
		MessagesDTO dto = new MessagesDTO();

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// check if username is already taken
		User user = userService.findByUsername(userDTO.getUsername());
		if (user != null) {
			dto.setError("Username taken!");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.CONFLICT);
		} else if (type.equalsIgnoreCase("guide")) {
			// guide type
			Guide guide = new Guide();
			guide.setFirstName(userDTO.getFirstName());
			guide.setLastName(userDTO.getLastName());
			guide.setPass(encoder.encode(userDTO.getPass()));
			guide.setUsername(userDTO.getUsername());
			guide.setRegistrationDate(new Date());

			// save to databse
			guideService.save(guide);

			// set massage
			dto.setMessage("User of type guide created");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.CREATED);
		} else if (type.equalsIgnoreCase("tourist")) {
			// tourist type
			Tourist tourist = new Tourist();
			tourist.setFirstName(userDTO.getFirstName());
			tourist.setLastName(userDTO.getLastName());
			tourist.setPass(encoder.encode(userDTO.getPass()));
			tourist.setUsername(userDTO.getUsername());
			tourist.setRegistrationDate(new Date());

			// save to database
			touristService.save(tourist);

			dto.setMessage("User of type tourist created");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.CREATED);
		} else {
			dto.setError(type + " type  isn't allowed!");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/allTours", method = RequestMethod.GET)
	public ResponseEntity<List<TourDTO>> searchTours() {

		List<Tour> tours = tourService.findAll();
		List<TourDTO> toursDto = new ArrayList<TourDTO>();

		for (Tour tour : tours)
			toursDto.add(new TourDTO(tour));

		return new ResponseEntity<List<TourDTO>>(toursDto, HttpStatus.FOUND);
	}

	@RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<TourDTO>> searchbyCity(@PathVariable Long id) {

		// find location infos
		City city = cityService.findOne(id);
		List<LocationInfo> infos = locationInfoService.findByCity(city);

		// find all events
		List<TourDTO> dtos = new ArrayList<TourDTO>();
		for (LocationInfo info : infos) {
			Event e = info.getEvent();
			Set<Tour> tours = e.getTours();
			for (Tour t : tours) {
				TourDTO tDto = new TourDTO(t);
				if (!dtos.contains(tDto))
					dtos.add(tDto);
			}
		}

		return new ResponseEntity<List<TourDTO>>(dtos, HttpStatus.FOUND);
	}

}
