package com.cositos.cetracking.Arduino;

import com.fazecast.jSerialComm.SerialPort;

import java.io.PrintWriter;

/**
 * Clase encargada del la logica del arduino
 */
public class Arduino {

    public Arduino(String code) {
        Arduino(code);
    }

    private static void Arduino(String Message){
        var sp = SerialPort.getCommPort("COM5");

        PrintWriter output = new PrintWriter(sp.getOutputStream());

        System.out.println(Message);
        System.out.println("1234");
        output.print("1234");
        output.flush();
        sp.closePort();
    }

}
