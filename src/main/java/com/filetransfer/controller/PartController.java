package com.filetransfer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.filetransfer.response.FileResponse;
import com.filetransfer.response.FilesResponse;
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

	public int index = 0;

	@GetMapping("/")
	@ApiOperation(value = "Verificação de funcionamento do Controller")
	public ResponseEntity<String> verificacaoController() {
		return ResponseEntity.ok("Conferindo se o PartController está configurado corretamente!");
	}

	@PostMapping("/upload")
	@ApiOperation(value = "Upload de um único arquivo usando Part, convertido para File e salvo no servidor")
	public ResponseEntity<FileResponse> uploadPart(@RequestParam("part") Part part) throws IOException {
		try {
			if (part == null || part.getSize() < 1) {
				return ResponseEntity.badRequest().body(new FileResponse("Arquivo vazio"));
			}

			MultipartFile file = Convert.convertPartToFile(part);

			String namePart = service.saveFile(file, index);
			FileResponse response = new FileResponse(index, "Arquivo enviado com sucesso", namePart);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new FileResponse("Falha no upload do arquivo"));
		}
	}

	@PostMapping("/uploads")
	@ApiOperation(value = "Upload de vários arquivos usando Part, convertidos para File e salvos no servidor")
	public ResponseEntity<FilesResponse> uploadParts(@RequestParam("parts") List<Part> parts) throws IOException {
		try {
			List<String> filePaths = new ArrayList<>();
			List<FileVO> filesVO = new ArrayList<>();

			for (Part part : parts) {
				MultipartFile file = Convert.convertPartToFile(part);

				String namePart = service.saveFile(file, index);
				filePaths.add(namePart);

				FileVO fileVO = new FileVO();
				fileVO.setIndex(index);
				fileVO.setDownloadFilename(namePart);
				fileVO.setOriginalFilename(file.getOriginalFilename());
				fileVO.setFilename(service.getFileNameWithoutExtension(file));
				fileVO.setExtension(service.getFileExtension(file));

				filesVO.add(fileVO);
				index++;
			}

			FilesResponse response = new FilesResponse("Arquivos enviados com sucesso", filePaths, filesVO);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new FilesResponse("Falha no upload dos arquivos"));
		}
	}

}
