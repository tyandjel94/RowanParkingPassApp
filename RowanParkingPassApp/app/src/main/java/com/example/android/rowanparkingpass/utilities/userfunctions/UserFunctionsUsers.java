package com.example.android.rowanparkingpass.utilities.userfunctions;

import android.content.Context;

import com.example.android.rowanparkingpass.utilities.JSONParser;
import com.example.android.rowanparkingpass.utilities.database.DatabaseHandlerUser;

import org.json.JSONObject;

import java.util.HashMap;

public class UserFunctionsUsers extends UserFunctionsBase {

    //URL of the PHP API
    private static final String LOGIN_URL = IP_ADDRESS_URL + "/check_cas_auth.php";

//    private static final String LOGIN_TAG = "login";

    private static final String EMAIL_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    // constructor
    public UserFunctionsUsers() {
        super();
    }

    /**
     * Function to log user in
     *
     * @param email    the email to log into
     * @param password the password assoaciated with the user's email
     * @return JSONObject of whether it was a sucessful login
     */
    public JSONObject loginUser(String email, String password) {
        // Building Parameters
        HashMap<String, String> params = new HashMap<>();
        params.put(EMAIL_KEY, email);
        params.put(PASSWORD_KEY, password);
        // Return JsonObject
        return jsonParser.makeHttpRequest(LOGIN_URL, JSONParser.POST, params);
    }

    /**
     * Function to check if admin
     */
    public boolean isAdmin(Context context) {
        //TODO: Check if current user is admin
        return true;
    }

}
