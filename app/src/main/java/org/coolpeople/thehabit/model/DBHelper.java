package org.coolpeople.thehabit.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

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
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);


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
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(String title, int type, Date startDate, Date endDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE,title);
        contentValues.put(COLUMN_TYPE,type);
        contentValues.put(COLUMN_START_DATE,formatter.format(startDate));
        contentValues.put(COLUMN_END_DATE,formatter.format(endDate));
        db.insert(TABLE_NAME,null,contentValues);
        return true;
    }

    public List<Habit> getAllHabits(){
        return queryDB("SELECT * FROM "+ TABLE_NAME);
    }
    
    public List<Habit> getHabitByType(int type){
        return queryDB("SELECT * FROM " + TABLE_NAME + " WHERE "+ COLUMN_TYPE + "="+type+"");
    }

    public int deleteHabit(int id){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME,
                COLUMN_ID+" = ? "+ id,
                new String[] { Integer.toString(id) });
    }

    public boolean modify(int id, Habit habit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE,habit.getTitle());
        contentValues.put(COLUMN_TYPE,habit.getHabitType());
        contentValues.put(COLUMN_START_DATE,formatter.format(habit.getBeginDate()));
        contentValues.put(COLUMN_END_DATE,formatter.format(habit.getEndDate()));
        db.update(TABLE_NAME, contentValues, COLUMN_ID+" = ? ", new String[] { Integer.toString(id) } );
        return true; 
    }
    
    private List<Habit> queryDB(String query){
        List<Habit> habitList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            try {
                habitList.add(new Habit(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE)),
                        formatter.parse(cursor.getString(cursor.getColumnIndex(COLUMN_START_DATE))),
                        formatter.parse(cursor.getString(cursor.getColumnIndex(COLUMN_END_DATE)))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }
        return habitList;
    }
}
