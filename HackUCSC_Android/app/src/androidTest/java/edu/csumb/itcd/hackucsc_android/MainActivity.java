/**
 * Created by andrewtrapani on 1/10/15.
 * UCSC Hackathon 2015 entry
 */
package edu.csumb.itcd.hackucsc_android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.plantronics.device.*;
import com.plantronics.device.info.*;
import java.util.ArrayList;

public class MainActivity extends Activity implements PairingListener, ConnectionListener, InfoListener {
    private static final String TAG = "edu.csumb.itcd.hackucsc_android.MainActivity";

    private StateTracker    _state;
    private Context 		_context;
    private Device 			_device;

    //Example text fields could be added to the GUI
    /*
    private TextView		_wearingStateValueTextView;
    private TextView		_localProximityValueTextView;
    private TextView		_remoteProximityValueTextView;
    private TextView		_tapsValueTextView;
    private TextView		_pedometerValueTextView;
    private TextView		_freeFallValueTextView;
    */
    /**
     * Constructor for the main activity will define
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sets the main GUI based on main.xml
        //setContentView(R.layout.main);

        _context = this;

        //Initialize the GUI's text fields
        /* Examples of text fields could be found in GUI xml info
        _wearingStateValueTextView = ((TextView)findViewById(R.id.wearingStateValueTextView));
        _localProximityValueTextView = ((TextView)findViewById(R.id.localProximityValueTextView));
        _remoteProximityValueTextView = ((TextView)findViewById(R.id.remoteProximityValueTextView));
        _tapsValueTextView = ((TextView)findViewById(R.id.tapsValueTextView));
        _pedometerValueTextView = ((TextView)findViewById(R.id.pedometerValueTextView));
        _freeFallValueTextView = ((TextView)findViewById(R.id.freeFallValueTextView));
        */

        //Button example
        /*_calOrientationButton = ((Button)findViewById(R.id.calOrientationButton));
        _calOrientationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calOrientationButton();
            }
        });*/
        /* Make the function later
        private void calOrientationButton() {
		// "zero" orientation tracking at current orientation
		try {
			_device.setCalibration(null, Device.SERVICE_ORIENTATION_TRACKING);
		}
		catch (Exception e) {
			Log.e(TAG, "Exception calibrating orientation: " + e);
		}
	}
         */

        Device.initialize(this, new Device.InitializationCallback() {
            @Override
            public void onInitialized() {
                Log.i(TAG, "onInitialized()");
                Device.registerPairingListener((PairingListener)_context);

                tryConnecting();
            }

            @Override
            public void onFailure(int error) {
                Log.i(TAG, "onFailure()");

                if (error == Device.ERROR_PLTHUB_NOT_AVAILABLE) {
                    Log.i(TAG, "PLTHub was not found.");
                } else if (error == Device.ERROR_FAILED_GET_DEVICE_LIST) {
                    Log.i(TAG, "Failed to get device list.");
                }
            }
        });
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume()");
        super.onResume();

        _context = this;

        if (_device != null) {
            _device.onResume();
        }
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause()");
        super.onPause();

        _context = null;

