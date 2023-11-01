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

	String originalFilename;
	String downloadFilename;
	String filename;
	String extension;

}
