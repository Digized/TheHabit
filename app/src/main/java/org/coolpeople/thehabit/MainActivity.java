package org.coolpeople.thehabit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.coolpeople.thehabit.adapter.HabitAdapter;
import org.coolpeople.thehabit.model.DBHelper;
import org.coolpeople.thehabit.model.Habit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    DBHelper db;
    HabitAdapter adapter;
    List<Habit> habits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddHabit.class);
                startActivity(intent);
            }
        });
        db = new DBHelper(this);
        habits = db.getAllHabits();
        adapter = new HabitAdapter(getApplicationContext(), habits);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(db.getAllHabits().size());
        ListView listView = (ListView) findViewById(R.id.list_habit);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, position +"  "+ id, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        listView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        update();

    }

    private void update(){
        adapter.clear();
        habits = db.getHabitBySelector(Constants.SELECTOR_GOOD);
        adapter.addAll(habits);
    }
}
