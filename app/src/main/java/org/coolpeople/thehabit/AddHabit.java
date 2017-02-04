package org.coolpeople.thehabit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.coolpeople.thehabit.model.Habit;

public class AddHabit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        // Number Picker - Slider
        final NumberPicker numPicker = (NumberPicker)findViewById(R.id.numberPicker);
        numPicker.setMinValue(1);
        numPicker.setMaxValue(365);
        numPicker.setWrapSelectorWheel(true);

        Button newHabit = (Button) findViewById(R.id.submitHabit);
        newHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String habitTitle = checkValid();

                RadioGroup radioButtonGroup = (RadioGroup)findViewById(R.id.radioHabitType);
                int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                View radioButton = radioButtonGroup.findViewById(radioButtonID);
                int indx = radioButtonGroup.indexOfChild(radioButton);

                // Get data from number picker
                int duration = numPicker.getValue();

                Button p1_button = (Button)findViewById(R.id.submitHabit);

                if(!habitTitle.equals("")) {
                    if (indx == Habit.GOOD) {
                        new Habit(habitTitle, Habit.GOOD, duration).save(getApplicationContext());
                    }else{
                        new Habit(habitTitle, Habit.BAD, duration).save(getApplicationContext());
                    }
                    finish();
                }
            }
        });
    }

    public String checkValid(){
        EditText edit = (EditText)findViewById(R.id.editText2);
        return edit.getText().toString();
        // checkTitle in previous titles
    }


}
