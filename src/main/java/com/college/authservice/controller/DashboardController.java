package com.college.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin-only")
	public ResponseEntity<String> adminEndpoint() {
	    return ResponseEntity.ok("Hello Admin");
	}
	@PreAuthorize("hasRole('STUDENT')")
	@GetMapping("/student-only")
	public ResponseEntity<String> student() {
	    return ResponseEntity.ok("Hello Student");
	}
	
	
}
