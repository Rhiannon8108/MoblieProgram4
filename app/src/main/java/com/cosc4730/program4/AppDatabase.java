package com.cosc4730.program4;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;

@Database(entities = {Expenses.class,}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ExpensesDao ExpensesDao();

    private static AppDatabase db;

    public static final String DATABASE_NAME = "database-expenses.db";

    public static AppDatabase getInstance(final Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
        }
        return db;
    }

}