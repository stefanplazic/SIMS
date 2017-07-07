package com.guide.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guide.dto.MessagesDTO;
import com.guide.dto.TourDTO;
import com.guide.dto.UserDTO;
import com.guide.model.Admin;
import com.guide.model.Tour;
import com.guide.model.Tourist;
import com.guide.model.TouristTour;
import com.guide.model.User;
import com.guide.model.User.UserStates;
import com.guide.service.TourService;
import com.guide.service.TouristTourService;
import com.guide.service.UserService;

@RestController
@RequestMapping(value = "/api/tourist")
public class TouristController {

	@Autowired
	private UserService userService;

	@Autowired
	private TouristTourService touristTourService;

	@Autowired
	private TourService tourService;

	@RequestMapping(value = "/applay", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<MessagesDTO> applay(Principal principal, @RequestBody TourDTO tourDTO) {

		User u = userService.findByUsername(principal.getName());

		if (!(u instanceof Tourist)) {

			return new ResponseEntity<MessagesDTO>(HttpStatus.UNAUTHORIZED);
		}
		// cast user to tourist
		Tourist tourist = (Tourist) u;

		MessagesDTO dto = new MessagesDTO();
		// check if tour exists
		List<TouristTour> t = touristTourService.findByTourist(tourist);
		Tour tour = tourService.findOne(tourDTO.getId());
		if (tour == null) {
			dto.setError("Tour doesn't exists");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.NOT_FOUND);
		}
		// already applaied
		for (TouristTour tt : t) {
			if (tt.getTour() == tour) {
				dto.setError("User already go on this tour");
				return new ResponseEntity<MessagesDTO>(dto, HttpStatus.BAD_REQUEST);
			}
		}

		// save tourist and tour
		TouristTour touristTour = new TouristTour();
		touristTour.setAssignmentDate(new Date());
		touristTour.setTour(tour);
		touristTour.setTourist(tourist);

		touristTourService.save(touristTour);

		return new ResponseEntity<MessagesDTO>(HttpStatus.OK);
	}

	@RequestMapping(value = "/unsubscribe", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<MessagesDTO> unsubscribe(Principal principal, @RequestBody TourDTO tourDTO) {

		User u = userService.findByUsername(principal.getName());

		if (!(u instanceof Tourist)) {

			return new ResponseEntity<MessagesDTO>(HttpStatus.UNAUTHORIZED);
		}
		// cast user to tourist
		Tourist tourist = (Tourist) u;

		MessagesDTO dto = new MessagesDTO();
		// check if tour exists
		List<TouristTour> t = touristTourService.findByTourist(tourist);
		Tour tour = tourService.findOne(tourDTO.getId());
		if (tour == null) {
			dto.setError("Tour doesn't exists");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.NOT_FOUND);
		}

		else if (tourDTO.isActive()) {
			dto.setError("Tour allready started!");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.BAD_REQUEST);
		}

		// already applaied
		for (TouristTour tt : t) {
			if (tt.getTour() == tour) {
				// deleate tour
				touristTourService.remove(tt.getId());
				dto.setMessage("Succesfull");
				return new ResponseEntity<MessagesDTO>(dto, HttpStatus.OK);
			}
		}

		dto.setError("YOu are not subscribed");

		return new ResponseEntity<MessagesDTO>(dto, HttpStatus.OK);
	}

	@RequestMapping(value = "/tours", method = RequestMethod.GET)
	public ResponseEntity<List<TourDTO>> tours(Principal principal) {

		User u = userService.findByUsername(principal.getName());
		if (u == null || !(u instanceof Tourist))
			return new ResponseEntity<List<TourDTO>>(HttpStatus.UNAUTHORIZED);

		// get all tours
		List<TouristTour> touristTours = touristTourService.findByTourist((Tourist) u);
		List<TourDTO> dtos = new ArrayList<TourDTO>();

		for (TouristTour touristTour : touristTours)
			dtos.add(new TourDTO(touristTour.getTour()));
		return new ResponseEntity<List<TourDTO>>(dtos, HttpStatus.OK);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> all(Principal principal) {
		User me = userService.findByUsername(principal.getName());
		if(me == null)
			return new ResponseEntity<List<UserDTO>>( HttpStatus.UNAUTHORIZED);
		
		List<User> users = userService.findAll();
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		
		for (User u : users)
			if (!(u instanceof Admin) && u.getUserState() != UserStates.Blocked && u.getUserState() != UserStates.Reported && me != u)
				dtos.add(new UserDTO(u));

		return new ResponseEntity<List<UserDTO>>(dtos, HttpStatus.OK);
	}

	@RequestMapping(value = "/report/{id}", method = RequestMethod.GET)
	public ResponseEntity<MessagesDTO> report(Principal principal, @PathVariable Long id) {

		User u = userService.findByUsername(principal.getName());
		if (u == null)
			return new ResponseEntity<MessagesDTO>(HttpStatus.UNAUTHORIZED);

		u = userService.findOne(id);
		u.setUserState(UserStates.Reported);
		userService.save(u);

		return new ResponseEntity<MessagesDTO>(HttpStatus.OK);
	}
}
