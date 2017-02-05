package org.coolpeople.thehabit;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
int selection = 3;

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

        final Spinner selectorspin = (Spinner) findViewById(R.id.spin_selector);
        selectorspin.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.Selectors)));
       selectorspin.setSelection(selection);
        selectorspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selection = position;
                selectorspin.setSelection(selection);
                update();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
        ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();

    }

    public void update(){
        adapter.clear();
        habits = db.getHabitBySelector(selection);
        adapter.notifyDataSetChanged();
        adapter.addAll(habits);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
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
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        1);
            }
        }
    }
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        String[] EC = new DBHelper(this).getEmergencyContact();
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(EC[1], null, "Hi " + EC[0] + ",\n I NEED YOUR HELP!!", null, null);
                    Toast.makeText(this.getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this.getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}
