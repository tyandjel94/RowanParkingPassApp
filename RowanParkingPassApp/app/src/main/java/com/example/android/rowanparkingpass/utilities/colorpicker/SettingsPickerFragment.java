package com.example.android.rowanparkingpass.utilities.colorpicker;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.android.rowanparkingpass.R;

public class SettingsPickerFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add  preferences.
        addPreferencesFromResource(R.xml.pref_calendarcolor);
    }


}
