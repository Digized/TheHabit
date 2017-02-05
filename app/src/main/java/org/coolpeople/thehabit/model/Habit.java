package org.coolpeople.thehabit.model;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


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
    private int completed;
    private long id;

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

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

    public int getDuration() {
        return duration;
    }
    public String getDurationBeautify(){
        return getDateAgo();
    }
    public long getId(){
        return id;
    }

    public Habit(String title, int habitType, int duration) {
        this.frequency = 1;
        this.title = title;
        this.habitType = habitType;
        this.duration = duration;
        this.beginDate = new Date(System.currentTimeMillis());

        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        cal.add(Calendar.DATE, duration);
        this.endDate = cal.getTime();
    }

    public Habit(String title, int habitType, Date beginDate, Date endDate, int completed, long id ) {
        this.frequency = 1;
        this.title = title;
        this.habitType = habitType;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.duration =(int) getDateDiff(beginDate, endDate, TimeUnit.DAYS);
        this.completed = completed;
        this.id = id;
    }

    public Habit(String title, int habitType, Date beginDate, Date endDate, int completed) {
        this.frequency = 1;
        this.title = title;
        this.habitType = habitType;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.duration =(int) getDateDiff(beginDate, endDate, TimeUnit.DAYS);
        this.completed = completed;
    }

    public void save(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.insert(this.title, this.habitType, this.beginDate, this.endDate, this.completed);
    }

    public long getProgress() {
        return (getDateDiff(new Date(System.currentTimeMillis()),beginDate,TimeUnit.DAYS));
    }

    public String getDateAgo() {
        Date date = endDate;
        Date now = new Date(System.currentTimeMillis());
        long days = getDateDiff(now, date, TimeUnit.DAYS);
        if (days < 7)
            return days + " d";
        else
            return days / 7 + " w"+ ((days%7 != 0) ? (" "+days % 7 + " d") :(" ")) ;
    }

    private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
