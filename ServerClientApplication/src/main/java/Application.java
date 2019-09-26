
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
                outWriter.println("Hello Client, Write two numbers with +-*/ between. ex. 2 + 2");
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
        String[] splitted = inputData.split(" ");
        int val1 = Integer.parseInt(splitted[0]);
        int val2 = Integer.parseInt(splitted[2]);
        switch (splitted[1]) {
            case "+":
                return Integer.toString(addition(val1, val2));
            case "-":
                return Integer.toString(subtraction(val1, val2));
            case "*":
                return Integer.toString(multiplication(val1, val2));
            case "/":
                return Integer.toString(division(val1, val2));
            default:
                break;
        }
        return "Unknown value";
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
