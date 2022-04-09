package com.sammiller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sammiller.ImageAdapter;

public class wordLearn extends Activity {
	//stuff we will get from data
	boolean soundOn;
	//variables
	int dlPacks, dlTotal, dlWords, dlWordTotal, mastered, totalWords;
  	String test; 
  	
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
    	//TODO dont neet this
    	soundOn = false;
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Spinner wordselect = (Spinner) findViewById(R.id.wordselect);
        Button buttonWS = (Button) findViewById(R.id.buttonWS);
        final Button buttonSound = (Button) findViewById(R.id.sound);
        final TextView data1 = (TextView) findViewById(R.id.statsR1data);
        final TextView data2 = (TextView) findViewById(R.id.statsR2data);
        final TextView data3 = (TextView) findViewById(R.id.statsR3data);
        GridView icons = (GridView) findViewById(R.id.icons);
        

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        		this, R.array.test_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wordselect.setAdapter(adapter);       
       
        buttonWS.setOnClickListener(new View.OnClickListener(){
        	 public void onClick(View view) {
        		 //TODO: get data
  
 	        	int requestNum= wordselect.getSelectedItemPosition();
 	        	Log.d("Main", "Option selected is " + requestNum);
 	        	
 	        	test = getResources().getStringArray(R.array.test_choices)[requestNum];
 	        	dlPacks = 0;//data.testInfo.dlPacks;
 	        	dlTotal = 0;//data.testInfo.dlTotal;
 	        	dlWords = 0;//data.testInfo.dlWords;
 	        	dlWordTotal = 0;//data.testInfo.dlWordTotal;
 	        	mastered = 0;//data.testInfo.mastered;
 	        	totalWords = 0;//data.testInfo.totalWords;
 	        	
 	        	data1.setText( getResources().getString(R.string.statsR1title) + " " +
	 	           		dlPacks + "/" + dlTotal +" "+ test + " word packs" );
	 	       	data2.setText( getResources().getString(R.string.statsR2title) + " " +
	 	       			dlWords + "/" + dlWordTotal +" "+ test + " words" );
	 	       	data3.setText( getResources().getString(R.string.statsR3title) + " " +
	 	       			mastered + "/" + totalWords + " of your " + test +" words" );
             }
        });
        
        buttonSound.setOnClickListener(new View.OnClickListener(){
       	 	public void onClick(View view) {
       	 		//TODO load soundOn from data
       	 		if(soundOn){
       	 			buttonSound.setBackgroundResource(R.drawable.soundoff);
       	 		}
       	 		else{
       	 			buttonSound.setBackgroundResource(R.drawable.soundon);
       	 		}
       	 		soundOn = !soundOn;
       	 	}
        });
        
        //TODO
        test = getResources().getStringArray(R.array.test_choices)[wordselect.getSelectedItemPosition()];
        dlPacks = 0;//data.testInfo.dlPacks;
     	dlTotal = 0;//data.testInfo.dlTotal;
     	dlWords = 0;//data.testInfo.dlWords;
     	dlWordTotal = 0;//data.testInfo.dlWordTotal;
     	mastered = 0;//data.testInfo.mastered;
     	totalWords = 0;//data.testInfo.totalWords;
     	data1.setText( getResources().getString(R.string.statsR1title) + " " +
           		dlPacks + "/" + dlTotal +" "+ test + " word packs" );
       	data2.setText( getResources().getString(R.string.statsR2title) + " " +
       			dlWords + "/" + dlWordTotal +" "+ test + " SAT words" );
       	data3.setText( getResources().getString(R.string.statsR3title) + " " +
	       			mastered + "/" + totalWords + " of your " + test +" words" );

        icons.setAdapter(new ImageAdapter(this));
        icons.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(HelloGridView.this, "" + position, Toast.LENGTH_SHORT).show();
            	Intent myIntent;
 	        	Log.d("Main", "Option selected is " + position);
 	        	switch(position){
 		        	case 0:
 		        		myIntent= new Intent(view.getContext(), GalleryView.class);
 		        		myIntent.putExtra("LEVEL", 1);
 		        		startActivityForResult(myIntent,0);
 		        	case 1:
 		        		myIntent= new Intent(view.getContext(), GalleryView.class);
 		        		myIntent.putExtra("LEVEL", 2);
 		        		startActivityForResult(myIntent,0);
 		        		break;
 		        	case 2:
 		        		myIntent= new Intent(view.getContext(), StageThreeActivity.class);
 		        		startActivityForResult(myIntent,0);
 		        		break;
 		        	case 3:
 		        		myIntent= new Intent(view.getContext(), ListView.class);
 		        		startActivityForResult(myIntent,0);
 		        		break;
 		        	case 4:
 		        		myIntent= new Intent(view.getContext(), DownloadActivity.class);
 		        		//myIntent.putExtra("REQUEST", 0);
 		        		startActivityForResult(myIntent,0);
 		        		break;
 		        	case 5:
 		        		myIntent= new Intent(view.getContext(), HowToActivity.class);
 		        		//myIntent.putExtra("REQUEST", 1);
 		        		startActivityForResult(myIntent,0);
 		        		break;
 		        	default:
 		        		Log.e("Main", "Invalid grid");
 		        		break;
 	        	}
            }
        });
        
        
    }
}