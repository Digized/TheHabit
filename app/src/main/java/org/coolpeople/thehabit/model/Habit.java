package org.coolpeople.thehabit.model;

import java.util.Date;

/**
 * Created by Zurai on 2017-02-04.
 */

public class Habit {

    public static final int BAD = 0;
    public static final int GOOD = 1;

    private String title;
    private int habitType;
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

    public Habit(String title, int habitType, Date beginDate, Date endDate){
        this.frequency = 1;
        this.title = title;
        this.habitType = habitType;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

}
