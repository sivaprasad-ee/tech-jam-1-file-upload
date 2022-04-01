const http = require('http');
const formidable = require('formidable');
const fs = require('fs');
const config = require('config');

const uploadFilePath = config.get('server.upload_file_path');

http.createServer(function (req, res) {
    const headers = {
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "OPTIONS, POST, GET",
        "Access-Control-Max-Age": 2592000, // 30 days
    };
    const form = new formidable.IncomingForm();
    form.on('progress', (bytesReceived, bytesExpected) => {
        const progress = (bytesReceived / bytesExpected * 100).toFixed(2)
        console.log(`Processing  ...  ${progress}% done`);
    });

    form.parse(req, function (err, fields, data) {
        var filepath = data.filetoupload.filepath;
        var newpath = `${uploadFilePath}/${data.filetoupload.originalFilename}`;
        fs.rename(filepath, newpath, function (err) {
            if (err) throw err;
            res.writeHead(200, headers);
            res.write('File uploaded!');
            res.end();
        });
    });

}).listen(config.get('server.port'));