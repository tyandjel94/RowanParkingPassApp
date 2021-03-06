package com.example.android.rowanparkingpass.Networking.SendInfo;

import com.example.android.rowanparkingpass.utilities.JSONParser;

public abstract class SendInfoBase {

    JSONParser jsonParser;

    //URL of the PHP API
    public static final String IP_ADDRESS_URL =  "http://shh.mordor.us/";//*/ "http://192.168.1.10";  // computer ip address
    static final String DATABASE_NAME = "Parking/";

    static final String TAG_KEY = "tag";
    static final String KILL_KEY = "kill";

    // constructor
    public SendInfoBase() {
        jsonParser = new JSONParser();
    }
}
