package com.filetransfer.vo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Part;

import org.springframework.web.multipart.MultipartFile;

public class PartToMultipartFileConverter implements MultipartFile {

    private final Part part;

    public PartToMultipartFileConverter(Part part) {
        this.part = part;
    }

    @Override
    public String getName() {
        return part.getName();
    }

    @Override
    public String getOriginalFilename() {
        return part.getSubmittedFileName();
    }

    @Override
    public String getContentType() {
        return part.getContentType();
    }

    @Override
    public boolean isEmpty() {
        return part.getSize() == 0;
    }

    @Override
    public long getSize() {
        return part.getSize();
    }

    @Override
    public byte[] getBytes() throws IOException {
        try (InputStream inputStream = part.getInputStream()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            return byteArrayOutputStream.toByteArray();
        }
    }


    @Override
    public InputStream getInputStream() throws IOException {
        return part.getInputStream();
    }

    @Override
    public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
        part.write(dest.getAbsolutePath());
    }

    // Se necessário, você pode adicionar outras implementações dos métodos da interface MultipartFile

    public static MultipartFile convert(Part part) {
        return new PartToMultipartFileConverter(part);
    }
}
