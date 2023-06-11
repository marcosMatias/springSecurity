package br.com.spring.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.security.model.record.FormRecord;

@RestController
@RequestMapping("/register")
public class FormController {

	
	@PostMapping
	public ResponseEntity<FormRecord> personRegister(@RequestBody FormRecord form){
		
		return ResponseEntity.ok(form);
		
	}
	
	}
