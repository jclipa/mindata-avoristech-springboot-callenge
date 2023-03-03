package es.mindata.avoristech.rest;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {
	
	public String generateId() {
		return randomAlphanumeric(7);
	}

}
