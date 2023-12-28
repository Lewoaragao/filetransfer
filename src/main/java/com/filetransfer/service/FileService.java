package com.filetransfer.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	public static final String uploadDir = "src/main/resources/static/uploads";

	public String saveFile(MultipartFile file, Integer index) throws IOException {
		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formattedDateTime = currentTime.format(formatter);

		// Obtenha o nome original do arquivo
		String fileExtension = this.getFileExtension(file);

		// Remova espaços e substitua por underline
		String sanitizedFileName = this.getSanitizedFileName(file);

		// Combine o nome sanitizado com a data/hora formatada e a extensão do arquivo
		String fileName = sanitizedFileName + "_" + formattedDateTime + index + fileExtension;

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

		// Remova a acentuação substituindo caracteres acentuados por não acentuados
		originalFileName = removeAccents(originalFileName);
		
		// Remove todos os pontos de interrogação por underline, 
		// geralmente ocorre por erro de encoding, por isso vem esses
		// pontos de interrogação
		originalFileName = originalFileName.replace("?", "_");

		// Substitua espaços por underscores
		String sanitizedFileName = originalFileName.replaceAll(" ", "_");

		return sanitizedFileName;
	}

	private String removeAccents(String input) {
		// Use Normalizer para remover a acentuação
		String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
		// Substitua caracteres acentuados por não acentuados
		return normalizedString.replaceAll("[^\\p{ASCII}]", "");
	}

	public String getFileExtension(MultipartFile file) {
		// Obtenha o nome original do arquivo
		String originalFileName = file.getOriginalFilename();

		// Obtenha a extensão do arquivo
		String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

		return fileExtension;
	}

}
