package com.example.android.rowanparkingpass.Activities.ListViewActivities;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.android.rowanparkingpass.Activities.CreateDriverActivity;
import com.example.android.rowanparkingpass.Activities.PassActivity;
import com.example.android.rowanparkingpass.Activities.ListViewActivities.ArrayAdapter.DriverArrayAdapter;
import com.example.android.rowanparkingpass.Networking.SendInfo.SendInfoDriver;
import com.example.android.rowanparkingpass.R;
import com.example.android.rowanparkingpass.utilities.SavedData.SaveData;
import com.example.android.rowanparkingpass.personinfo.Driver;
import com.example.android.rowanparkingpass.utilities.Utilities;
import com.example.android.rowanparkingpass.utilities.database.DatabaseHandlerDrivers;
import com.example.android.rowanparkingpass.utilities.database.DatabaseHandlerPasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DriversActivity extends ListActivity implements SearchView.OnQueryTextListener {


    DatabaseHandlerDrivers db;
    Context context;
    SearchView searchView;
    MenuItem searchMenuItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        context = getApplicationContext();
        build();
        loaded();
    }

    public void build() {

        db = new DatabaseHandlerDrivers(this.getApplicationContext());
        ArrayList<Driver> listOfDrivers = db.getDrivers();

        buildDriversList(listOfDrivers);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        if (currentMode.equals(mode.DRIVERS_LIST.name())) {
            inflator.inflate(R.menu.menu_search_home, menu);
        } else {
            inflator.inflate(R.menu.menu_vehicles_drivers_page, menu);
        }
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(this);
        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                Utilities.hideSoftKeyboard(DriversActivity.this);
                searchView.setQuery("", false);
            }
        });
        return true;
    }

    private void buildDriversList(List<Driver> drivers) {
        listView = (ListView) findViewById(R.id.listView);
        final ListView tempListView = listView;
        makeAdapter(drivers);

        // checks what item in the listview was clicked. 
        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                //Close search view if its visible
                if (searchView.isShown()) {
                    searchMenuItem.collapseActionView();
                    searchView.setQuery("", false);
                }
                Intent intent = new Intent(DriversActivity.this, CreateDriverActivity.class);

                if (position == 0 && listView.getItemAtPosition(0) == null) {
                    intent.putExtra(MODE, mode.CREATE_DRIVER.name());
                    intent.putExtra("Old", currentMode);
                } else {
                    if (currentMode.equals(mode.DRIVERS_LIST.name())) {
                        intent.putExtra(MODE, mode.UPDATE_DRIVER.name());
                    } else if (currentMode.equals(mode.UPDATE_PASS_DRIVERS.name())) {
                        intent = new Intent(DriversActivity.this, PassActivity.class);
                        intent.putExtra("Vehicle", pastIntent.getSerializableExtra("Vehicle"));
                        intent.putExtra(MODE, mode.CREATE_PASS.name());
                    } else {
                        intent = new Intent(DriversActivity.this, VehiclesActivity.class);
                        intent.putExtra(MODE, mode.VEHICLES.name());
                        leftToRightTransition();
                    }
                    intent.putExtra("Driver", (Serializable) adapter.getItem(position));
                }
                startActivity(intent);
            }
        };
        // checks what item in the listview was long clicked
        AdapterView.OnItemLongClickListener mMessageLongClickedHandler = new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView parent, View v, final int position, long id) {
                //Close search view if its visible
                if (searchView.isShown()) {
                    searchMenuItem.collapseActionView();
                    searchView.setQuery("", false);
                }
                if (position != 0) {
                    final Driver driver = (Driver) tempListView.getItemAtPosition(position);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DriversActivity.this);
                    alertDialog.setTitle("Delete Driver?");
                    alertDialog.setMessage(driver.getName());
                    alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (SaveData.getSync()) {
                                SendInfoDriver sendInfoDriver = new SendInfoDriver();
                                sendInfoDriver.deleteDriver(String.valueOf(driver.getDriverId()));
                            }
                            db.deleteDriver(String.valueOf(driver.getDriverId()));
                            new DatabaseHandlerPasses(context).deleteRequestDriverID(String.valueOf(driver.getDriverId()));
                            makeAdapter(db.getDrivers());
                        }
                    });
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                        }
                    });
                    alertDialog.show();
                }
                return true;
            }
        };
        listView.setOnItemClickListener(mMessageClickedHandler);
        listView.setOnItemLongClickListener(mMessageLongClickedHandler);
    }

    private void makeAdapter(List<Driver> d) {
        adapter = new DriverArrayAdapter(d, this);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                searchView.requestFocus();
                break;
            case R.id.action_home:
                Intent intent = new Intent(this, PassesActivity.class);
                intent.putExtra(MODE, mode.HOME_PAGE.name());
                startActivity(intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return true;
    }

}