        if (_device != null) {
            _device.onPause();
        }
    }

    private void tryConnecting() {
        ArrayList<Device> devices = Device.getPairedDevices();
        Log.i(TAG, "devices: " + devices);

        try {
            if (devices.size() > 0) {
                _device = devices.get(0);
                _device.registerConnectionListener((ConnectionListener)_context);
                _device.openConnection();
                _state = new StateTracker();
                _state.setConnected(true);
            }
            else {
                Log.i(TAG, "No paired PLT devices found!");
            }
        }
        catch (Exception e) {
            Log.e(TAG, "Exception opening connection: " + e);
        }
    }

    public void onDevicePaired(Device device) {
        Log.i(TAG, "onDevicePaired(): " + device);

        tryConnecting();
    }

    public void onDeviceUnpaired(Device device) {
        Log.i(TAG, "onDeviceUnpaired(): " + device);
    }

    public void onConnectionOpen(Device device) {
        Log.i(TAG, "onConnectionOpen()");

        // subscribe to all services
        try {
            //_device.subscribe(this, Device.SERVICE_ORIENTATION_TRACKING, Device.SUBSCRIPTION_MODE_ON_CHANGE, (short)0);
            _device.subscribe(this, Device.SERVICE_WEARING_STATE, Device.SUBSCRIPTION_MODE_ON_CHANGE, (short)0);
            _device.subscribe(this, Device.SERVICE_PROXIMITY, Device.SUBSCRIPTION_MODE_ON_CHANGE, (short)0);
            _device.subscribe(this, Device.SERVICE_TAPS, Device.SUBSCRIPTION_MODE_ON_CHANGE, (short)0);
            _device.subscribe(this, Device.SERVICE_PEDOMETER, Device.SUBSCRIPTION_MODE_ON_CHANGE, (short)0);
            _device.subscribe(this, Device.SERVICE_FREE_FALL, Device.SUBSCRIPTION_MODE_ON_CHANGE, (short)0);
            //_device.subscribe(this, Device.SERVICE_MAGNETOMETER_CAL_STATUS, Device.SUBSCRIPTION_MODE_ON_CHANGE, (short)0);
            //_device.subscribe(this, Device.SERVICE_GYROSCOPE_CAL_STATUS, Device.SUBSCRIPTION_MODE_ON_CHANGE, (short)0);
        }
        catch (Exception e) {
            Log.e(TAG, "Exception subscribing to services: " + e);
        }

    }

    public void onConnectionFailedToOpen(Device device, int error) {
        Log.i(TAG, "onConnectionFailedToOpen()");

        if (error == Device.ERROR_CONNECTION_TIMEOUT) {
            Log.i(TAG, "Open connection timed out.");
        }
    }

    public void onConnectionClosed(Device device) {
        Log.i(TAG, "onConnectionClosed()");

        _device = null;
        _state.setConnected(false);
        _state = null;
    }

    public void onSubscriptionChanged(Subscription oldSubscription, Subscription newSubscription) {
        Log.i(TAG, "onSubscriptionChanged(): oldSubscription=" + oldSubscription + ", newSubscription=" + newSubscription);
    }

    public void onInfoReceived(final Info info) {
        Log.i(TAG, "onInfoReceived(): " + info);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (info.getClass() == OrientationTrackingInfo.class) {
                    //Don't do anything for head tracking
                    /*OrientationTrackingInfo theInfo = (OrientationTrackingInfo)info;
                    EulerAngles eulerAngles = theInfo.getEulerAngles();

                    int heading = (int)Math.round(eulerAngles.getX());
                    int pitch = (int)Math.round(eulerAngles.getY());
                    int roll = (int)Math.round(eulerAngles.getZ());

                    _headingProgressBar.setProgress(heading + 180);
                    _pitchProgressBar.setProgress(pitch + 90);
                    _rollProgressBar.setProgress(roll + 180);

                    // heading uses the "right-hand" rule. http://en.wikipedia.org/wiki/Right-hand_rule
                    // most people find it more intuitive if the angle increases when rotated in the opposite direction
                    _headingValueTextView.setText(heading + "°");
                    _pitchValueTextView.setText(pitch + "°");
                    _rollValueTextView.setText(roll + "°");
                    */
                }
                else if (info.getClass() == WearingStateInfo.class) {
                    WearingStateInfo theInfo = (WearingStateInfo)info;
                    _state.setWearingState(theInfo);
                    //theInfo.getIsBeingWorn();
                    //Example of placing the wearing state into a text field on the GUI
                    //_wearingStateValueTextView.setText((theInfo.getIsBeingWorn() ? "yes" : "no"));
                }
                else if (info.getClass() == ProximityInfo.class) {
                    ProximityInfo theInfo = (ProximityInfo)info;
                    _state.setProximityInfo(theInfo);
                    //Example of placing state into test fields on the GUI.
                    //_localProximityValueTextView.setText(ProximityInfo.getStringForProximity(theInfo.getLocalProximity()));
                    //_remoteProximityValueTextView.setText(ProximityInfo.getStringForProximity(theInfo.getRemoteProximity()));
                }
                else if (info.getClass() == TapsInfo.class) {
                    TapsInfo theInfo = (TapsInfo)info;
                    _state.setTapInfo(theInfo);
                    //int count = theInfo.getCount();
                    //String tapss = (count > 1 ? " taps" : " tap");
                    //_tapsValueTextView.setText((count == 0 ? "-" : count + tapss + " in " + TapsInfo.getStringForTapDirection(theInfo.getDirection())));
                }
                else if (info.getClass() == PedometerInfo.class) {
                    PedometerInfo theInfo = (PedometerInfo)info;
                    _state.setPedometerInfo(theInfo);
                    //_pedometerValueTextView.setText(theInfo.getSteps() + " steps");
                }
                else if (info.getClass() == FreeFallInfo.class) {
                    FreeFallInfo theInfo = (FreeFallInfo)info;
                    _state.setFreeFall(theInfo);
                    //_freeFallValueTextView.setText((theInfo.getIsInFreeFall() ? "yes" : "no"));
                }
                else if (info.getClass() == MagnetometerCalInfo.class) {
                    MagnetometerCalInfo theInfo = (MagnetometerCalInfo)info;
                    //_magnetometerCalValueTextView.setText((theInfo.getIsCalibrated() ? "yes" : "no"));
                }
                else if (info.getClass() == GyroscopeCalInfo.class) {
                    GyroscopeCalInfo theInfo = (GyroscopeCalInfo)info;
                    //_gyroscopeCalValueTextView.setText((theInfo.getIsCalibrated() ? "yes" : "no"));
                }
            }
        });
    }

}
