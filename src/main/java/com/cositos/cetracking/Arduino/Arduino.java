package com.cositos.cetracking.Arduino;

import com.cositos.cetracking.views.cetracker.CETrackerView;
import com.fazecast.jSerialComm.SerialPort;

import java.io.*;

/**
 * Clase encargada del la logica del arduino
 */
public class Arduino {

    public Arduino(String Message){
        var sp = SerialPort.getCommPort("COM5");
        Mensaje(Message, sp);
        sp.closePort();
    }
    public void Mensaje(String Message, SerialPort sp){
            PrintWriter output = new PrintWriter(sp.getOutputStream());

            System.out.println(Message);
            System.out.println("1234");
            output.print("1234");
            output.flush();
            sp.closePort();
        }

}
