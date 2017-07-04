package com.guide.controller.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.guide.GuideApplication;
import com.guide.TestUtil;
import com.guide.dto.EventDTO;
import com.guide.dto.LocationInfoDTO;
import com.guide.model.Guide;
import com.guide.service.EventService;
import com.guide.service.UserService;
import com.sun.security.auth.UserPrincipal;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GuideApplication.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
public class GuideControllerTest {

	private static final String URL_PREFIX = "/api/guide";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserService userService;

	@Autowired
	private EventService eventService;

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCreateEvent() throws Exception {

		// create guide
		Guide guide = new Guide();
		guide.setFirstName("Milos");
		guide.setLastName("Petrovic");
		guide.setUsername("milos");
		guide.setPass("somePass");

		// save guide
		guide = (Guide) userService.save(guide);

		// create events
		EventDTO dto = new EventDTO();
		dto.setDescription("someDess");
		dto.setName("event1");
		dto.setPrice(125);

		LocationInfoDTO infoDTO = new LocationInfoDTO();
		infoDTO.setAdress("knez mihajlova");
		infoDTO.setPostalCode("110000");
		dto.setInfo(infoDTO);

		String json = TestUtil.json(dto);

		int previusSize = eventService.findAll().size();

		// do mock request
		mockMvc.perform(post(URL_PREFIX + "/createEvent").contentType(contentType).content(json)
				.principal(new UserPrincipal(guide.getUsername()))).andExpect(status().isCreated());

		// check num of events
		assertThat(eventService.findAll()).hasSize(previusSize + 1);
	}
}
