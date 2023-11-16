![Visitors](https://api.visitorbadge.io/api/visitors?path=lewoaragao%2Ffiletransfer&countColor=%233cb371)

**Documentação em Português:** [link](https://github.com/Lewoaragao/filetransfer/blob/master/README-PT-BR.md).
**Documentation in English below.**

# File transfer
🗂 API for file transfer.

**API Description:** It is a service that allows you to upload and download files. It was developed using Spring Boot, Java, and provides endpoints for file manipulation, including uploading and downloading single and multiple files.

## Summary
- Endpoints
    - File type _File_
      - Upload a file
      - Upload multiple files
    - File type _Part_
      - Upload a file
      - Upload multiple files
    - Download a file
    - Download multiple files
- Comments
- Contact

# Endpoints available for File type files
## Single File Upload
**Endpoint:** `POST /api/file/upload`

**Description:** Uploads a single file of type File.

**Parameter:**
- `file`: File to be sent of type File.

**Successful Response:**
```json
{
    "message": "File sent successfully",
    "fileName": "file_name_20231031123456.txt"
}
```

**Error Response:**
```json
{
    "message": "Empty file",
    "FileName": null
}
```

## Multiple File Upload

### File type file
**Endpoint:** `POST /api/file/uploads`

**Description:** Upload multiple files once.

**Parameter:**
- `files`: List of files to be sent of type File.
 
**Successful Response:**
```json
{
    "message": "Files sent successfully",
    "fileNames": ["file_name_1.txt", "file_name_2.jpg"],
    "files": [
      {
        "originalFilename": "file_name_1.txt",
        "downloadFilename": "file_name_1_2023-10-31_12-34-56.txt",
        "filename": "file_name_1",
        "extension": ".txt"
      },
      {
        "originalFilename": "file_name_2.jpg",
        "downloadFilename": "file_name_2_2023-10-31_12-34-56.jpg",
        "filename": "file_name_2",
        "extension": ".jpg"
      }
    ]
}
```

**Error Response:**
```json
{
    "message": "Empty files",
    "filenames": null,
    "files": null
}
```

# Endpoints available for Part type files
## Single File Upload
**Endpoint:** `POST /api/file/upload`

**Description:** Uploads a single file of type File.

**Parameter:**
- `file`: File to be sent of type File.

**Successful Response:**
```json
{
    "message": "File sent successfully",
    "fileName": "file_name_20231031123456.txt"
}
```

**Error Response:**
```json
{
    "message": "Empty file",
    "FileName": null
}
```

## Multiple File Upload

### File type file
**Endpoint:** `POST /api/file/uploads`

**Description:** Upload multiple files once.

**Parameter:**
- `files`: List of files to be sent of type File.
 
**Successful Response:**
```json
{
    "message": "Files sent successfully",
    "fileNames": ["file_name_1.txt", "file_name_2.jpg"],
    "files": [
      {
        "originalFilename": "file_name_1.txt",
        "downloadFilename": "file_name_1_2023-10-31_12-34-56.txt",
        "filename": "file_name_1",
        "extension": ".txt"
      },
      {
        "originalFilename": "file_name_2.jpg",
        "downloadFilename": "file_name_2_2023-10-31_12-34-56.jpg",
        "filename": "file_name_2",
        "extension": ".jpg"
      }
    ]
}
```

**Error Response:**
```json
{
    "message": "Empty files",
    "filenames": null,
    "files": null
}
```


## Download from File
**Endpoint:** `GET /api/file/download/{filename}`

**Description:** Download a specific file by its name.

**Parameters:**
- `filename`: Name of the file to be downloaded.

**Request** `GET /api/file/downloads?filename=filename_1.txt`
 
**Answer:** The content file_name_1.txt is downloaded as an attachment.

## Download multiple files in one Zip file

**Endpoint:** `GET /api/file/downloads`

**Description:** Download multiple files compressed into a zip file.

**Parameters:**
- `fileNames`: List of file names that will be downloaded.

**Request** `GET /api/file/downloads?fileNames=file_name_1.txt,file_name_2.jpg`

**Answer:** The zip file containing file_name_1.txt and file_name_2.jpg are downloaded as an attachment.

## Comments
- If any file in the list does not exist in the upload directory, it will be ignored in the zip file.
- If an error occurs during the process, the response will have HTTP status 500 (Internal Server Error).

# Contact
**Author:** [@Lewoaragao](https://github.com/lewoaragao)

**Cell phone:** [(85) 99797-2854](https://wa.me/5585997972854)
