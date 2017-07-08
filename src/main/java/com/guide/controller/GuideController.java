package com.guide.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guide.dto.EventDTO;
import com.guide.dto.MessagesDTO;
import com.guide.dto.TourDTO;
import com.guide.model.Event;
import com.guide.model.Guide;
import com.guide.model.LocationInfo;
import com.guide.model.Tour;
import com.guide.model.User;
import com.guide.service.CityService;
import com.guide.service.EventService;
import com.guide.service.GuideService;
import com.guide.service.LocationInfoService;
import com.guide.service.TourService;
import com.guide.service.TouristTourService;
import com.guide.service.UserService;

@RestController
@RequestMapping(value = "/api/guide")
public class GuideController {

	@Autowired
	private GuideService guideService;

	@Autowired
	private EventService eventService;

	@Autowired
	private UserService userService;

	@Autowired
	private LocationInfoService locationInfoService;

	@Autowired
	private TourService tourService;

	@Autowired
	private TouristTourService touristTourService;
	
	@Autowired
	private CityService cityService;

	@RequestMapping(value = "/createEvent/{id}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<MessagesDTO> createEvent(@RequestBody EventDTO eventDto, Principal principal, @PathVariable Long id) {

		User s = userService.findByUsername(principal.getName());
		MessagesDTO dto = new MessagesDTO();
		if (s == null || !(s instanceof Guide)) {
			dto.setError("You are not allowed to create events!!");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.BAD_REQUEST);
		}

		// save event
		Event event = new Event();
		event.setDescription(eventDto.getDescription());
		//event.setDate(new Date());
		event.setDate(eventDto.getDate());
		event.setName(eventDto.getName());
		event.setPrice(eventDto.getPrice());

		LocationInfo info = new LocationInfo();
		info.setAdress(eventDto.getInfo().getAdress());
		info.setPostalCode(eventDto.getInfo().getPostalCode());
		info.setCity(cityService.findOne(id));

		// save info
		info = locationInfoService.save(info);

		event.setLocInfo(info);
		event.setGuide((Guide) s);
		eventService.save(event);

