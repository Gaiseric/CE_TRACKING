package com.cositos.cetracking.Arduino;

import com.fazecast.jSerialComm.SerialPort;

import java.io.PrintWriter;

/**
 * Clase encargada del la logica del arduino
 */
public class Arduino {

    private SerialPort sp;

    public Arduino(String code) {
        Arduino(code);
    }

    /**
     * It takes a string, and sends it to the Arduino
     * 
     * @param Message The message to be sent to the Arduino
     */
    private static void Arduino(String Message){
        SerialPort sp = SerialPort.getCommPort("COM5");

        PrintWriter output = new PrintWriter(sp.getOutputStream());

        System.out.println(Message);
        System.out.println("1234");
        output.print("1234");
        output.flush();
        sp.closePort();
    }

}
