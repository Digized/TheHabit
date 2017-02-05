package org.coolpeople.thehabit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

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
        final ListView listView = (ListView) findViewById(R.id.list_habit);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.modal_delete_title)
                        .setMessage("Would you like to delete "+ habits.get(position).getTitle()+"?")
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               db.deleteHabit(habits.get(position).getId());
                                update();
                            }
                        })
                        .setNegativeButton(R.string.cancel,null)
                        .show();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_option_setting:
                Intent intent = new Intent(getApplicationContext(),Settings.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    public void sendSMS(){

    }
}