		dto.setMessage(eventDto.getName() + " created");
		return new ResponseEntity<MessagesDTO>(dto, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/viewEvents", method = RequestMethod.GET)
	public ResponseEntity<List<EventDTO>> viewEvents(Principal principal) {

		User u = userService.findByUsername(principal.getName());
		if (u == null)
			return new ResponseEntity<List<EventDTO>>(HttpStatus.UNAUTHORIZED);

		if (u instanceof Guide) {
			List<Event> events = eventService.findByGuide((Guide) u);
			List<EventDTO> eventsDto = new ArrayList<EventDTO>();

			// populate eventsDto
			for (Event ev : events) {
				eventsDto.add(new EventDTO(ev));
			}

			// return eventsDto
			return new ResponseEntity<List<EventDTO>>(eventsDto, HttpStatus.OK);
		}

		// if not type of guide
		else
			return new ResponseEntity<List<EventDTO>>(HttpStatus.NOT_FOUND);

	}

	@RequestMapping(value = "/editEvent", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<EventDTO> editEvent(Principal principal, @RequestBody EventDTO eventDTO) {

		User user = userService.findByUsername(principal.getName());
		if (user == null)
			return new ResponseEntity<EventDTO>(HttpStatus.UNAUTHORIZED);

		Event e = eventService.findOne(eventDTO.getId());
		if (e == null || e.getGuide().getId() != user.getId())
			return new ResponseEntity<EventDTO>(HttpStatus.BAD_REQUEST);

		e.setDate(eventDTO.getDate());
		e.setDescription(eventDTO.getDescription());
		e.setName(eventDTO.getName());
		e.setPrice(eventDTO.getPrice());

		e = eventService.save(e);

		return new ResponseEntity<EventDTO>(new EventDTO(e), HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteEvent/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<MessagesDTO> deleteEvent(Principal principal, @PathVariable Long id) {
		User user = userService.findByUsername(principal.getName());
		Event e = eventService.findOne(id);
		MessagesDTO dto = new MessagesDTO();
		if (user == null || e.getGuide().getId() != user.getId()) {
			dto.setError("Wrong request");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.UNAUTHORIZED);
		}

		eventService.remove(id);
		dto.setMessage("Event deleted.");
		return new ResponseEntity<MessagesDTO>(dto, HttpStatus.OK);
	}

	@RequestMapping(value = "/createTour", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<MessagesDTO> createTour(@RequestBody TourDTO tourDTO, Principal principal) {

		User s = userService.findByUsername(principal.getName());
		MessagesDTO dto = new MessagesDTO();
		if (s == null || !(s instanceof Guide)) {
			dto.setError("You are not allowed to create events!!");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.BAD_REQUEST);
		}

		Tour tour = new Tour();
		tour.setBeginDate(tourDTO.getBeginDate());
		tour.setEndDate(tourDTO.getEndDate());
		tour.setName(tourDTO.getName());
		tour.setGuide((Guide) s);

		for (EventDTO eventDto : tourDTO.getEvents()) {
			Event e = eventService.findOne(eventDto.getId());
			if (e != null) {
				tour.getEvents().add(e);// add event
			}

		}
		tourService.save(tour);// save tour

		dto.setMessage("Tour saved");
		return new ResponseEntity<MessagesDTO>(dto, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/deleteTour/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<MessagesDTO> deleteTour(Principal principal, @PathVariable Long id) {

		MessagesDTO dto = new MessagesDTO();
		// check if user is logged and if hi is a guide
		User user = userService.findByUsername(principal.getName());
		if (user == null || !(user instanceof Guide)) {
			dto.setError("Must be logged in!");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.UNAUTHORIZED);
		}
		Tour tour = tourService.findOne(id);

		System.out.println(tour.getGuide().getId());
		if (tour == null || tour.getGuide().getId() != user.getId()) {
			dto.setError("Wrong request!");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.UNAUTHORIZED);
		}

		// delete from touristTour
		touristTourService.removeByTour(tour);

		// remove tour
		tourService.remove(id);
		dto.setMessage("Removed");

		return new ResponseEntity<MessagesDTO>(dto, HttpStatus.OK);
	}

	@RequestMapping(value = "/editTour", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<TourDTO> editTour(Principal principal, @RequestBody TourDTO tourDTO) {
		// first check if user is logged
		User user = userService.findByUsername(principal.getName());
		if (user == null)
			return new ResponseEntity<TourDTO>(HttpStatus.UNAUTHORIZED);

		// check if user is owner of the tour
		Tour tour = tourService.findOne(tourDTO.getId());
		if (tour != null && tour.getGuide().getId() == user.getId()) {
			tour.setBeginDate(tourDTO.getBeginDate());
			tour.setEndDate(tourDTO.getEndDate());
			tour.setName(tourDTO.getName());

			// hashset
			Set<Event> events = new HashSet<Event>();
			for (EventDTO evDto : tourDTO.getEvents()) {

				Event event = eventService.findOne(evDto.getId());
				if (event != null)
					events.add(event);
			}
			// set hashset to tour
			tour.setEvents(events);
			System.out.println(tour.getId());
			tour = tourService.save(tour);

			return new ResponseEntity<TourDTO>(new TourDTO(tour), HttpStatus.OK);
		}

		else
			return new ResponseEntity<TourDTO>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/viewTours", method = RequestMethod.GET)
	public ResponseEntity<List<TourDTO>> viewTours(Principal principal) {

		User u = userService.findByUsername(principal.getName());
		if (u == null)
			return new ResponseEntity<List<TourDTO>>(HttpStatus.UNAUTHORIZED);

		if (u instanceof Guide) {
			List<Tour> tours = tourService.findByGuide((Guide) u);
			List<TourDTO> toursDto = new ArrayList<TourDTO>();

			// populate eventsDto
			for (Tour ev : tours) {
				toursDto.add(new TourDTO(ev));
			}

			// return eventsDto
			return new ResponseEntity<List<TourDTO>>(toursDto, HttpStatus.OK);
		}

		// if not type of guide
		else
			return new ResponseEntity<List<TourDTO>>(HttpStatus.NOT_FOUND);

	}

}
