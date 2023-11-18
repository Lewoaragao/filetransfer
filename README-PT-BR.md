
![Visitors](https://api.visitorbadge.io/api/visitors?path=lewoaragao%2Ffiletransfer&countColor=%233cb371)


**Documentation in English:** [link](https://github.com/Lewoaragao/filetransfer/blob/master/README.md)

**Documentação em Português abaixo**

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

# Endpoints para arquivos do tipo File
## Upload de Arquivo Único
**Endpoint:** `POST /api/file/upload`

**Descrição:** Realiza o upload de um único arquivo do tipo File.

**Parâmetro:** 
- `file`: Arquivo a ser enviado do tipo File.

**Resposta de Sucesso:**
```json
{
  "index": 0,
  "message": "Arquivo enviado com sucesso",
  "fileName": "nome_do_arquivo_20231031123456.txt"
}
```

**Resposta de Erro:**
```json
{
  "index": null,
  "message": "Arquivo vazio",
  "fileName": null
}
```

**Resposta de Erro Inesperado:**
```json
{
  "message": "Falha no upload do arquivo",
  "fileNames": null,
  "files": null
}
```

## Upload de Múltiplos Arquivos
### Arquivos do tipo File
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
      "index": 0,
      "originalFilename": "nome_arquivo_1.txt",
      "downloadFilename": "nome_arquivo_1_202310311234560.txt",
      "filename": "nome_arquivo_1",
      "extension": ".txt"
    },
    {
      "index": 1,
      "originalFilename": "nome_arquivo_2.jpg",
      "downloadFilename": "nome_arquivo_2_202310311234561.jpg",
      "filename": "nome_arquivo_2",
      "extension": ".jpg"
    }
  ]
}
```

**Resposta de Erro:**
```json
{
  "message": "Lista vazia",
  "fileNames": null,
  "files": null
}
```

**Resposta de Erro Inesperado:**
```json
{
  "message": "Falha no upload dos arquivos",
  "fileNames": null,
  "files": null
}
```

# Endpoints para arquivos do tipo Part
## Upload de Arquivo Único
**Endpoint:** `POST /api/part/upload`

**Descrição:** Realiza o upload de um único arquivo do tipo Part.

**Parâmetro:** 
- `part`: Arquivo a ser enviado do tipo File.

**Resposta de Sucesso:**
```json
{
  "index": 0,
  "message": "Arquivo enviado com sucesso",
  "fileName": "nome_do_arquivo_20231031123456.txt"
}
```

**Resposta de Erro:**
```json
{
  "index": null,
  "message": "Arquivo vazio",
  "fileName": null
}
```

**Resposta de Erro Inesperado:**
```json
{
  "message": "Falha no upload do arquivo",
  "fileNames": null,
  "files": null
}
```

## Upload de Múltiplos Arquivos
### Arquivos do tipo part
**Endpoint:** `POST /api/part/uploads`

**Descrição:** Realiza o upload de vários arquivos de uma vez.

**Parâmetro:**
- `parts`: Lista de arquivos a serem enviados do tipo Part.
 
**Resposta de Sucesso:**
```json
{
  "message": "Arquivos enviados com sucesso",
  "fileNames": ["nome_arquivo_1.txt", "nome_arquivo_2.jpg"],
  "files": [
    {
      "index": 0,
      "originalFilename": "nome_arquivo_1.txt",
      "downloadFilename": "nome_arquivo_1_202310311234560.txt",
      "filename": "nome_arquivo_1",
      "extension": ".txt"
    },
    {
      "index": 1,
      "downloadFilename": "nome_arquivo_2_202310311234561.jpg",
      "filename": "nome_arquivo_2",
      "extension": ".jpg"
    }
  ]
}
```

**Resposta de Erro:**
```json
{
  "message": "Lista vazia",
  "fileNames": null,
  "files": null
}
```

**Resposta de Erro Inesperado:**
```json
{
  "message": "Falha no upload dos arquivos",
  "fileNames": null,
  "files": null
}
```

## Download de Arquivo
**Endpoint:** `GET /api/file/download/{fileName}`

**Descrição:** Baixa um arquivo específico pelo seu nome.

**Parâmetros:**
- `fileName`: Nome do arquivo a ser baixado.

**Requisição exemplo:** `GET /api/file/nome_arquivo_1.txt`
 
**Resposta:** O nome_arquivo_1.txt é baixado como um anexo.

## Download de Vários Arquivos em um Arquivo Zip

**Endpoint:** `GET /api/file/downloads`

**Descrição:** Realiza o download de vários arquivos compactados em um arquivo zip.

**Parâmetros:**
- `fileNames`: Lista de nomes de arquivos a serem baixados.

**Requisição exemplo:** `GET /api/file/downloads?fileNames=nome_arquivo_1.txt,nome_arquivo_2.jpg`

**Resposta:** Os arquivos nome_arquivo_1.txt e nome_arquivo_2.jpg são baixados como um anexo compactados em um arquivo zip.

# Observações Gerais
- Se não for enviado nenhum arquivo por parâmetro nos _endpoints_ de upload a resposta terá o status **HTTP 400 (_Bad Request_)**.
- Caso algum arquivo da lista passada no _endpoint_ de download de múltiplos arquivos **não exista** no diretório de upload, ele será ignorado no arquivo zip.
- Se ocorrer algum **erro inesperado** durante algum dos processos, a resposta terá o status **HTTP 500 (_Internal Server Error_)**.

# Contato
## Feito com ❤️ por Leonardo Aragão 👋🏻 Entre em contato!

![Foto de perfil de Leonardo Aragão autor da API FileTransfer](https://avatars.githubusercontent.com/u/65857778?s=96&v=4)

**Autor:** [@Lewoaragao](https://github.com/lewoaragao)

**Celular:** [(85) 99797-2854](https://wa.me/5585997972854)

**Instagram:** https://instagram.com/lewoaragao

**LinkedIn:** http://linkedin.com/in/lewoaragao
