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
import com.filetransfer.vo.FileVO;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

	@Autowired
	FileService service;

	@GetMapping("/")
	public ResponseEntity<String> uploadFile() throws IOException {
		return ResponseEntity.ok("Aplicação configurada com sucesso!");
	}

	@PostMapping("/upload")
	public ResponseEntity<FileResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body(new FileResponse("Arquivo vazio", null));
		}

		String nameFile = service.saveFile(file);
		FileResponse response = new FileResponse("Arquivo enviado com sucesso", nameFile);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/uploads")
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
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
		Path filePath = Paths.get(service.uploadDir).resolve(filename);
		Resource resource = new UrlResource(filePath.toUri());

		MediaType mediaType = this.getMediaTypeForFile(filename);

		if (resource.exists() && resource.isReadable()) {
			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + filename)
					.contentType(mediaType).body(resource);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/downloads")
	public void downloadMultipleFiles(@RequestParam List<String> fileNames, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=filetransfer.zip");

		ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

		try {
			for (String fileName : fileNames) {
				String filePath = service.uploadDir + fileName;

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

	public MediaType getMediaTypeForFile(String filename) {
		String extension = filename.substring(filename.lastIndexOf(".")).toLowerCase();
		extension = extension.replace(".", "");

		switch (extension) {

		case "pdf":
			return MediaType.APPLICATION_PDF;
		case "jpg":
		case "jpeg":
			return MediaType.IMAGE_JPEG;
		case "png":
			return MediaType.IMAGE_PNG;
		case "gif":
			return MediaType.IMAGE_GIF;
		case "bmp":
			return MediaType.valueOf("image/bmp");
		case "svg":
			return MediaType.valueOf("image/svg+xml");
		case "webp":
			return MediaType.valueOf("image/webp");
		case "mp3":
			return MediaType.valueOf("audio/mpeg");
		case "wav":
			return MediaType.valueOf("audio/wav");
		case "ogg":
			return MediaType.valueOf("audio/ogg");
		case "flac":
			return MediaType.valueOf("audio/flac");
		default:
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}

//	public String saveFile(MultipartFile file) throws IOException {
//		UUID uniqueId = UUID.randomUUID();
//		LocalDateTime currentTime = LocalDateTime.now();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
//		String formattedDateTime = currentTime.format(formatter);
//		String uniqueFileName = uniqueId.toString() + "_" + formattedDateTime;
//
//		Path uploadPath = Paths.get(uploadDir);
//		Files.createDirectories(uploadPath);
//		String fileExtension = "."
//				+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//
//		String fileName = uniqueFileName + fileExtension;
//		Path destinationPath = uploadPath.resolve(fileName);
//
//		Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
//
//		return fileName;
//	}

}
