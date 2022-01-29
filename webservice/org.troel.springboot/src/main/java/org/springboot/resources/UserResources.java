package org.springboot.resources;

import org.springboot.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserResources {

	@GetMapping
	public ResponseEntity<User> findAll() {
		User user = new User(1L, "Thales", "thales.roel@devcoffee.com.br", "1998387488", "123456");
		return ResponseEntity.ok().body(user);
	}
}
