package com.example.springauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin("http://localhost:4200")
public class SpringAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAuthApplication.class, args);
	}

//	@Bean
//	@SuppressWarnings("ALL")
//	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//		UserDetails one = User.withDefaultPasswordEncoder().roles("admin", "user").username("admin").password("pw").build();
//		UserDetails two = User.withDefaultPasswordEncoder().roles("user").username("truong").password("pw").build();
//		return new InMemoryUserDetailsManager(one, two);
//	}

}
