package com.filetransfer.controller.response;

import java.util.List;

import com.filetransfer.vo.FileVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilesResponse {

	private String message;
	private List<String> fileNames;
	private List<FileVO> files;

}
