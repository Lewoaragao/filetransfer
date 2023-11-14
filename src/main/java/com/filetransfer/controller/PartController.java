package com.filetransfer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.filetransfer.controller.response.FileResponse;
import com.filetransfer.controller.response.FilesResponse;
import com.filetransfer.service.FileService;
import com.filetransfer.util.Convert;
import com.filetransfer.vo.FileVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/part")
@RequiredArgsConstructor
@Api(tags = "Operações com arquivo do tipo Part")
public class PartController {

	@Autowired
	FileService service;

	@PostMapping("/upload")
	@ApiOperation(value = "Upload de um único arquivo usando Part, convertido para File e salvo no servidor")
	public ResponseEntity<FileResponse> uploadPart(@RequestPart("file") Part filePart) throws IOException {
		if (filePart == null || filePart.getSize() < 1) {
			return ResponseEntity.badRequest().body(new FileResponse("Arquivo vazio", null));
		}

		MultipartFile file = Convert.convertPartToFile(filePart);

		String namePart = service.saveFile(file);
		FileResponse response = new FileResponse("Arquivo enviado com sucesso", namePart);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/uploads")
	@ApiOperation(value = "Upload de vários arquivos usando Part, convertidos para File e salvos no servidor")
	public ResponseEntity<FilesResponse> uploadParts(@RequestParam("files") List<Part> files) throws IOException {
		List<String> filePaths = new ArrayList<>();
		List<FileVO> filesVO = new ArrayList<>();

		for (Part part : files) {

			MultipartFile file = Convert.convertPartToFile(part);

			String namePart = service.saveFile(file);
			filePaths.add(namePart);

			FileVO fileVO = new FileVO();
			fileVO.setDownloadFilename(namePart);
			fileVO.setOriginalFilename(file.getOriginalFilename());
			fileVO.setFilename(service.getFileNameWithoutExtension(file));
			fileVO.setExtension(service.getFileExtension(file));
			filesVO.add(fileVO);
		}

		FilesResponse response = new FilesResponse("Arquivos enviados com sucesso", filePaths, filesVO);
		return ResponseEntity.ok(response);
	}

}
