# TechJam - File Upload

## Agenda:
* Learn more about how file upload functionality works?
* How to deal with large file uploads?
* How to handle file uploads in efficient manner?

## Stages

### Basic File Uploading
1. Simple file upload
2. Multiple files upload.
3. File uploading using Streaming approach.
4. Break and do parallel upload chunks

### Advanced File Uploading
1. Upload optimizations - can images be skewed, videos reduced size
2. Region based uploads CDN friendly?
3. P2P uploads similar to torrents.
4. How does zoom save video on cloud vs Desktop.
5. How does google drive, dropbox or iCloud upload files?
6. How deltas are managed in cloud shared drives?


## Sample Large File Sources
* http://xcal1.vodafone.co.uk/
* https://fastest.fish/test-files

## How HTTP FileUpload Works?

```shell
POST /upload HTTP/1.1
Host: localhost:3000
Content-Length: 1325
Origin: http://localhost:3000
... other headers ...
Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryePkpFF7tjBAqx29L

------WebKitFormBoundaryePkpFF7tjBAqx29L
Content-Disposition: form-data; name="age"

25
------WebKitFormBoundaryePkpFF7tjBAqx29L
Content-Disposition: form-data; name="file"; filename="upload.png"
Content-Type: image/png

... contents of file goes here ...
------WebKitFormBoundaryePkpFF7tjBAqx29L--
```