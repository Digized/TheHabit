package org.coolpeople.thehabit.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import org.coolpeople.thehabit.MainActivity;
import org.coolpeople.thehabit.R;
import org.coolpeople.thehabit.model.Habit;
import org.w3c.dom.Text;

import java.util.List;

import javax.crypto.Mac;

/**
 * Created by Zurai on 2017-02-04.
 */

public class HabitAdapter extends ArraySwipeAdapter<Habit> {

    public HabitAdapter(Context context, List<Habit> habits) {
        super(context, 0, habits);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Habit habit = (Habit) getItem(position);

        if(convertView ==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_habit,parent,false);
        }
        ImageView imgHabitType = (ImageView) convertView.findViewById(R.id.img_habit_type);
        switch (habit.getHabitType()){
            case Habit.GOOD:
                imgHabitType.setBackgroundResource(android.R.color.holo_green_light);
                break;
            case Habit.BAD:
                imgHabitType.setBackgroundResource(android.R.color.holo_red_light);
                break;
        }
        TextView habitTitle = (TextView) convertView.findViewById(R.id.tv_habit_title);
        habitTitle.setText(habit.getTitle());

        ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.pb_progress);
        progressBar.setProgress((int)habit.getProgress());

        TextView progressTxt = (TextView) convertView.findViewById(R.id.tv_progress);
        progressTxt.setText(habit.getDurationBeautify());
        HabitAdapter hb = this;
        Button button = (Button) convertView.findViewById(R.id.help_me);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getRootView().getContext())
                        .setTitle("SOS")
                        .setMessage("HELLLLPADAD ASOHK ASJDHKJAS HILDA")
                        .setPositiveButton("SEND IT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendSMS();
                            }
                        }).show();
            }
        });

        return convertView;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeLayout;
    }

    public void sendSMS(){

    }
}
