package com.test.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RestContextController {

	public static final Logger logger = LoggerFactory.getLogger(RestContextController.class);

	// ------------------- health---------------------------------------------

	@RequestMapping(value = "health", method = RequestMethod.GET)
	public ResponseEntity<String> getHealthStatus() {
        return new ResponseEntity<String>("Server up and running!", HttpStatus.OK);
    }

    // ------------------- version ---------------------------------------------

	@RequestMapping(value = "version", method = RequestMethod.GET)
	public ResponseEntity<String> getCurrentVersion() {
        return new ResponseEntity<String>("Current version: 1.0.0", HttpStatus.CHECKPOINT);
    }

}
