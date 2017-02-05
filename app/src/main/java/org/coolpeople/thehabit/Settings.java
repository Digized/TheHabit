package org.coolpeople.thehabit;

import android.database.CursorIndexOutOfBoundsException;
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

        final DBHelper db = new DBHelper(getApplicationContext());
        final EditText name = (EditText)findViewById(R.id.edit_EC_name);
        final EditText number = (EditText) findViewById(R.id.edit_EC_phone);
        String[] s;
        try {
            s = db.getEmergencyContact();
            name.setText(s[0]);
            number.setText(s[1]);
        }catch (CursorIndexOutOfBoundsException e){
            s =null;
        }

        Button submit = (Button) findViewById(R.id.add_EC);
        final String[] finalS = s;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertEmergencyContact(name.getText().toString(),number.getText().toString(), finalS !=null);
                finish();
            }
        });
    }


}

