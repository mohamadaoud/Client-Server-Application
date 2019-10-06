
import java.io.*;
import java.net.Socket;
import java.util.logging.*;

/*
The Application class with the run method that create a new thread for each new connected Client.
Input string from Client will be calculated on the Server side and the result 
will be returned back to Client.
*/

public class Application extends Thread {
    private Socket socket = null;
    
    public Application (Socket socket) {
        this.socket = socket;
    }

    //Reading from and Writing to a Socket
    @Override
    public void run() {
        boolean clientRun = true;
        PrintWriter outWriter = null;
        BufferedReader inputReader = null;
        try {
            while(clientRun) {
                outWriter = new PrintWriter(socket.getOutputStream(), true);
                //Enter only two valid numbers with an operation in between
                outWriter.println("Write two numbers to calculate ex. 2+2 / or quit to exit");
                inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inputData = inputReader.readLine();
                //Or you can simply quit the calculation by entering a "quit" word
                if (inputData.equalsIgnoreCase("quit")) {
                    clientRun = false;
                //Once the user has entered a value other than "quit"
                } else {
                    String result = splitData(inputData);
                    outWriter.println(inputData+" = "+result);
                }
            }
            //Throws an exception once an invalid equation has been entered
            //And connection though will be disconnected
        } catch (IOException | NumberFormatException e) {
            outWriter.println("Error: "+e.getMessage()+"\n\rConnection will be interrupted.");
        } finally {
            try {
                //Close the socket's input stream, socket's output stream and the socket connection
                inputReader.close();
                outWriter.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Split the equation that has been entered (input by user), and return the result
    private String splitData(String inputData) {
        String[] data;
        for (int i = 0; i < inputData.length(); i++) {
            char character = inputData.charAt(i);
            switch (Character.toString(character)) {
            case "+":
                data = inputData.split("\\+");
                return Integer.toString(Math.addExact(Integer.parseInt(data[0]), 
                          Integer.parseInt(data[1])));
            case "-":
                data = inputData.split("\\-");
                return Integer.toString(Math.subtractExact(Integer.parseInt(data[0]), 
                          Integer.parseInt(data[1])));
            case "*":
                data = inputData.split("\\*");
                return Integer.toString(Math.multiplyExact(Integer.parseInt(data[0]), 
                          Integer.parseInt(data[1])));
            case "/":
                data = inputData.split("\\/");
                return Integer.toString(Math.floorDiv(Integer.parseInt(data[0]), 
                          Integer.parseInt(data[1])));   
            default:
                break;
            } 
        }
        //Return only if the input were not numbers, and print this message to the user
        return "Unknown value. Input string may have wrong format.";
    }
}
