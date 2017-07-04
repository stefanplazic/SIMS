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
import com.guide.dto.LoginDTO;
import com.guide.dto.UserDTO;
import com.guide.service.UserService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GuideApplication.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
public class UserControllerTest {

	private static final String URL_PREFIX = "/api/user";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private UserService userService;

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSaveUser() throws Exception {

		UserDTO user = new UserDTO();
		user.setUsername("stefan");
		user.setPass("stefan");
		user.setFirstName("stefan");
		user.setLastName("Plaza");
		String json = TestUtil.json(user);

		// do mock request
		mockMvc.perform(post(URL_PREFIX + "/register/guide").contentType(contentType).content(json))
				.andExpect(status().isCreated());

		// check for same username
		user.setUsername("stefan");
		user.setPass("stefi");
		user.setFirstName("milos");
		user.setLastName("Plaza");
		json = TestUtil.json(user);

		int previusSize = userService.findAll().size(); //take the number of user
		
		mockMvc.perform(post(URL_PREFIX + "/register/tourist").contentType(contentType).content(json))
				.andExpect(status().isConflict());

		// try to register admin
		// check for same username
		user.setUsername("milos");
		user.setPass("milos");
		user.setFirstName("milos");
		user.setLastName("Plaza");
		json = TestUtil.json(user);
		System.out.println(json);

		mockMvc.perform(post(URL_PREFIX + "/register/admin").contentType(contentType).content(json))
				.andExpect(status().isBadRequest());
		
		//check if num of user greater then priviusSize
		assertThat(userService.findAll()).hasSize(previusSize + 1);

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testLogin() throws Exception {

		LoginDTO dto = new LoginDTO();
		dto.setUsername("admin");
		dto.setPassword("admin");
		String json = TestUtil.json(dto);

		mockMvc.perform(post(URL_PREFIX + "/login").contentType(contentType).content(json)).andExpect(status().isOk());

		// login with wrong username

		dto = new LoginDTO();
		dto.setUsername("niko");
		dto.setPassword("admin");
		json = TestUtil.json(dto);

		mockMvc.perform(post(URL_PREFIX + "/login").contentType(contentType).content(json))
				.andExpect(status().isNotFound());

		// login without username
		dto = new LoginDTO();
		dto.setPassword("admin");
		json = TestUtil.json(dto);

		mockMvc.perform(post(URL_PREFIX + "/login").contentType(contentType).content(json))
				.andExpect(status().isNotFound());
	}
}
