package com.cositos.cetracking.views.cetracker;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;



//import StartWindow.Reproductor.Reproducir_Musica;


/**
 * The SendThread class is a Spring service that has an asynchronous method called SendAsync. 
 * 
 * The SendAsync method sleeps for one second and then returns a ListenableFuture object. 
 * 
 * The ListenableFuture object is a Spring object that represents the result of an asynchronous method.
 * 
 * 
 * The ListenableFuture object is returned to the caller of the SendAsync method. 
 * 
 * The caller can then use the ListenableFuture object to get the result of the asynchronous method. 
 * 
 * The ListenableFuture object can also be used to register a callback method that will be called when
 * the asynchronous method completes. 
 * 
 * The ListenableFuture object is returned to the caller of the SendAsync method. 
 * 
 * The caller can then use the ListenableFuture object to get the result of the asynchronous method. 
 * 
 * The ListenableFuture object can also be used to register a callback method that will be called when
 * the asynchronous method completes.
 */
@Service
public class SendThread {

    /**
     * It returns a ListenableFuture that will be completed with a Void value after a 1 second delay
     * 
     * @return A ListenableFuture.
     */
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
