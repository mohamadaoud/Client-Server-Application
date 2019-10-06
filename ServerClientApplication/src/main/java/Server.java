
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/*
This is the Main class for Server. A loop that wait for serverSocket.accept() ti find a new Client.
The Client connect on port 35000.
*/

public class Server {
    
    public static void main(String[] args) throws Exception {
        //Creates a Server Socket that listens on port 35000
        try (ServerSocket serverSocket = new ServerSocket(35000)) {
            boolean runServer = true;
            //Listening for incoming Connections
            while (runServer) {
                System.out.println("Connecton opened. (" + new Date() + ")");
                //The client should accept in order to communicate with the server)
                Socket socket = serverSocket.accept();
                Application application = new Application(socket);
                application.start();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
