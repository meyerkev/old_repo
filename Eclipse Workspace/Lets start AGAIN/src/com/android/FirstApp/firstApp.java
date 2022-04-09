package com.android.FirstApp;

//import //com.SamsApp.GalleryView;
//import //com.SamsApp.ListView;
//import //com.SamsApp.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class firstApp extends Activity {
	/** Called when the activity is first created. */
	
	static final int DATE_DIALOG_ID = 0;
	//
	protected SongPlayer mp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mp = new SongPlayer(this);
		//Button genre_button = (Button) findViewById(R.id.genre_button);
		MyListView.setHigher(this);
		GalleryView.setHigher(this);
		Button art_button = (Button) findViewById(R.id.art_button);
		Button list_button = (Button) findViewById(R.id.list_button);
		//
		Button music_button = (Button) findViewById(R.id.music_button);

		final Spinner spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.spinner_choices, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new myOnItemSelectedListener("genre", this));

		//mp.start();
		
		art_button.setOnClickListener(new View.OnClickListener() {  
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), GalleryView.class);;
				int requestNum= spinner.getSelectedItemPosition();
				Log.d("Main", "Option selected is " + requestNum);
				myIntent.putExtra("REQUEST", requestNum);
				startActivity(myIntent);


			}
		});

		list_button.setOnClickListener(new View.OnClickListener() {  
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), MyListView.class);;
				int requestNum= spinner.getSelectedItemPosition();
				Log.d("Main", "Option selected is " + requestNum);
				myIntent.putExtra("REQUEST", requestNum);
				//myIntent.putExtra(MyListView.higher, firstApp.this);
				startActivity(myIntent);
				

			}
		});
		
		//*
		music_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mp.isPlaying()){
                	mp.stop();
                }else{
                	mp.restart();
                }
            }
        });
        //*/
	}
	//*
	public SongPlayer mp(){
		return mp;
		
	}
	//*/

}