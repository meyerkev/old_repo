package com.android.hockeyapp;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.MonthDisplayHelper;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameCalendarActivity extends Activity{

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.table_layout_1);
        CalenderView cv = new CalenderView(this, 11, 2010);
        
        
        
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
        /*
        TableLayout t = (TableLayout)this.findViewById(R.id.menu);
        
        //set up all the spinner stuff here
        buildCalendar(11,2010,t);
        addGamesToCalendar(11,2010,t);
        
        
        
        
    }
	
	private void buildCalendar(int month, int year, TableLayout t){
		//requires that t be an empty table view
		MonthDisplayHelper mHelper = new MonthDisplayHelper(month, year);
		//Build first row here with just days of week;
		String[] daysOfWeek = {"Sun", "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat"};
		TableRow days = new TableRow(this);

		int height = t.getRootView().getHeight()-30;
		int width = t.getRootView().getWidth();
		for(int i = 0; i<7; i++){
			TextView label = new TextView(this);
	        label.setText(daysOfWeek[i]);
	        label.setPadding(3, 3, 3, 3);
	        label.setHeight(30);
	        days.addView(label, new TableRow.LayoutParams(i));
		}
		t.addView(days, 0);
		for( int i = 1; i<=6; i++ ){
			TableRow mRow = new TableRow(this);
			for (int j = 0; j<7; j++){
				//View v = new View(R.layout.calendar_item);
				TextView v = (TextView)findViewById(R.id.date);
				mRow.addView(v, j);
				
			}
			t.addView(mRow, i);
		}
		
		
		for (int i = 1; i<=mHelper.getNumberOfDaysInMonth();i++){
			int column = mHelper.getColumnOf(i);
			int row = mHelper.getRowOf(i)+ 1;
			((TextView)((TableRow)t.getChildAt(row)).getChildAt(column)).setText(Integer.toString(i));
		}
		/*
		for( int i = 1; i<t.getChildCount(); i++){
			for(int j = 0; j<((TableRow)(t.getChildAt(i))).getChildCount();j++){
				((TextView)((TableRow)t.getChildAt(i)).getChildAt(j)).setWidth((int) (t.getWidth()/7.0));
				((TextView)((TableRow)t.getChildAt(i)).getChildAt(j)).setWidth((int) ((t.getHeight()-t.getChildAt(0).getHeight())/6.0));
			}
		}
		//
	}

	
	private void addGamesToCalendar(int month, int year, TableLayout t){
		Game[] g = Backend.getGames(month, year);
		MonthDisplayHelper mHelper = new MonthDisplayHelper(month, year);
		for(int i = 0; i<g.length; i++){
			int day = g[i].t.monthDay;
			int column = mHelper.getColumnOf(day);
			int row = mHelper.getRowOf(day)+ 1;
			
			TextView v = (TextView)((TableRow)(t.getChildAt(row))).getChildAt(column);
			v.append("\n" + (g[i].home?"vs.":"@") +" \n" + g[i].opponent.initials + "\n" + g[i].timeString());
		}
		
	}
	
	
	
	/*
    private void appendRow(TableLayout table) {
        TableRow row = new TableRow(this);

        TextView label = new TextView(this);
        label.setText(R.string.table_layout_7_quit);
        label.setPadding(3, 3, 3, 3);

        TextView shortcut = new TextView(this);
        shortcut.setText(R.string.table_layout_7_ctrlq);
        shortcut.setPadding(3, 3, 3, 3);
        shortcut.setGravity(Gravity.RIGHT | Gravity.TOP);

        row.addView(label, new TableRow.LayoutParams(1));
        row.addView(shortcut, new TableRow.LayoutParams());

        table.addView(row, new TableLayout.LayoutParams());
	}
		//*/
	
}
