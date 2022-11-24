package com.cositos.cetracking.Arduino;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.PrintWriter;

/**
 * Clase encargada del la logica del arduino
 */

@Service
public class Arduino  {
    static private SerialPort porta;

    /**
     * It opens the serial port, sends a hexadecimal string to the Arduino, waits 10 seconds, and then
     * closes the serial port
     * 
     * @param hex the hexadecimal code that will be sent to the arduino
     * @return A ListenableFuture.
     */
    @Async
    public ListenableFuture<Void> Led(String hex) {
        try {
            porta = SerialPort.getCommPort("COM4");
            porta.openPort();
            Thread.sleep(1600);
            PrintWriter output = new PrintWriter(porta.getOutputStream());
            System.out.println(hex);
            output.print(hex);
            output.flush();
            porta.closePort();
            Thread.sleep(10000);
            porta.openPort();
            porta.closePort();
        } catch (InterruptedException e) {
            return AsyncResult.forExecutionException(new RuntimeException("Error"));
        }

        return AsyncResult.forValue(null);
    }

}
