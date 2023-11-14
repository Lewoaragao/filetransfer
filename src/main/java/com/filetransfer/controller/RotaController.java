package com.filetransfer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = "Operação somente para verificar se API está funcionando")
public class RotaController {

	@GetMapping("/")
    @ApiOperation(value = "Verificação de funcionamento da aplicação")
	public ResponseEntity<String> helloWorld() {
		return ResponseEntity.ok("Hello World FileTransfer!");
	}

}
