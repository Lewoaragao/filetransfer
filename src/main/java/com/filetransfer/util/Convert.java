package com.filetransfer.util;

import javax.servlet.http.Part;

import org.springframework.web.multipart.MultipartFile;

import com.filetransfer.vo.PartToMultipartFileConverter;

public class Convert {

	public static MultipartFile convertPartToFile(Part part) {
		return new PartToMultipartFileConverter(part);
	}

}
