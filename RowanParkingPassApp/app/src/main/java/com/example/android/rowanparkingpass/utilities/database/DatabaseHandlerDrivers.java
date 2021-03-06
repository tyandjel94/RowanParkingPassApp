package com.example.android.rowanparkingpass.utilities.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.rowanparkingpass.personinfo.Driver;
import com.example.android.rowanparkingpass.utilities.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DatabaseHandlerDrivers extends DatabaseHandlerBase {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DriverContract.DriverEntry.TABLE_NAME + " (" +
                    DriverContract.DriverEntry.COLUMN_DRIVER_ID + DriverContract.INTEGER_TYPE + " PRIMARY KEY AUTO_INCREMENT," +
                    DriverContract.DriverEntry.COLUMN_DRIVER_PIC + DriverContract.BLOB + DriverContract.COMMA_SEP +
                    DriverContract.DriverEntry.COLUMN_FULL_NAME + DriverContract.TEXT_TYPE + DriverContract.COMMA_SEP +
                    DriverContract.DriverEntry.COLUMN_STREET + DriverContract.TEXT_TYPE + DriverContract.COMMA_SEP +
                    DriverContract.DriverEntry.COLUMN_CITY + DriverContract.TEXT_TYPE + DriverContract.COMMA_SEP +
                    DriverContract.DriverEntry.COLUMN_STATE + DriverContract.TEXT_TYPE + DriverContract.COMMA_SEP +
                    DriverContract.DriverEntry.COLUMN_ZIP + DriverContract.TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DriverContract.DriverEntry.TABLE_NAME;
    private static final String SQL_SELECT_ALL_ENTRIES =
            "SELECT * FROM " + DriverContract.DriverEntry.TABLE_NAME;

    public DatabaseHandlerDrivers(Context context) {
        super(context);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TAG", "DRIVERS ON CREATE");
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(SQL_DELETE_ENTRIES);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing Driver details in database
     */
    public void addDriver(int driverId, byte[] image, String fullName, String street, String city, String state, String zip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DriverContract.DriverEntry.COLUMN_DRIVER_ID, driverId);
        values.put(DriverContract.DriverEntry.COLUMN_DRIVER_PIC, image); // Image
        values.put(DriverContract.DriverEntry.COLUMN_FULL_NAME, fullName); // Full Name
        values.put(DriverContract.DriverEntry.COLUMN_STREET, street); // Street
        values.put(DriverContract.DriverEntry.COLUMN_CITY, city); // City
        values.put(DriverContract.DriverEntry.COLUMN_STATE, state); // State
        values.put(DriverContract.DriverEntry.COLUMN_ZIP, zip); // Zip
        // Inserting Row
        db.insert(DriverContract.DriverEntry.TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Storing Driver details in database
     */
    public int addDriver(byte[] image, String fullName, String street, String city, String state, String zip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DriverContract.DriverEntry.COLUMN_DRIVER_PIC, image); // Image
        values.put(DriverContract.DriverEntry.COLUMN_FULL_NAME, fullName); // Full Name
        values.put(DriverContract.DriverEntry.COLUMN_STREET, street); // Street
        values.put(DriverContract.DriverEntry.COLUMN_CITY, city); // City
        values.put(DriverContract.DriverEntry.COLUMN_STATE, state); // State
        values.put(DriverContract.DriverEntry.COLUMN_ZIP, zip); // Zip
        // Inserting Row
        int id = (int) db.insert(DriverContract.DriverEntry.TABLE_NAME, null, values);
        db.close(); // Closing database connection
        return id;
    }

    /**
     * Update visitor details in database
     */
    public void updateDriver(String driverId, byte[] image, String fullName, String street, String city, String state, String zip) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DriverContract.DriverEntry.COLUMN_DRIVER_PIC, image); // Image
        values.put(DriverContract.DriverEntry.COLUMN_FULL_NAME, fullName); // Full Name
        values.put(DriverContract.DriverEntry.COLUMN_STREET, street); // Street
        values.put(DriverContract.DriverEntry.COLUMN_CITY, city); // City
        values.put(DriverContract.DriverEntry.COLUMN_STATE, state); // State
        values.put(DriverContract.DriverEntry.COLUMN_ZIP, zip); // Zip
        // Update Row
        db.update(DriverContract.DriverEntry.TABLE_NAME, values, DriverContract.DriverEntry.COLUMN_DRIVER_ID + "=" + driverId, null);
    }

    public void updateDriverWithID(int oldID, int newID) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DriverContract.DriverEntry.COLUMN_DRIVER_ID, newID);
        // Update Row
        db.update(DriverContract.DriverEntry.TABLE_NAME, values, DriverContract.DriverEntry.COLUMN_DRIVER_ID + "=" + oldID, null);
    }

    /**
     * Update visitor details in database
     */
    public void deleteDriver(String driverId) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Delete Row
        db.delete(DriverContract.DriverEntry.TABLE_NAME, DriverContract.DriverEntry.COLUMN_DRIVER_ID.toString() + "=" + driverId, null);
    }

    /**
     * Get a driver from ID
     */
    public Driver getDriver(String id) {
        HashMap<String, String> obj = new HashMap<>();
        final String SQL_SELECT_DRIVER =
                "SELECT * FROM " + DriverContract.DriverEntry.TABLE_NAME +
                        " WHERE " + DriverContract.DriverEntry.COLUMN_DRIVER_ID +
                        " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL_SELECT_DRIVER, null);
        cursor.moveToFirst();
        String firstName = "";
        String lastName = "";
        if (!cursor.isAfterLast() && cursor.getCount() > 0) {
            obj.put(DriverContract.DriverEntry.COLUMN_DRIVER_PIC, Arrays.toString(cursor.getBlob(1)));
            obj.put(DriverContract.DriverEntry.COLUMN_FULL_NAME, cursor.getString(2));
            obj.put(DriverContract.DriverEntry.COLUMN_STREET, cursor.getString(3));
            obj.put(DriverContract.DriverEntry.COLUMN_CITY, cursor.getString(4));
            obj.put(DriverContract.DriverEntry.COLUMN_STATE, cursor.getString(5));
            obj.put(DriverContract.DriverEntry.COLUMN_ZIP, cursor.getString(6));
            // Split the full name
            String[] fullName = obj.get(DriverContract.DriverEntry.COLUMN_FULL_NAME).split(" ");
            firstName = fullName[0];
            for (int i = 1; i <= fullName.length - 1; i++) {
                lastName += fullName[i];
            }
            cursor.moveToNext();
        }
        Driver driver = new Driver(Integer.parseInt(id),
                Utilities.byteArrayToBitmap(obj.get(DriverContract.DriverEntry.COLUMN_DRIVER_PIC).getBytes()),
                firstName, lastName,
                obj.get(DriverContract.DriverEntry.COLUMN_STREET),
                obj.get(DriverContract.DriverEntry.COLUMN_CITY),
                obj.get(DriverContract.DriverEntry.COLUMN_STATE),
                obj.get(DriverContract.DriverEntry.COLUMN_ZIP));
        cursor.close();
        db.close();
        return driver;
    }

    /**
     * Getting driver data from database
     */
    public ArrayList<Driver> getDrivers() {
        ArrayList<Driver> rows = new ArrayList<>();
        HashMap<String, String> driver = new HashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL_SELECT_ALL_ENTRIES, null);
        // Move to first row
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && cursor.getCount() > 0) {
            driver.put(DriverContract.DriverEntry.COLUMN_DRIVER_ID, cursor.getString(0));
            driver.put(DriverContract.DriverEntry.COLUMN_DRIVER_PIC, Arrays.toString(cursor.getBlob(1)));
            driver.put(DriverContract.DriverEntry.COLUMN_FULL_NAME, cursor.getString(2));
            driver.put(DriverContract.DriverEntry.COLUMN_STREET, cursor.getString(3));
            driver.put(DriverContract.DriverEntry.COLUMN_CITY, cursor.getString(4));
            driver.put(DriverContract.DriverEntry.COLUMN_STATE, cursor.getString(5));
            driver.put(DriverContract.DriverEntry.COLUMN_ZIP, cursor.getString(6));
            // Split the full name
            String[] fullName = driver.get(DriverContract.DriverEntry.COLUMN_FULL_NAME).split(" ");
            String firstName = fullName[0];
            String lastName = "";
            for (int i = 1; i <= fullName.length - 1; i++) {
                lastName += fullName[i];
            }
            rows.add(new Driver(Integer.parseInt(driver.get(DriverContract.DriverEntry.COLUMN_DRIVER_ID)),
                    Utilities.byteArrayToBitmap(driver.get(DriverContract.DriverEntry.COLUMN_DRIVER_PIC).getBytes()),
                    firstName, lastName, driver.get(DriverContract.DriverEntry.COLUMN_STREET),
                    driver.get(DriverContract.DriverEntry.COLUMN_CITY),
                    driver.get(DriverContract.DriverEntry.COLUMN_STATE),
                    driver.get(DriverContract.DriverEntry.COLUMN_ZIP)));
            driver.clear();
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        // return user
        return rows;
    }

    /**
     * Getting user login status
     * return true if rows are there in table
     */
    public int getRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL_SELECT_ALL_ENTRIES, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    /**
     * Re create database
     * Delete all tables and create them again
     */
    public void resetTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(DriverContract.DriverEntry.TABLE_NAME, null, null);
        db.close();
    }

}
