package com.ashysystem.mbhq.db;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ashysystem.mbhq.util.SharedPreference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by ravi on 15/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Quote.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Quote.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    public void importFromCsv() throws IOException {
        //String mCSVfile = "Quotes.csv";
        String mCSVfile = "Author_Quote.csv";
        AssetManager manager =context.getAssets();



      InputStream inStream = null;
        try {
            inStream = manager.open(mCSVfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        ////////////
        SQLiteDatabase db = getWritableDatabase();
        String line = "";
        db.beginTransaction();

      //  Date startDateOfYear = cal.getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        String[] monthArr={"JAN","FEB","MARCH","APR","MAY","JUNE","JUL","AUG","SEP","OCT","NOV","DEC"};
        int dayNo=0;
        SharedPreference sharedPreference=new SharedPreference(context);
        String signUpdate= "";
        Date getDate=null;
        if(sharedPreference.read("signupdate","").equals(""))
        {
            signUpdate = sharedPreference.read("FIRST_LOGIN_TIME","");
        }else {
            signUpdate = sharedPreference.read("signupdate","");
        }

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date signupDateObj=null;
        try {
            signupDateObj=simpleDateFormat.parse(signUpdate);
        } catch (ParseException e) {
            e.printStackTrace();
            Calendar calendar =Calendar.getInstance();
            signUpdate = simpleDateFormat.format(calendar.getTime());
        }

        Calendar calObj = Calendar.getInstance();
        try {
            calObj.setTime(signupDateObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            buffer.readLine();
            while ((line = buffer.readLine()) != null) {
                String[] colums = line.split(",");
               if (colums.length != 2) {
                    Log.d("CSVParser", "Skipping Bad CSV Row");
                    continue;
                }
                if(dayNo>0)
                    calObj.add(Calendar.DATE, 1);
                ContentValues cv = new ContentValues();
                String quote=colums[0].trim();
                String replaceText=quote.replace("^",",");
                cv.put(Quote.COLUMN_QUOTE,replaceText);
                String author=colums[1].trim();
                if(author.equals("N/A"))
                    author="Unknown";
                cv.put(Quote.COLUMN_AUTHOR, author);
                cv.put(Quote.COLUMN_EDITED, 0);
                cv.put(Quote.COLUMN_FAV, 0);
                cv.put(Quote.COLUMN_MONTH,monthArr[calObj.get(Calendar.MONTH)]);
                cv.put(Quote.COLUMN_DATE,sdf.format(calObj.getTime()));
                dayNo++;
                db.insert(Quote.TABLE_NAME, null, cv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    public Quote getQuote(int id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Quote.TABLE_NAME,
                new String[]{Quote.COLUMN_ID, Quote.COLUMN_QUOTE, Quote.COLUMN_AUTHOR,Quote.COLUMN_EDITED,Quote.COLUMN_FAV,Quote.COLUMN_MONTH,Quote.COLUMN_DATE},
                Quote.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Quote note = new Quote(
                cursor.getInt(cursor.getColumnIndex(Quote.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Quote.COLUMN_QUOTE)),
                cursor.getString(cursor.getColumnIndex(Quote.COLUMN_AUTHOR)),
                        cursor.getInt(cursor.getColumnIndex(Quote.COLUMN_EDITED)),
                                cursor.getInt(cursor.getColumnIndex(Quote.COLUMN_FAV)),
                cursor.getString(cursor.getColumnIndex(Quote.COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndex(Quote.COLUMN_MONTH)));

        // close the db connection
        cursor.close();

        return note;
    }
    public Quote getQuoteByDate(String date) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Quote.TABLE_NAME,
                new String[]{Quote.COLUMN_ID, Quote.COLUMN_QUOTE, Quote.COLUMN_AUTHOR,Quote.COLUMN_EDITED,Quote.COLUMN_FAV,Quote.COLUMN_MONTH,Quote.COLUMN_DATE},
                Quote.COLUMN_DATE + "=?",
                new String[]{String.valueOf(date)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Quote note = null;
        try {
            note = new Quote(
                    cursor.getInt(cursor.getColumnIndex(Quote.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Quote.COLUMN_QUOTE)),
                    cursor.getString(cursor.getColumnIndex(Quote.COLUMN_AUTHOR)),
                    cursor.getInt(cursor.getColumnIndex(Quote.COLUMN_EDITED)),
                    cursor.getInt(cursor.getColumnIndex(Quote.COLUMN_FAV)),
                    cursor.getString(cursor.getColumnIndex(Quote.COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndex(Quote.COLUMN_MONTH)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // close the db connection
        cursor.close();

        return note;
    }
}