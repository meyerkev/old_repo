package com.sammiller;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class StageThreeActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stagethreelayout);
    }
    public void checkAnswer(View v)
    {
    	RadioButton correctAnswer = (RadioButton) findViewById(R.id.radio_1);
    	if (correctAnswer.isChecked())
    	{
    		AlertDialog.Builder adb = new AlertDialog.Builder(this);
    		adb.create();
    		adb.setMessage("Correct!");
    		adb.show();
    	}
    	else
    	{
    		AlertDialog.Builder adb = new AlertDialog.Builder(this);
    		adb.create();
    		adb.setMessage("Incorrect! Correct answer is \"intestacy\"");
    		adb.show();
    	}
    }
}