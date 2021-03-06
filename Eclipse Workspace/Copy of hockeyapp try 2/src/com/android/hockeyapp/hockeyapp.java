package com.android.hockeyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class hockeyapp extends Activity {
    /** Called when the activity is first created. */
    
    
    //TODO - set up calendar view - grid pattern
    //TODO - set up spinner to let you switch between months
    //TODO - build games
    //TODO - set up listview of games;
    static Backend back;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Backend.buildGames();
        //two buttons - 1 to calendar - 1 to List
        Button list = (Button)findViewById(R.id.list_button);
        Button calendar = (Button)findViewById(R.id.calendar_button);
        list.setOnClickListener(new View.OnClickListener() {  
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), GameListActivity.class);
				//myIntent.putExtra(MyListView.higher, firstApp.this);
				startActivity(myIntent);


			}
		});
        
        calendar.setOnClickListener(new View.OnClickListener() {  
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), GameCalendarActivity.class);
				//myIntent.putExtra(MyListView.higher, firstApp.this);
				startActivity(myIntent);
			}
        });

        
        
    }
    
}