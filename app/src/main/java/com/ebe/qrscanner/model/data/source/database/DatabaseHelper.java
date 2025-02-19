package com.ebe.qrscanner.model.data.source.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ebe.qrscanner.model.data.dto.QRItemDTO;


@Database(entities = {QRItemDTO.class}, version = 1, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {

    public abstract QRItemDao qrItemDao();

    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper getDatabaseHelper(Context context) {
        if (databaseHelper == null) {
            databaseHelper = Room.databaseBuilder(context,
                            DatabaseHelper.class, "QRScanner-Database")
                    .allowMainThreadQueries()
                    .build();
        }

        return databaseHelper;
    }

}
