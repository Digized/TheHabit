package org.coolpeople.thehabit.model;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Zurai on 2017-02-04.
 */

public class Habit {

    public static final int GOOD = 0;
    public static final int BAD = 1;

    private String title;
    private int habitType;
    private int duration;
    private Date beginDate;
    private Date endDate;
    private int frequency;

    public int getFrequency() {
        return frequency;
    }

    public String getTitle() {
        return title;
    }

    public int getHabitType() {
        return habitType;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Habit(String title, int habitType, int duration){
        this.frequency = 1;
        this.title = title;
        this.habitType = habitType;
        this.duration = duration;
        Calendar c = Calendar.getInstance();
        this.beginDate = c.getTime();
        c.add(Calendar.DATE, duration);
        this.endDate = c.getTime();
    }
    public Habit(String title, int habitType, Date beginDate, Date endDate){
        this.frequency = 1;
        this.title = title;
        this.habitType = habitType;
        this.duration = (int) (endDate.getTime() - beginDate.getTime());
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public void save(Context context){
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.insert(this.title,this.habitType,this.beginDate,this.endDate);
    }

}
