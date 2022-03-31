const http = require('http');
const formidable = require('formidable');
const fs = require('fs');
const config = require('config');

const filePath = config.get('server.upload_file_path');

http.createServer(function (req, res) {
    const form = new formidable.IncomingForm();
    form.parse(req, function (err, fields, data) {
        var filepath = data.filetoupload.filepath;
        var newpath = `${filePath}/${data.filetoupload.originalFilename}`;
        fs.rename(filepath, newpath, function (err) {
            if (err) throw err;
            res.write('File uploaded!');
            res.end();
        });
    });
}).listen(config.get('server.port'));