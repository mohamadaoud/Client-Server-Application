
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Patrik.Karlsson
 */

public class Server {
    
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(35000)) {
            boolean runServer = true;
            while (runServer) {
                System.out.println("Connecton opened. (" + new Date() + ")");
                Socket socket = serverSocket.accept();
                Application application = new Application(socket);
                application.start();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
