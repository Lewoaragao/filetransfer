package com.filetransfer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {

	private Integer index;
	private String message;
	private String fileName;

	public FileResponse(String message) {
		this.message = message;
	}
}
