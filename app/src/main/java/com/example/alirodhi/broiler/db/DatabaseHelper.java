package com.example.alirodhi.broiler.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.alirodhi.broiler.Models.HistorySensorModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.alirodhi.broiler.Models.HistorySensorModel.TABLE_NAME;

/**
 * Created by alirodhi on 7/7/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "broiler_db";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        // Create history table
        db.execSQL(HistorySensorModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        //Delete table if already exixst
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //Create table again
        onCreate(db);
    }

    public long insertHistory(String temp, String hum, String cdioksida, String ammonia){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` dan `timestamp` akan masuk otomatis
        // Tidak perlu memasukaanya
        values.put(HistorySensorModel.COLUMN_TEMP, temp);
        values.put(HistorySensorModel.COLUMN_HUM, hum);
        values.put(HistorySensorModel.COLUMN_CDIOKSIDA, cdioksida);
        values.put(HistorySensorModel.COLUMN_AMMONIA, ammonia);

        // Insert row
        long id = db.insert(TABLE_NAME, null, values);
        // Close database connection
        db.close();
        // Return id
        return id;
    }

    public HistorySensorModel getHistory(long id) {
        // Get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{
                        HistorySensorModel.COLUMN_ID,
                        HistorySensorModel.COLUMN_TEMP,
                        HistorySensorModel.COLUMN_HUM,
                        HistorySensorModel.COLUMN_CDIOKSIDA,
                        HistorySensorModel.COLUMN_AMMONIA,

                },
                HistorySensorModel.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // Prepare history object
        HistorySensorModel history = new HistorySensorModel(
                cursor.getInt(cursor.getColumnIndex(HistorySensorModel.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(HistorySensorModel.COLUMN_TEMP)),
                cursor.getString(cursor.getColumnIndex(HistorySensorModel.COLUMN_HUM)),
                cursor.getString(cursor.getColumnIndex(HistorySensorModel.COLUMN_CDIOKSIDA)),
                cursor.getString(cursor.getColumnIndex(HistorySensorModel.COLUMN_AMMONIA)),
                cursor.getString(cursor.getColumnIndex(HistorySensorModel.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return history;
    }

    public List<HistorySensorModel>getAllHistory(){
        List<HistorySensorModel> historySensorModels = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " +
                HistorySensorModel.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HistorySensorModel historySensorModel = new HistorySensorModel();
                historySensorModel.setId(cursor.getInt(cursor.getColumnIndex(historySensorModel.COLUMN_ID)));
                historySensorModel.setTemp(cursor.getString(cursor.getColumnIndex(historySensorModel.COLUMN_TEMP)));
                historySensorModel.setHum(cursor.getString(cursor.getColumnIndex(historySensorModel.COLUMN_HUM)));
                historySensorModel.setCdioksida(cursor.getString(cursor.getColumnIndex(historySensorModel.COLUMN_CDIOKSIDA)));
                historySensorModel.setAmmonia(cursor.getString(cursor.getColumnIndex(historySensorModel.COLUMN_AMMONIA)));
                historySensorModel.setTimestamp(cursor.getString(cursor.getColumnIndex(historySensorModel.COLUMN_TIMESTAMP)));

                historySensorModels.add(historySensorModel);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return histories list
        return historySensorModels;
    }

    public int getHistoryCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public void deleteHistory(HistorySensorModel historySensorModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, HistorySensorModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(historySensorModel.getId())});
        db.close();
    }

    public void hapusUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close(); // Closing database connection
    }
}
