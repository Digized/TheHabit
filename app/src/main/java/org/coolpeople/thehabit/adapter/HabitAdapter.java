package org.coolpeople.thehabit.adapter;

import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.coolpeople.thehabit.R;
import org.coolpeople.thehabit.model.Habit;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Zurai on 2017-02-04.
 */

public class HabitAdapter extends ArrayAdapter<Habit> {

    public HabitAdapter(Context context, List<Habit> habits) {
        super(context, 0, habits);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Habit habit = getItem(position);

        if(convertView ==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_habit,parent,false);
        }
        ImageView imgHabitType = (ImageView) convertView.findViewById(R.id.img_habit_type);
        switch (habit.getHabitType()){
            case Habit.GOOD:
                imgHabitType.setBackgroundResource(android.R.color.holo_green_dark);
                break;
            case Habit.BAD:
                imgHabitType.setBackgroundResource(android.R.color.holo_red_dark);
                break;
        }
        TextView habitTitle = (TextView) convertView.findViewById(R.id.tv_habit_title);
        habitTitle.setText(habit.getTitle());

        ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.pb_progress);
        progressBar.setProgress((int) habit.getProgress());

        return convertView;
    }
}
