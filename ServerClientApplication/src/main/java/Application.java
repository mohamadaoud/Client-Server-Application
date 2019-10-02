
import java.io.*;
import java.net.Socket;
import java.util.logging.*;

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
    
    public Application (Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        boolean clientRun = true;
        PrintWriter outWriter = null;
        BufferedReader inputReader = null;
        try {
            while(clientRun) {
                outWriter = new PrintWriter(socket.getOutputStream(), true);
                outWriter.println("Write two numbers to calculate ex. 2+2 / or quit to exit");
                inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inputData = inputReader.readLine();
                if (inputData.equalsIgnoreCase("quit")) {
                    clientRun = false;
                } else {
                    String result = splitData(inputData);
                    outWriter.println(inputData+" = "+result);
                }
            }
        } catch (IOException e) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                inputReader.close();
                outWriter.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

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
        return "";
    }
}
