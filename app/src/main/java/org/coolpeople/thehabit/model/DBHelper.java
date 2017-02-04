package org.coolpeople.thehabit.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

/**
 * Created by Zurai on 2017-02-04.
 */

public class DBHelper extends SQLiteOpenHelper {

    public final String DATABASE_NAME = "DatabaseHabit.db";
    public final String TABLE_NAME = "habits";
    public final String COLUMN_ID = "id";
    public final String COLUMN_TITLE = "title";
    public final String COLUMN_TYPE = "type";
    public final String COLUMN_START_DATE = "startDate";
    public final String COLUMN_END_DATE = "endDate";
    public final String DATE_FORMAT = "yyyy-MM-dd";


    public DBHelper(Context context){
        super(context, "DatabaseHabit.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table habits " +
                        "(id integer primary key, title text,type integer,startDate text, endDate text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS habits");
        onCreate(db);
    }

    public boolean insert(String title, int type, Date startDate, Date endDate){
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("type",type);
        contentValues.put("startDate",formatter.format(startDate));
        contentValues.put("endDate",formatter.format(endDate));
        db.insert(TABLE_NAME,null,contentValues);
        return true;
    }
}
