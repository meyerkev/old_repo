package com.android.hockeyapp;




import android.app.ListActivity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GameListActivity extends ListActivity {

	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.schedulelist);
		
		//this.getListView().setBackgroundResource(R.drawable.uofm_app_bg);
		this.getListView().setDivider(getResources().getDrawable(R.drawable.yellow_divider));
		this.getListView().setDividerHeight(3);
		
		/*
		//set children to different backgrounds
		for( int i = 0; i< this.getListView().getChildCount(); i ++){
			this.getListView().getChildAt(i).setBackgroundColor(i%2 ==0?Color.BLUE:Color.WHITE);
			((TextView)this.getListView().getChildAt(i)).setTextColor(i%2==0?Color.WHITE:Color.BLACK);
			Log.d("Kevin", "i = " + Integer.toString(i));
		}
		Log.d("Kevin", "Finished setting child colors");
		*/
		//this.getListAdapter().getItemViewType(0);
		
		final Spinner spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.spinner_choices, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		myOnItemSelectedListener m;
		spinner.setOnItemSelectedListener(m = new myOnItemSelectedListener(this));
		spinner.setPrompt("");
		m.onItemSelected(spinner, getCurrentFocus(), 0, 0l);
		//*
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
						Toast.LENGTH_SHORT).show();
			}
		});
		
		//
		



		//*/


	}
	
	public void rebuild(int month, int year){
		Game[] g = Backend.getGames(month, year);
		String[] adapt = new String[g.length];
		for (int i = 0; i<g.length; i++){
			adapt[i] = g[i].toString();
		}
		setListAdapter(new ArrayAdapter<String>(this, R.layout.hockey_game_item, adapt));
		
	}

}
