package com.android.kevinsalarmclock;

import java.io.File;
import java.util.ArrayList;

import android.app.ListActivity;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

public class SongLists extends ListActivity{
	private static Information parent;
	MediaScannerConnection msc;

	public static void setParent(Information k){
		parent = k;
	}

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.songlist);
		
		ArrayAdapter<String> songList = new ArrayAdapter<String>(this,R.layout.song,icicle.getStringArray("songs"));
		setListAdapter(songList);

	}

	
	

	protected void onListItemClick(ListView l, View v, int position, long id) {

		parent.getCurrentAlarm().setAudio(""/*I dunno*/);
		parent.updateText();
		//parent.setScroll(position);
		//parent.setScroll(thisView.getScrollY());
		Log.d("Kevin", "Updated audio to "+ parent.getSongs().get(position));
		finish();
	}

}
