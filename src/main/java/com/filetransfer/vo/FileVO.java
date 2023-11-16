package com.filetransfer.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileVO {

	private String originalFilename;
	private String downloadFilename;
	private String filename;
	private String extension;

}
