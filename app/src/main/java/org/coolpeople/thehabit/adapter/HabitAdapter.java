package org.coolpeople.thehabit.adapter;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.coolpeople.thehabit.MainActivity;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import org.coolpeople.thehabit.MainActivity;
import org.coolpeople.thehabit.R;
import org.coolpeople.thehabit.Settings;
import org.coolpeople.thehabit.model.DBHelper;
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

    public View getView(int position, View convertView, ViewGroup parent) {
        final Habit habit = (Habit) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_habit, parent, false);
        }
        Button button = (Button) convertView.findViewById(R.id.sidebutton);
        ImageView imgHabitType = (ImageView) convertView.findViewById(R.id.img_habit_type);
        LinearLayout bottom = (LinearLayout) convertView.findViewById(R.id.bottom_wrapper);

        switch (habit.getHabitType()) {
            case Habit.GOOD:
                imgHabitType.setBackgroundResource(android.R.color.holo_green_light);
                bottom.setBackgroundResource(android.R.color.holo_green_light);
                button.setText("Completed");
                break;
            case Habit.BAD:
                imgHabitType.setBackgroundResource(android.R.color.holo_red_light);
                bottom.setBackgroundResource(android.R.color.holo_red_light);
                button.setText("Need Assistance");
                break;
        }
        TextView habitTitle = (TextView) convertView.findViewById(R.id.tv_habit_title);
        habitTitle.setText(habit.getTitle());

        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.pb_progress);
        progressBar.setProgress((int) habit.getProgress());
        HabitAdapter a = this;
        TextView progressTxt = (TextView) convertView.findViewById(R.id.tv_progress);
        progressTxt.setText(habit.getDurationBeautify());

        SwipeLayout sL = (SwipeLayout) convertView.findViewById(R.id.swipeLayout);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                if (habit.getHabitType() == Habit.GOOD) {
                    habit.setCompleted(1);
                    new DBHelper(v.getContext()).modify(habit.getId(),habit);
                   HabitAdapter.this.notifyDataSetChanged();
                } else {
                    try{
                        String[] s =new DBHelper(getContext()).getEmergencyContact();
                        new AlertDialog.Builder(v.getRootView().getContext())
                                .setTitle("Need Assistance")
                                .setMessage("Do you want to text your friend, "+s[0]+". They may be able to help you")
                                .setPositiveButton("SEND IT", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sendaSMS(view);
                                    }
                                }).show();
                    }catch (Exception e){
                        Intent intent = new Intent(getContext(), Settings.class);
                        getContext().startActivity(intent);
                    }

                }
            }
        });

        return convertView;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeLayout;
    }


    public void sendaSMS(View v) {
        ((MainActivity) v.getRootView().getContext()).sendSMS();
    }

    public void update(View v){
        ((MainActivity) v.getRootView().getContext()).update();
    }

}
