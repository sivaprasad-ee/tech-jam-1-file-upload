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
    const multipartParser = new formidable.MultipartParser();

    multipartParser.on('data', ({ name, buffer, start, end }) => {
        console.log(`${name}:`);
        if (name === 'end') {
            res.writeHead(200, headers);
            res.write('File uploaded!');
            res.end();
        }
        if (buffer && start && end) {
            console.log(String(buffer.slice(start, end)));
        }
    });
    multipartParser.on('error', console.error);

    const boundary = req.headers['content-type'].split('boundary=')[1];
    multipartParser.initWithBoundary(boundary);
    req.pipe(multipartParser);

}).listen(config.get('server.port'));