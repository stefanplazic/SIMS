package com.guide.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guide.dto.EventDTO;
import com.guide.dto.MessagesDTO;
import com.guide.model.Event;
import com.guide.model.Guide;
import com.guide.model.LocationInfo;
import com.guide.model.User;
import com.guide.service.EventService;
import com.guide.service.GuideService;
import com.guide.service.LocationInfoService;
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

	@RequestMapping(value = "/createEvent", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<MessagesDTO> createEvent(@RequestBody EventDTO eventDto, Principal principal) {

		System.out.println(principal.getName());
		User s = userService.findByUsername(principal.getName());
		MessagesDTO dto = new MessagesDTO();
		if (s == null || !(s instanceof Guide)) {
			dto.setError("You are not allowed to create events!!");
			return new ResponseEntity<MessagesDTO>(dto, HttpStatus.BAD_REQUEST);
		}

		// save event
		Event event = new Event();
		event.setDescription(eventDto.getDescription());
		event.setDate(new Date());
		event.setName(eventDto.getName());
		event.setPrice(eventDto.getPrice());

		LocationInfo info = new LocationInfo();
		info.setAdress(eventDto.getInfo().getAdress());
		info.setPostalCode(eventDto.getInfo().getPostalCode());

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
			return new ResponseEntity<List<EventDTO>>(eventsDto, HttpStatus.FOUND);
		}

		// if not type of guide
		else
			return new ResponseEntity<List<EventDTO>>(HttpStatus.NOT_FOUND);

	}
}
