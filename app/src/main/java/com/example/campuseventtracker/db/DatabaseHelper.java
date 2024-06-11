package com.example.campuseventtracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.campuseventtracker.model.Events;
import com.example.campuseventtracker.model.Helper;
import com.example.campuseventtracker.model.UsersData;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String KEY_ID = "id";
    private static final String TABLE_USERS = "users";
    private static final String KEY_USERNAME = "first_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHONE = "phone";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "campusdb";
    private static final String TABLE_EVENTS = "events";
    private static final String TABLE_FAVORITES = "favourites";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_DETAILS = "details";
    private static final String COLUMN_LOCATION = "location";
    private static final String FAVOURITE_ID = "fav_id";
    private static final String FAVOURITE_USER_ID = "fav_user_id";
    private static final String FAVOURITE_EVENT_ID = "fav_event_id";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //UsersTable
        db.execSQL(" CREATE TABLE " + TABLE_USERS + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_USERNAME + " TEXT NOT NULL, " +
                KEY_EMAIL + " TEXT NOT NULL, " +
                KEY_PHONE + " TEXT NOT NULL, " +
                KEY_PASSWORD + " TEXT NOT NULL)"
        );
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_DATE + " INTEGER,"
                + COLUMN_USER_ID + " INTEGER,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_TYPE + " TEXT,"
                + COLUMN_IMAGE + " TEXT,"
                + COLUMN_DETAILS + " TEXT,"
                + COLUMN_LOCATION + " TEXT"
                + ")";
        db.execSQL(CREATE_EVENTS_TABLE);

        String CREATE_FAVOURITE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + FAVOURITE_ID + " INTEGER PRIMARY KEY,"
                + FAVOURITE_USER_ID + " INTEGER,"
                + FAVOURITE_EVENT_ID + " INTEGER"
                + ")";
        db.execSQL(CREATE_FAVOURITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void register(UsersData users) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, users.getUserName());
        values.put(KEY_EMAIL, users.getEmail());
        values.put(KEY_PHONE, users.getPhoneNum());
        values.put(KEY_PASSWORD, users.getPassword());

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get all users in a list view
    public List<UsersData> getAllUsers() {
        List<UsersData> usersList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UsersData users = new UsersData();
                users.setId(Integer.parseInt(cursor.getString(0)));
                users.setUserName(cursor.getString(1));
                users.setEmail(cursor.getString(2));
                users.setPhoneNum(cursor.getString(3));
                users.setPassword(cursor.getString(4));
                // Adding contact to list
                usersList.add(users);
            } while (cursor.moveToNext());
        }

        // return contact list
        return usersList;
    }

    public int updatePassword(UsersData usersData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, usersData.getUserName());
        values.put(KEY_EMAIL, usersData.getEmail());
        values.put(KEY_PHONE, usersData.getPhoneNum());
        values.put(KEY_PASSWORD, usersData.getPassword());
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(usersData.getId())});
    }

    public void addEvent(Events event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, event.getDate());
        values.put(COLUMN_USER_ID, event.getUser_id());
        values.put(COLUMN_NAME, event.getName());
        values.put(COLUMN_TYPE, event.getType());
        values.put(COLUMN_IMAGE, event.getImage());
        values.put(COLUMN_DETAILS, event.getDetails());
        values.put(COLUMN_LOCATION, event.getLocation());
        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }

    private boolean isEventInFavorites(long userId, long eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FAVORITES +
                " WHERE " + FAVOURITE_USER_ID + " = ?" +
                " AND " + FAVOURITE_EVENT_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(eventId)});
        boolean isEventInFavorites = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isEventInFavorites;
    }

    public List<Events> getEventsByUserId(long userId) {
        List<Events> eventsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
                long date = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                long user_id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                String details = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DETAILS));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));
                Events event = new Events(id, date, user_id, name, type, image, details, location);
                if (isEventInFavorites(userId, id)) {
                    event.setFavourite(true);
                } else {
                    event.setFavourite(false);
                }
                eventsList.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return eventsList;
    }

    public List<Events> getAllEvents() {
        List<Events> eventsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
                long date = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                long user_id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                String details = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DETAILS));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));
                Events event = new Events(id, date, user_id, name, type, image, details, location);
                event.setFavourite(false);
                if (isEventInFavorites(Helper.usersData.getId(), id)) {
                    event.setFavourite(true);
                } else {
                    event.setFavourite(false);
                }
                eventsList.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return eventsList;
    }

    public void addToFavorites(long userId, long eventId) {
        if (!isEventInFavorites(userId, eventId)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(FAVOURITE_USER_ID, userId);
            values.put(FAVOURITE_EVENT_ID, eventId);
            db.insert(TABLE_FAVORITES, null, values);
            db.close();
        }
    }

    public void removeFromFavorites(long userId, long eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = FAVOURITE_USER_ID + " = ? AND " + FAVOURITE_EVENT_ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(userId), String.valueOf(eventId)};
        db.delete(TABLE_FAVORITES, whereClause, whereArgs);
        db.close();
    }

    public List<Events> getFavoriteEventsByUserId(long userId) {
        List<Events> favoriteEventsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_EVENTS +
                " JOIN " + TABLE_FAVORITES +
                " ON " + TABLE_EVENTS + "." + COLUMN_ID + " = " + TABLE_FAVORITES + "." + FAVOURITE_EVENT_ID +
                " WHERE " + TABLE_FAVORITES + "." + FAVOURITE_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
                long date = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                long user_id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                String details = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DETAILS));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));

                Events event = new Events(id, date, user_id, name, type, image, details, location);
                event.setFavourite(true);
                if (Helper.usersData.getId() != user_id) {
                    favoriteEventsList.add(event);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return favoriteEventsList;
    }
}

