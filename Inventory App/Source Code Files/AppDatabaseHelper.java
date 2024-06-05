package com.cs360.mterry_inventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class AppDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "Inventory.db";
    private static final int DATABASE_Version = 1;

    private static final String USER_TABLE = "user_table";
    private static final String USER_COL_ONE = "first_name";
    private static final String USER_COL_TWO = "last_name";
    private static final String USER_COL_THREE = "email";
    private static final String USER_COL_FOUR = "password";

    private static final String ITEM_TABLE = "item_table";
    private static final String ITEM_COL_ONE = "id";
    private static final String ITEM_COL_TWO = "type";
    private static final String ITEM_COL_THREE = "make";
    private static final String ITEM_COL_FOUR = "model";
    private static final String ITEM_COL_FIVE = "location";
    private static final String ITEM_COL_SIX = "department";
    private static final String ITEM_COL_SEVEN = "user_assigned";

    AppDatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userQuery = "CREATE TABLE " + USER_TABLE +
                " (" + USER_COL_ONE + " TEXT, " +
                USER_COL_TWO + " TEXT, " +
                USER_COL_THREE + " TEXT PRIMARY KEY, " +
                USER_COL_FOUR + " TEXT);";

        String itemQuery = "CREATE TABLE " + ITEM_TABLE +
                " (" + ITEM_COL_ONE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ITEM_COL_TWO + " TEXT, " +
                ITEM_COL_THREE + " TEXT, " +
                ITEM_COL_FOUR + " TEXT, " +
                ITEM_COL_FIVE + " TEXT, " +
                ITEM_COL_SIX + " TEXT, " +
                ITEM_COL_SEVEN + " TEXT);";

        db.execSQL(userQuery);
        db.execSQL(itemQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE);
        onCreate(db);
    }

    /** @noinspection unused*/
    public void addUser(String firstName, String lastName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_COL_ONE, firstName);
        cv.put(USER_COL_TWO, lastName);
        cv.put(USER_COL_THREE, email);
        cv.put(USER_COL_FOUR, password);

        long result = db.insert(USER_TABLE, null, cv);

        if (result == -1)
            Toast.makeText(context, "User Failed to Add", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "User Added", Toast.LENGTH_SHORT).show();
    }

    Cursor readUser(String email, String password) {
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_COL_THREE + "=\"" +
                email + "\" AND " + USER_COL_FOUR + "=\"" + password + "\";";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        else
            cursor = null;
        return cursor;
    }

    public void addItem(String type, String make, String model, String location,
                        String department, String userAssigned) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ITEM_COL_TWO, type);
        cv.put(ITEM_COL_THREE, make);
        cv.put(ITEM_COL_FOUR, model);
        cv.put(ITEM_COL_FIVE, location);
        cv.put(ITEM_COL_SIX, department);
        cv.put(ITEM_COL_SEVEN, userAssigned);
        long result = db.insert(ITEM_TABLE, null, cv);

        if (result == -1)
            Toast.makeText(context, "Item Failed to Add", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show();
    }

    Cursor readData(String column) {
        String query = "SELECT * FROM " + ITEM_TABLE + " ORDER BY " + column + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        else
            cursor = null;

        return cursor;
    }

    public void updateData(String rowID, String type, String make, String model, String location,
                    String department, String userAssigned) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ITEM_COL_TWO, type);
        cv.put(ITEM_COL_THREE, make);
        cv.put(ITEM_COL_FOUR, model);
        cv.put(ITEM_COL_FIVE, location);
        cv.put(ITEM_COL_SIX, department);
        cv.put(ITEM_COL_SEVEN, userAssigned);

        long result = db.update(ITEM_TABLE, cv, "id=?", new String[] {rowID});

        if (result == -1)
            Toast.makeText(context, "Item Failed to Update", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Item Successfully Updated", Toast.LENGTH_SHORT).show();
    }

    public void deleteData(String rowID) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(ITEM_TABLE, "id=?", new String[] {rowID});

        if (result == -1)
            Toast.makeText(context, "Item Failed to Delete", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Item Successfully Deleted", Toast.LENGTH_SHORT).show();
    }
}
