package com.asadrao.textreplacer.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.asadrao.textreplacer.SaveModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Database {
    String DB_PATH = "data/data/com.asadrao.textreplacer/databases/";
    String DB_NAME = "save_db.sqlite";
    Context activity;
    SQLiteDatabase sqLiteDatabase;

    public Database(Context activity) {
        this.activity = activity;
    }

    public void createDatabase() {
        boolean dBExist = false;

        try {
            dBExist = checkDatabase();
        } catch (Exception e) {
            e.printStackTrace();

        }
        if (dBExist) {

        } else {
            try {

                sqLiteDatabase = activity.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
                sqLiteDatabase.close();
                copyDatabaseTable();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
    }

    private void copyDatabaseTable() throws IOException {

        //open your local database as input stream
        InputStream myInput = activity.getAssets().open(DB_NAME);

        //path to the created empty database
        String outFileName = DB_PATH + DB_NAME;

        //open the empty database as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private boolean checkDatabase() {

        SQLiteDatabase checkDB = null;
        String myPath = DB_PATH + DB_NAME;
        try {
            try {
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            //no database exists...
        }


        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    public void open() {
        sqLiteDatabase = activity.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
    }

    public void close() {
        sqLiteDatabase.close();
    }

    //============================start custom methods / Crud for category table ====================================

  /*
    CREATE TABLE "svae_data" (
	"id"	INTEGER,
	"output"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT));
  */

    public long saveData(SaveModel saveModel) {
        long rowId = -1;
        try {
            open();
            ContentValues cv = new ContentValues();
            cv.put("output", saveModel.output);
            rowId = sqLiteDatabase.insert("svae_data", null, cv);
            close();
        } catch (SQLiteException e) {
            Toast.makeText(activity, "Database exception", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        System.out.println("-- Record inserted rowId : " + rowId);
        return rowId;
    }

    public ArrayList<SaveModel> getAllSavedData() {
        open();
        ArrayList<SaveModel> categoryBeans = new ArrayList<>();
        SaveModel temp;
        String query = "select * from svae_data";

        System.out.println("--query in getAllAttendance : " + query);
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String notif_title = cursor.getString(cursor.getColumnIndex("output"));
                temp = new SaveModel(id, notif_title);
                categoryBeans.add(temp);
                temp = null;
            }
            while (cursor.moveToNext());
            close();
            return categoryBeans;
        }
        close();
        return null;
    }

    public void deleteCategory(int id) {
        open();
        String query = "delete from svae_data WHERE id = '" + id + "'";
        sqLiteDatabase.execSQL(query);
        close();
    }

}