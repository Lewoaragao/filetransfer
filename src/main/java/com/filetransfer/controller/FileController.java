package com.filetransfer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.filetransfer.response.FileResponse;
import com.filetransfer.response.FilesResponse;
import com.filetransfer.service.FileService;
import com.filetransfer.util.Util;
import com.filetransfer.vo.FileVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Api(tags = "Operações com arquivo do tipo File")
public class FileController {

	@Autowired
	FileService service;

	public int index = 0;

	@GetMapping("/")
	@ApiOperation(value = "Verificação de funcionamento do Controller")
	public ResponseEntity<String> verificacaoController() {
		return ResponseEntity.ok("Conferindo se o FileController está configurado corretamente!");
	}

	@PostMapping("/upload")
	@ApiOperation(value = "Upload de um único arquivo")
	public ResponseEntity<FileResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		try {
			if (file.isEmpty()) {
				return ResponseEntity.badRequest().body(new FileResponse("Arquivo vazio"));
			}

			String nameFile = service.saveFile(file, index);
			FileResponse response = new FileResponse(index, "Arquivo enviado com sucesso", nameFile);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new FileResponse("Falha no upload do arquivo"));
		}
	}

	@PostMapping("/uploads")
	@ApiOperation(value = "Upload de vários arquivos")
	public ResponseEntity<FilesResponse> uploadFiles(@RequestParam("files") List<MultipartFile> files)
			throws IOException {
		try {
			if (files.isEmpty()) {
				return ResponseEntity.badRequest().body(new FilesResponse("Lista vazio"));
			}

			List<String> filePaths = new ArrayList<>();
			List<FileVO> filesVO = new ArrayList<>();

			for (MultipartFile file : files) {
				String nameFile = service.saveFile(file, index);
				filePaths.add(nameFile);

				FileVO fileVO = new FileVO();
				fileVO.setIndex(index);
				fileVO.setDownloadFilename(nameFile);
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
			return ResponseEntity.internalServerError()
					.body(new FilesResponse("Falha no upload dos arquivos", null, null));
		}
	}

	@GetMapping("/download/{fileName}")
	@ApiOperation(value = "Download de um único arquivo")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws IOException {
		try {
			Path filePath = Paths.get(FileService.uploadDir).resolve(fileName);
			Resource resource = new UrlResource(filePath.toUri());

			MediaType mediaType = Util.getMediaTypeForFile(fileName);

			if (resource.exists() && resource.isReadable()) {
				return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + fileName)
						.contentType(mediaType).body(resource);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/downloads")
	@ApiOperation(value = "Download de vários arquivos em um arquivo zip")
	public void downloadMultipleFiles(@RequestParam("fileNames") List<String> fileNames, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=filetransfer.zip");

		ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

		try {
			for (String fileName : fileNames) {
				String filePath = FileService.uploadDir + File.separator + fileName;
				File fileToZip = new File(filePath);
				FileInputStream fis = new FileInputStream(fileToZip);
				ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
				zipOut.putNextEntry(zipEntry);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}

				fis.close();
			}

			zipOut.close();
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
