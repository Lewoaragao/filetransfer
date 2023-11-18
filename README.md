![Visitors](https://api.visitorbadge.io/api/visitors?path=lewoaragao%2Ffiletransfer&countColor=%233cb371)

**Documentação em Português:** [link](https://github.com/Lewoaragao/filetransfer/blob/master/README-PT-BR.md)

**Documentation in English below**

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

# Endpoints for File type files
## Single File Upload
**Endpoint:** `POST /api/file/upload`

**Description:** Uploads a single file of type File.

**Parameter:**
- `file`: File to be sent of type File.

**Successful Response:**
```json
{
   "index": 0,
   "message": "File sent successfully",
   "fileName": "file_name_20231031123456.txt"
}
```

**Error Response:**
```json
{
   "index": null,
   "message": "Empty file",
   "fileName": null
}
```

**Unexpected Error Response:**
```json
{
   "message": "File upload failed",
   "fileNames": null,
   "files": null
}
```

## Multiple File Upload
### File type files
**Endpoint:** `POST /api/file/uploads`

**Description:** Uploads multiple files at once.

**Parameter:**
- `files`: List of files to be sent of type File.
 
**Successful Response:**
```json
{
   "message": "Files sent successfully",
   "fileNames": ["file name 1.txt", "file name 2.jpg"],
   "files": [
     {
       "index": 0,
       "originalFilename": "file_name_1.txt",
       "downloadFilename": "file_name_1_202310311234560.txt",
       "filename": "file_name_1",
       "extension": ".txt"
     },
     {
       "index": 1,
       "originalFilename": "file_name_2.jpg",
       "downloadFilename": "file_name_2_202310311234561.jpg",
       "filename": "file_name_2",
       "extension": ".jpg"
     }
   ]
}
```

**Error Response:**
```json
{
   "message": "Empty list",
   "fileNames": null,
   "files": null
}
```

**Unexpected Error Response:**
```json
{
   "message": "Files upload failed",
   "fileNames": null,
   "files": null
}
```

# Endpoints for Part type files
## Single File Upload
**Endpoint:** `POST /api/part/upload`

**Description:** Uploads a single file of type Part.

**Parameter:**
- `part`: File to be sent of type File.

**Successful Response:**
```json
{
   "index": 0,
   "message": "File sent successfully",
   "fileName": "file_name_20231031123456.txt"
}
```

**Error Response:**
```json
{
   "index": null,
   "message": "Empty file",
   "fileName": null
}
```

**Unexpected Error Response:**
```json
{
   "message": "File upload failed",
   "fileNames": null,
   "files": null
}
```

## Multiple File Upload
### Part type files
**Endpoint:** `POST /api/part/uploads`

**Description:** Uploads multiple files at once.

**Parameter:**
- `parts`: List of files to be sent of type Part.
 
**Successful Response:**
```json
{
   "message": "Files sent successfully",
   "fileNames": ["file name 1.txt", "file name 2.jpg"],
   "files": [
     {
       "index": 0,
       "originalFilename": "file_name_1.txt",
       "downloadFilename": "file_name_1_202310311234560.txt",
       "filename": "file_name_1",
       "extension": ".txt"
     },
     {
       "index": 1,
       "downloadFilename": "file_name_2_202310311234561.jpg",
       "filename": "file_name_2",
       "extension": ".jpg"
     }
   ]
}
```

**Error Response:**
```json
{
   "message": "Empty list",
   "fileNames": null,
   "files": null
}
```

**Unexpected Error Response:**
```json
{
   "message": "Files upload failed",
   "fileNames": null,
   "files": null
}
```

## File Download
**Endpoint:** `GET /api/file/download/{fileName}`

**Description:** Downloads a specific file by its name.

**Parameters:**
- `fileName`: Name of the file to be downloaded.

**Example request:** `GET /api/file/name_arquivo_1.txt`
 
**Answer:** File_name_1.txt is downloaded as an attachment.

## Download Multiple Files in One Zip File

**Endpoint:** `GET /api/file/downloads`

**Description:** Downloads several files compressed in a zip file.

**Parameters:**
- `fileNames`: List of file names to be downloaded.

**Example request:** `GET /api/file/downloads?fileNames=file_name_1.txt,file_name_2.jpg`

**Answer:** The files file_name_1.txt and file_name_2.jpg are downloaded as an attachment compressed in a zip file.

# General observations
- If no file is sent per parameter in the upload _endpoints_, the response will have the status **HTTP 400 (_Bad Request_)**.
- If any file from the list passed in the multiple file download _endpoint_ **does not exist** in the upload directory, it will be ignored in the zip file.
- If an **unexpected error** occurs during any of the processes, the response will have the status **HTTP 500 (_Internal Server Error_)**.

# Contact
## Made with ❤️ by Leonardo Aragão 👋🏻 Get in touch!

![Profile photo of Leonardo Aragão author of the FileTransfer API](https://avatars.githubusercontent.com/u/65857778?s=96&v=4)
