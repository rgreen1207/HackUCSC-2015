package edu.csumb.itcd.hackucsc_android;

import com.plantronics.device.info.FreeFallInfo;
import java.util.ArrayList;

/**
 * Created by andrewtrapani on 1/10/15.
 */
public class FreeFallTracker implements Runnable {
    private long timestampStarted;
    private long timestampEnded;
    private Boolean isFalling;
    //If the free fall is below this time, ignore it by default
    private long minimumFreeFallTime; //milliseconds
    //If the free fall is above this time, automatically ask the user if they need help
    // Hopefully they're sky diving...
    private long maximumFreeFallTime; //milliseconds

    private ArrayList<Subscriber> subscribers;
    public interface Subscriber {
        public void receiveData(boolean fell);
    }
    private void notifySubscribers(boolean fell) {
        for (Subscriber subscriber : subscribers) {
            subscriber.receiveData(fell);
        }
    }


    public FreeFallTracker() {
        timestampStarted = 0;
        timestampEnded = 0;
        isFalling = false;
        minimumFreeFallTime = 1000;
        maximumFreeFallTime = 4000;
        subscribers = new ArrayList<Subscriber>();
    }

    public void updateFreeFall(FreeFallInfo info) {
        boolean inFreeFall = info.getIsInFreeFall();
        isFalling = inFreeFall;
        isFalling.notifyAll();

    }

    @Override
    public void run() {
        while (Thread.currentThread().isInterrupted() == false) {
            try {
                //First just wait for a free fall to start
                isFalling.wait();
                timestampStarted = System.currentTimeMillis();
                //Wait until free fall has ended or a time limit is reached
                isFalling.wait(maximumFreeFallTime);
                timestampEnded = System.currentTimeMillis();
                if (timestampEnded - timestampStarted >= minimumFreeFallTime) {
                    notifySubscribers(true);
                } else {
                    notifySubscribers(false);
                }
            } catch (InterruptedException ex) {
                //Make sure to end this thread
                Thread.currentThread().interrupt();
            }
        }
    }


}
