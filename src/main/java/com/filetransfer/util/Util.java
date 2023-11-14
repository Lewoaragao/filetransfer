package com.filetransfer.util;

import org.springframework.http.MediaType;

public class Util {

	public static MediaType getMediaTypeForFile(String filename) {
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
	
}
