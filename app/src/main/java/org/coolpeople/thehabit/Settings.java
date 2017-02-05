package org.coolpeople.thehabit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.coolpeople.thehabit.model.DBHelper;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final EditText emerge = (EditText)findViewById(R.id.emergeText);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText em = emerge;
                new DBHelper(getApplicationContext()).insertEmergencyContact("JOHN DOE",em.getText().toString());
                finish();

            }
        });
    }


}

