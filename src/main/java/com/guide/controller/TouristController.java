package com.guide.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guide.dto.MessagesDTO;
import com.guide.dto.TourDTO;
import com.guide.model.Tour;
import com.guide.model.Tourist;
import com.guide.model.TouristTour;
import com.guide.model.User;
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

}
