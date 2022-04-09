package com.sammiller;

import java.util.Date;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class ListView extends ListActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	//  setContentView(R.layout.list);
	  setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, QUOTES));

	  android.widget.ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	    	
	    	Toast.makeText(getBaseContext(), FULLQUOTES[position], Toast.LENGTH_LONG).show();
	    }
	  });
	}

	static final String[] QUOTES = new String[] {
		"intersect", "intestacy", "intestine", "intimidate","intricate"

	};
	
	static final String[] FULLQUOTES = new String[] {
		"v. To cut through or into so as to divide.",  "n. The condition resulting from one's dying not having made a valid will. ",  "n. That part of the digestive tube below or behind the stomach, extending to the anus. ",  "v. To cause to become frightened. ",  "adj. Difficult to follow or understand. "

	};
}
