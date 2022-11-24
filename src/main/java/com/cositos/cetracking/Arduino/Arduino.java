package com.cositos.cetracking.Arduino;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase encargada del la logica del arduino
 */

@Service
public class Arduino  {
    static private SerialPort porta;

    @Async
    public void Led(String hex) throws InterruptedException {
        porta = SerialPort.getCommPort("COM5");
        porta.openPort();
        Thread.sleep(1600);
        PrintWriter output = new PrintWriter(porta.getOutputStream());
        System.out.println(hex);
        output.print(hex);
        output.flush();
        porta.closePort();
        }

    }
