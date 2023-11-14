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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.filetransfer.controller.response.FileResponse;
import com.filetransfer.controller.response.FilesResponse;
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

	@PostMapping("/upload")
	@ApiOperation(value = "Upload de um único arquivo")
	public ResponseEntity<FileResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body(new FileResponse("Arquivo vazio", null));
		}

		String nameFile = service.saveFile(file);
		FileResponse response = new FileResponse("Arquivo enviado com sucesso", nameFile);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/uploads")
	@ApiOperation(value = "Upload de vários arquivos")
	public ResponseEntity<FilesResponse> uploadFiles(@RequestParam("files") List<MultipartFile> files)
			throws IOException {
		List<String> filePaths = new ArrayList<>();
		List<FileVO> filesVO = new ArrayList<>();

		for (MultipartFile file : files) {
			String nameFile = service.saveFile(file);
			filePaths.add(nameFile);

			FileVO fileVO = new FileVO();
			fileVO.setDownloadFilename(nameFile);
			fileVO.setOriginalFilename(file.getOriginalFilename());
			fileVO.setFilename(service.getFileNameWithoutExtension(file));
			fileVO.setExtension(service.getFileExtension(file));
			filesVO.add(fileVO);
		}

		FilesResponse response = new FilesResponse("Arquivos enviados com sucesso", filePaths, filesVO);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/download/{filename}")
	@ApiOperation(value = "Download de um único arquivo")
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
		Path filePath = Paths.get(FileService.uploadDir).resolve(filename);
		Resource resource = new UrlResource(filePath.toUri());

		MediaType mediaType = Util.getMediaTypeForFile(filename);

		if (resource.exists() && resource.isReadable()) {
			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + filename)
					.contentType(mediaType).body(resource);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/downloads")
	@ApiOperation(value = "Download de vários arquivos em um arquivo zip")
	public void downloadMultipleFiles(@RequestParam List<String> fileNames, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=filetransfer.zip");

		ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

		try {
			for (String fileName : fileNames) {
				String filePath = FileService.uploadDir + fileName;

				File file = new File(filePath);

				if (file.exists()) {
					FileInputStream fis = new FileInputStream(file);
					ZipEntry zipEntry = new ZipEntry(file.getName());
					zipOut.putNextEntry(zipEntry);

					FileCopyUtils.copy(fis, zipOut);
					fis.close();
					zipOut.closeEntry();
				}

				response.setStatus(HttpServletResponse.SC_OK);
			}
		} catch (Exception e) {
//	        e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
