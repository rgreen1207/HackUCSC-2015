package com.plantronics.device.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.twilio.client.Device;
import com.twilio.client.Twilio;
import com.twilio.client.impl.net.HttpHelper;

public class Testing extends Activity implements Twilio.InitListener{
    public Testing(Context context)
    {
        Twilio.initialize(context, this /* Twilio.InitListener */);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_test_page);
    }

    private Device device;

    @Override /* Twilio.InitListener method */
    public void onInitialized()
    {
        android.util.Log.d(TAG, "Twilio SDK is ready");
        try {
            String capabilityToken = HttpHelper.httpGet("http://companyfoo.com/token");
            device = Twilio.createDevice(capabilityToken, null /* DeviceListener */);
        } catch (Exception e) {
            Log.e(TAG, "Failed to obtain capability token: " + e.getLocalizedMessage());
        }
    }
}