
import java.io.*;
import java.net.Socket;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Patrik.Karlsson
 */
public class Application extends Thread {
    private Socket socket = null;
    static final File WEB_ROOT = new File(".");
    static final String DEFAULT_FILE = "index.html";
   
    
    public Application (Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // we manage our particular client connection
        BufferedReader inputReader = null;
        PrintWriter outPutWriter = null;
        BufferedOutputStream outputStream = null;
        String fileRequested = null;

        try {
                // we read characters from the client via input stream on the socket
                inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // we get character output stream to client (for headers)
                outPutWriter = new PrintWriter(socket.getOutputStream());
                // get binary output stream to client (for requested data)
                outputStream = new BufferedOutputStream(socket.getOutputStream());

                // get first line of the request from the client
                String input = inputReader.readLine();
                // we parse the request with a string tokenizer
                StringTokenizer parse = new StringTokenizer(input);
                String method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client
                // we get file requested
                fileRequested = parse.nextToken().toLowerCase();

                // we support only GET and HEAD methods, we check
                // GET or HEAD method
                if (fileRequested.endsWith("/")) {
                        fileRequested += DEFAULT_FILE;
                }

                File file = new File(WEB_ROOT, fileRequested);
                int fileLength = (int) file.length();
                String content = getContentType(fileRequested);

                if (method.equals("GET")) { // GET method so we return content
                        byte[] fileData = readFileData(file, fileLength);

                        // send HTTP Headers
                        outPutWriter.println("HTTP/1.1 200 OK");
                        outPutWriter.println("Server: Java HTTP Server from SSaurel : 1.0");
                        outPutWriter.println("Date: " + new Date());
                        outPutWriter.println("Content-type: " + content);
                        outPutWriter.println("Content-length: " + fileLength);
                        outPutWriter.println(); // blank line between headers and content, very important !
                        outPutWriter.println("<body>");
                        outPutWriter.println("<h1>Group chat</h1>");
                        outPutWriter.println("<p>Type message</p>");
                        outPutWriter.println("<textarea name=\"message\" style=\"width:400px; height:300px;\"></textarea>");
                        outPutWriter.println("<button value=\"Refresh Page\" onClick=\"window.location.reload();\">Send</button>");
                        outPutWriter.println("</body>");
                        outPutWriter.flush(); // flush character output stream buffer

                        outputStream.write(fileData, 0, fileLength);
                        outputStream.flush();
                }
        } catch (IOException ioe) {
                System.err.println("Server error : " + ioe);
        } finally {
            try {
                    inputReader.close();
                    outPutWriter.close();
                    outputStream.close();
                    socket.close(); // we close socket connection
            } catch (IOException e) {
                    System.err.println("Error closing stream : " + e.getMessage());
            }
        }
    }

    private byte[] readFileData(File file, int fileLength) throws IOException {
            FileInputStream fileIn = null;
            byte[] fileData = new byte[fileLength];

            try {
                    fileIn = new FileInputStream(file);
                    fileIn.read(fileData);
            } finally {
                    if (fileIn != null) 
                            fileIn.close();
            }

            return fileData;
    }

    // return supported MIME Types
    private String getContentType(String fileRequested) {
            if (fileRequested.endsWith(".htm")  ||  fileRequested.endsWith(".html"))
                    return "text/html";
            else
                    return "text/plain";
    }
}
