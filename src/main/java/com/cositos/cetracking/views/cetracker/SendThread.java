package com.cositos.cetracking.views.cetracker;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;


//import StartWindow.Reproductor.Reproducir_Musica;


@Service
public class SendThread {

    @Async
    public ListenableFuture<Void> SendAsync()  {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            return AsyncResult.forExecutionException(new RuntimeException("Error"));
        }

        return AsyncResult.forValue(null);
        
    }
    
}
