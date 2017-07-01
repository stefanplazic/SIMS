package com.guide.service;

import java.util.Date;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.guide.model.Admin;
import com.guide.repository.AdminRepository;

@Component
public class AppLoader implements ApplicationRunner {

	private AdminRepository rep;

	public AppLoader(AdminRepository rep) {
		this.rep = rep;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (rep.findAll().size() == 0) {
			// if there is no admin in database insert one
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			Admin admin = new Admin();
			admin.setUsername("admin");
			admin.setPass(encoder.encode("admin"));
			admin.setRegistrationDate(new Date());

			// save it to database
			rep.save(admin);
			System.out.println("Admin added to database");
		}

	}

}
