
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
        boolean com = true;
        PrintWriter outWriter = null;
        BufferedReader inputReader = null;
        while (com) {
            try {
                outWriter = new PrintWriter(socket.getOutputStream(), true);
                outWriter.println("Write two numbers to calculate ex. 2+2 / or quit to exit");
                inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inputData = inputReader.readLine();
                if (inputData.matches("quit")) {
                        com = false;
                } else {
                    String result = splitData(inputData);
                    outWriter.println(inputData+" = "+result);
                }
            } catch (IOException e) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        try {
            inputReader.close();
            outWriter.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String splitData(String inputData) {
        String[] data;
        for (int i = 0; i < inputData.length(); i++) {
            char character = inputData.charAt(i);
            switch (Character.toString(character)) {
            case "+":
                data = inputData.split("\\+");
                return Integer.toString(addition(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
            case "-":
                data = inputData.split("\\-");
                return Integer.toString(subtraction(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
            case "*":
                data = inputData.split("\\*");
                return Integer.toString(multiplication(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
            case "/":
                data = inputData.split("\\/");
                return Integer.toString(division(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
            default:
                break;
            } 
        }
        return "";
    }
    
    private int addition(int val1, int val2) {
        return val1 + val2;
    }

    private int subtraction(int val1, int val2) {
        return val1 - val2;
    }

    private int multiplication(int val1, int val2) {
        return val1 * val2;
    }

    private int division(int val1, int val2) {
        return val1 / val2;
    }
}
