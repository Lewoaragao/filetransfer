package com.filetransfer.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	public static final String uploadDir = "src/main/resources/static/uploads";

	public String saveFile(MultipartFile file) throws IOException {
		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formattedDateTime = currentTime.format(formatter);

		// Obtenha o nome original do arquivo
		String fileExtension = this.getFileExtension(file);

		// Remova espaços e substitua por underline
		String sanitizedFileName = this.getSanitizedFileName(file);

		// Combine o nome sanitizado com a data/hora formatada e a extensão do arquivo
		String fileName = sanitizedFileName + "_" + formattedDateTime + fileExtension;

		Path uploadPath = Paths.get(uploadDir);
		Files.createDirectories(uploadPath);
		Path destinationPath = uploadPath.resolve(fileName);

		Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

		return fileName;
	}

	public String getFileNameWithoutExtension(MultipartFile file) {
		// Obtenha o nome original do arquivo
		String originalFileName = file.getOriginalFilename();

		// Verifique se o nome original do arquivo contém uma extensão
		int lastIndex = originalFileName.lastIndexOf('.');
		
		if (lastIndex >= 0) {
			// Remova a extensão do nome original
			return originalFileName.substring(0, lastIndex);
		} else {
			// Se não houver extensão, retorne o nome original completo
			return originalFileName;
		}
	}

	public String getSanitizedFileName(MultipartFile file) {
		// Obtenha o nome original do arquivo
		String originalFileName = this.getFileNameWithoutExtension(file);
		
		// Substitua espaços por underscores
		String sanitizedFileName = originalFileName.replaceAll(" ", "_");

		return sanitizedFileName;
	}

	public String getFileExtension(MultipartFile file) {
		// Obtenha o nome original do arquivo
		String originalFileName = file.getOriginalFilename();

		// Obtenha a extensão do arquivo
		String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

		return fileExtension;
	}

}
