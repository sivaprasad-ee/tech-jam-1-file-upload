package com.everest.fileupload;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleSocketServer {

    public static void main( String[] args ) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(8081)) {
            while (true) {
                try (Socket client = serverSocket.accept()) {
                    handleClient(client);
                }
            }
        }
    }

    private static void handleClient(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String contentLengthHeader = "Content-Length: ";
        int contentLength = 0;
        String line;
        while ((line = in.readLine()) != null && (line.length() != 0)) {
            System.out.println(line);

            if (line.contains(contentLengthHeader)) {
                contentLength = Integer.parseInt(line.substring(line.indexOf(contentLengthHeader) + contentLengthHeader.length()));
            }
        }
        System.out.println("HTTP BODY");
        System.out.println("=============================");
        String postData = "";
        // read the post data
        if (contentLength > 0) {
            char[] charArray = new char[contentLength];
            in.read(charArray, 0, contentLength);
            postData = new String(charArray);
        }
        System.out.println(postData);

        byte[] response = "<h1>Success</h1>".getBytes();
        sendResponse(client, "200 OK", "text/html", response);
    }

    private static void sendResponse(Socket client, String status, String contentType, byte[] content) throws IOException {
        OutputStream clientOutput = client.getOutputStream();
        clientOutput.write(("HTTP/1.1 \r\n" + status).getBytes());
        clientOutput.write(("ContentType: " + contentType + "\r\n").getBytes());
        clientOutput.write("\r\n".getBytes());
        clientOutput.write(content);
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
        client.close();
    }
}