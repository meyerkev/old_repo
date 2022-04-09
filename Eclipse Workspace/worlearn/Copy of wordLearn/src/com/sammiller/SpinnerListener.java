package com.sammiller;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SpinnerListener implements OnItemSelectedListener {
	
    public void onItemSelected(AdapterView<?> parent,
        View view, int pos, long id) {
    	
    	if(parent.getItemAtPosition(pos).toString().startsWith("See")){
			//Additional go to gallery actions

		}
    	
		else{
			//Additional go to list actions

		}
    }

    public void onNothingSelected(AdapterView<?> parent) {
      // Do nothing.
    }
}
