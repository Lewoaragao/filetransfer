package com.filetransfer.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class RotaController {

	@GetMapping("/")
	public ResponseEntity<String> uploadFile() throws IOException {
		return ResponseEntity.ok("Aplicação configurada com sucesso!");
	}

}
