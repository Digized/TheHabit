package org.coolpeople.thehabit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.coolpeople.thehabit.adapter.HabitAdapter;
import org.coolpeople.thehabit.model.DBHelper;
import org.coolpeople.thehabit.model.Habit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DBHelper db;
    HabitAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final MainActivity myactivity = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddHabit.class);
                startActivity(intent);
            }
        });
        db = new DBHelper(this);
         adapter = new HabitAdapter(getApplicationContext(),db.getAllHabits());
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(db.getAllHabits().size());
        ListView listView = (ListView) findViewById(R.id.list_habit);

        listView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        update();

    }

    private void update(){
        adapter.clear();
        adapter.addAll(db.getAllHabits());
    }
}
