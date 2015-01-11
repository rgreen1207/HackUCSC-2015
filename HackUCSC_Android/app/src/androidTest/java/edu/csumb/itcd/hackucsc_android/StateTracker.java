package edu.csumb.itcd.hackucsc_android;

import com.plantronics.device.info.*;
import java.util.ArrayList;

/**
 * Created by andrewtrapani on 1/10/15.
 */
public class StateTracker implements Runnable {

    private boolean connected;
    private WearingStateInfo wearingState = null;

    //Keep the current prox info and keep an array of distances to determines "Far" vs "Close" states
    private ProximityInfo proximityInfo = null;
    private ArrayList<Integer> distances = null;
    public enum Proximity {
        UNKNOWN, FAR, CLOSE;
    }
    private Proximity proximity;

    //Keep a list of taps in case we want to track many taps
    private ArrayList<TapsInfo> tapInfoList = null;
    //Don't keep information related to taps past this many seconds
    private long tapInfoTimeoutSec = 10;

    //Pedometer information simply could be steps
    private int steps;

    //Free Fall data
    private FreeFallInfo freeFalling = null;
    private FreeFallTracker freeFallTracker;
    private Thread freeFallThread;


    public StateTracker() {
        connected = false;
        tapInfoList = new ArrayList<TapsInfo>();
        distances = new ArrayList<Integer>();
        steps = 0;
        proximity = Proximity.UNKNOWN;
        freeFallTracker = new FreeFallTracker();
        freeFallThread = new Thread(freeFallTracker);
    }

    public void setConnected(boolean isConnected) {
        if (connected != isConnected) {
            //Do anything related to changing connection state
            if (isConnected = false) {
                freeFallThread.interrupt();
                if (proximity == Proximity.FAR) {
                    //Maybe it went out of range, possibly try to shout out of the phone
                    wentOutOfRange();
                }

                //clear out some state info
                wearingState = null;
                proximityInfo = null;
                proximity = Proximity.UNKNOWN;
                freeFalling = null;
                distances.clear();
                tapInfoList.clear();
                steps = 0;
            } else {
                freeFallThread.run();
            }
        }
        connected = isConnected;
    }

    /**
     * If it was disconnected and it was at a "Far" range, then perhaps the user walked away too far
     * Have the phone say something or do something, if possible
     */
    private void wentOutOfRange() {
        //Nothing here yet
    }

    public void setWearingState(WearingStateInfo state) {
        if (wearingState == null) {
            //Do something if this is the first time this was set
        }
        wearingState = state;
    }

    public void setProximityInfo(ProximityInfo info) {
        if (proximityInfo == null) {
            //Do something if this is the first time this was set
        }

        int currentDistance = info.getLocalProximity();
        if (currentDistance > distances.get(distances.size())) {
            //Check if it's "Far" and wasn't Far before
            //If it wasn't Far before, notify the user with a polite message that they forgot something
        } else if (currentDistance < distances.get(distances.size())) {
            //Check if it's "Close" and was Far before
            //Silently change the state back to Close if it was Far
        }

        distances.add(currentDistance);
        proximityInfo = info;
    }

    public void setTapInfo (TapsInfo info) {
        if (tapInfoList.size() == 0) {
            //Notify subscribers that there may have been an interesting initial tap event
        } else {
            //Clear out any out dated taps
            long currentTime = System.currentTimeMillis();
            for (TapsInfo prevTap : tapInfoList) {
                if (prevTap.getTimestamp().getTime() < currentTime - tapInfoTimeoutSec) {
                    tapInfoList.remove(prevTap);
                }
            }
        }



        tapInfoList.add(info);
    }

    /**
     * Update the number of steps
     * Possibly make another thread to post periodic pedometer data
     * This thread could hold an array list of pedometer info to keep time->steps to help paint a better picture
     * @param info
     */
    public void setPedometerInfo (PedometerInfo info) {
        steps = info.getSteps();

    }

    /**
     * Update the free fall state and notify subscribers
     * @param info
     */
    public void setFreeFall (FreeFallInfo info) {
        freeFalling = info;
    }

    @Override
    public void run() {

    }
}