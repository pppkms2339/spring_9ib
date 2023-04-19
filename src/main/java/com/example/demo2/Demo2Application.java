package com.example.demo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Demo2Application implements CommandLineRunner {

	@Autowired
	private BeerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Demo2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Beer beer = new Beer("Stella");
		repository.save(beer);
		Iterable<Beer> beers = repository.findAll();
		beers.forEach(System.out::println);
	}
}
