![Visitors](https://api.visitorbadge.io/api/visitors?path=lewoaragao%2Ffiletransfer&countColor=%233cb371)

# FileTransfer
🗂 API para transferência de arquivos.

**Descrição da API:** É um serviço que permite o upload e download de arquivos. Ela foi desenvolvida usando Spring Boot, Java, e oferece endpoints para manipulação de arquivos, incluindo upload e download de arquivos individuais e múltiplos.

## Sumário
- Endpoints
  - Arquivo tipo _File_
    - Upload de um arquivo
    - Upload de vários arquivos
  - Arquivo tipo _Part_
    - Upload de um arquivo
    - Upload de vários arquivos
  - Download de um arquivo
  - Download de vários arquivos
- Observações
- Contato

# Endpoints disponíveis para arquivos do tipo File
## Upload de Arquivo Único
**Endpoint:** `POST /api/file/upload`

**Descrição:** Realiza o upload de um único arquivo do tipo File.

**Parâmetro:** 
- `file`: Arquivo a ser enviado do tipo File.

**Resposta de Sucesso:**
```json
{
  "message": "Arquivo enviado com sucesso",
  "fileName": "nome_do_arquivo_20231031123456.txt"
}
```

**Resposta de Erro:**
```json
{
  "message": "Arquivo vazio",
  "fileName": null
}
```

## Upload de Múltiplos Arquivos

### Arquivo do tipo File
**Endpoint:** `POST /api/file/uploads`

**Descrição:** Realiza o upload de vários arquivos de uma vez.

**Parâmetro:**
- `files`: Lista de arquivos a serem enviados do tipo File.
 
**Resposta de Sucesso:**
```json
{
  "message": "Arquivos enviados com sucesso",
  "fileNames": ["nome_arquivo_1.txt", "nome_arquivo_2.jpg"],
  "files": [
    {
      "originalFilename": "nome_arquivo_1.txt",
      "downloadFilename": "nome_arquivo_1_2023-10-31_12-34-56.txt",
      "filename": "nome_arquivo_1",
      "extension": ".txt"
    },
    {
      "originalFilename": "nome_arquivo_2.jpg",
      "downloadFilename": "nome_arquivo_2_2023-10-31_12-34-56.jpg",
      "filename": "nome_arquivo_2",
      "extension": ".jpg"
    }
  ]
}
```

**Resposta de Erro:**
```json
{
  "message": "Arquivos vazios",
  "fileNames": null,
  "files": null
}
```

# Endpoints disponíveis para arquivos do tipo Part
## Upload de Arquivo Único
**Endpoint:** `POST /api/file/upload`

**Descrição:** Realiza o upload de um único arquivo do tipo File.

**Parâmetro:** 
- `file`: Arquivo a ser enviado do tipo File.

**Resposta de Sucesso:**
```json
{
  "message": "Arquivo enviado com sucesso",
  "fileName": "nome_do_arquivo_20231031123456.txt"
}
```

**Resposta de Erro:**
```json
{
  "message": "Arquivo vazio",
  "fileName": null
}
```

## Upload de Múltiplos Arquivos

### Arquivo do tipo File
**Endpoint:** `POST /api/file/uploads`

**Descrição:** Realiza o upload de vários arquivos de uma vez.

**Parâmetro:**
- `files`: Lista de arquivos a serem enviados do tipo File.
 
**Resposta de Sucesso:**
```json
{
  "message": "Arquivos enviados com sucesso",
  "fileNames": ["nome_arquivo_1.txt", "nome_arquivo_2.jpg"],
  "files": [
    {
      "originalFilename": "nome_arquivo_1.txt",
      "downloadFilename": "nome_arquivo_1_2023-10-31_12-34-56.txt",
      "filename": "nome_arquivo_1",
      "extension": ".txt"
    },
    {
      "originalFilename": "nome_arquivo_2.jpg",
      "downloadFilename": "nome_arquivo_2_2023-10-31_12-34-56.jpg",
      "filename": "nome_arquivo_2",
      "extension": ".jpg"
    }
  ]
}
```

**Resposta de Erro:**
```json
{
  "message": "Arquivos vazios",
  "fileNames": null,
  "files": null
}
```


## Download de Arquivo
**Endpoint:** `GET /api/file/download/{filename}`

**Descrição:** Baixa um arquivo específico pelo seu nome.

**Parâmetros:**
- `filename`: Nome do arquivo a ser baixado.

**Requisição** `GET /api/file/downloads?filename=nome_arquivo_1.txt`
 
**Resposta:** O contendo nome_arquivo_1.txt é baixado como um anexo.

## Download de Vários Arquivos em um Arquivo Zip

**Endpoint:** `GET /api/file/downloads`

**Descrição:** Realiza o download de vários arquivos compactados em um arquivo zip.

**Parâmetros:**
- `fileNames`: Lista de nomes de arquivos a serem baixados.

**Requisição** `GET /api/file/downloads?fileNames=nome_arquivo_1.txt,nome_arquivo_2.jpg`

**Resposta:** O arquivo zip contendo nome_arquivo_1.txt e nome_arquivo_2.jpg são baixados como um anexo.

## Observações
- Caso algum arquivo da lista não exista no diretório de upload, ele será ignorado no arquivo zip.
- Se ocorrer algum erro durante o processo, a resposta terá o status HTTP 500 (Internal Server Error).

# Contato
**Autor:** @Lewoaragao
**Celular:** (85) 99797-2854
