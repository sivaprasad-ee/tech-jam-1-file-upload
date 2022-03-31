const http = require('http');
const formidable = require('formidable');
const fs = require('fs');
const config = require('config');

const filePath = config.get('server.upload_file_path');

http.createServer(function (req, res) {
    if (req.url == '/upload') {
        const form = new formidable.IncomingForm();
        form.parse(req, function (err, fields, files) {
            var filepath = files.filetoupload.filepath;
            var newpath = `${filePath}/${files.filetoupload.originalFilename}`;
            fs.rename(filepath, newpath, function (err) {
                if (err) throw err;
                res.write('File uploaded!');
                res.end();
            });
        });
    } else {
        res.writeHead(200, { 'Content-Type': 'text/html' });
        res.write('<form action="upload" method="post" enctype="multipart/form-data">');
        res.write('<input type="file" name="filetoupload"><br>');
        res.write('<input type="submit">');
        res.write('</form>');
        return res.end();
    }
}).listen(config.get('server.port'));