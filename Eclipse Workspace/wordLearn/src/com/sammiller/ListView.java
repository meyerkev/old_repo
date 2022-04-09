package com.sammiller;

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
		"Quote1",
		"Quote2",
		"Quote3",
		"Quote4",
		"Quote5",
		"Quote6",
		"Quote7",
		"Quote8",
		"Quote9",
		"Quote10",
		"Quote11",
		"Quote12",
		"Quote13",
		"Quote14",
		"Quote15",
		"Quote16",
		"Quote17",
		"Quote18",
		"Quote19",
		"Quote20",
		"Quote21"
	};
	
	static final String[] FULLQUOTES = new String[] {
		"FullQuote1",
		"FullQuote2",
		"FullQuote3",
		"FullQuote4",
		"FullQuote5",
		"FullQuote6",
		"FullQuote7",
		"FullQuote8",
		"FullQuote9",
		"FullQuote10",
		"FullQuote11",
		"FullQuote12",
		"FullQuote13",
		"FullQuote14",
		"FullQuote15",
		"FullQuote16",
		"FullQuote17",
		"FullQuote18",
		"FullQuote19",
		"FullQuote20",
		"FullQuote21"
	};

}
