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
    const stream = fs.createWriteStream(`${uploadFilePath}/file`);
    // With the open - event, data will start being written
    // from the request to the stream's destination path
    stream.on('open', () => {
        console.log('Stream open ...  0.00%');
        req.pipe(stream);
    });

    // Drain is fired whenever a data chunk is written.
    // When that happens, print how much data has been written yet.
    stream.on('drain', () => {
        const written = parseInt(stream.bytesWritten);
        const total = parseInt(req.headers['content-length']);
        const pWritten = ((written / total) * 100).toFixed(2);
        console.log(`Processing  ...  ${pWritten}% done`);
    });

    // When the stream is finished, print a final message
    // Also, resolve the location of the file to calling function
    stream.on('close', () => {
        console.log('Processing  ...  100%');
        res.writeHead(200, headers);
        res.write('File uploaded!');
        res.end();
    });
    // If something goes wrong, reject the primise
    stream.on('error', err => {
        console.error(err);
        reject(err);
    });
}).listen(config.get('server.port'));