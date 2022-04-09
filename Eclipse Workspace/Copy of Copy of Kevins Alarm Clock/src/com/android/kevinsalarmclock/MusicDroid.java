package com.android.kevinsalarmclock;

import java.io.File;
import java.util.List;

import android.app.ListActivity;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

public class MusicDroid extends ListActivity {

	//private static final String MEDIA_PATH = new String("/sdcard/");
	//private List<String> songs = new ArrayList<String>();
	//private MediaPlayer mp = new MediaPlayer();
	MediaScannerConnection msc;
	private static Information parent;
	ScrollView thisView = null;

	public static void setParent(Information k){
		parent = k;
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.songlist);
		updateSongList();
		setSelection(parent.getScroll());
		
		Button upButton = (Button)findViewById(R.id.up_button);
		Button downButton = (Button) findViewById(R.id.down_button);
		upButton.setOnClickListener(new View.OnClickListener() {  
			public void onClick(View view) {
				MusicDroid.this.setSelection(Math.max(
						MusicDroid.this.getListView().getLastVisiblePosition() - 25,0));
			}
		});
		downButton.setOnClickListener(new View.OnClickListener() {  
			public void onClick(View view) {
				MusicDroid.this.setSelection(Math.min(
						MusicDroid.this.getListView().getLastVisiblePosition() + 25,
						MusicDroid.this.getListView().getCount()));
			}
		});
		
		
		
	}

	public static void recursiveSearch(List<String> l, File allfile[],String endsWith){
		if(allfile == null){
			return;
		}
		//recursiveSearch(l,file.listFiles(),endsWith);
		//*
		for(int i = 0; i<allfile.length; i++){
			File file = allfile[i];
			if(file.getAbsolutePath().endsWith(endsWith)){
				l.add(file.getAbsolutePath());
			}
			recursiveSearch(l,file.listFiles(),endsWith);
		}
		/*
		ContentResolver resolver = getBaseContext().getContentResolver(); 
		Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String [] {"ARTIST"}, null, null, null);
		while(cursor.moveToNext()) {
			Log.d("TEST", cursor.getString(0));
		}
		msc = new MediaScannerConnection(this,
				new MediaScannerConnectionClient() {
			public void onMediaScannerConnected() {
				for (final String fileName : songs) {
					File mpFile = new File(fileName);
					Log.d("MSC", mpFile.getAbsolutePath());
				}
			}
			public void onScanCompleted(String path, Uri uri) {
				Log.d("MPSCAN", "Complete"
						+ MediaStore.getMediaScannerUri());
				msc.disconnect();
			}
		});
		msc.connect();
		Uri Uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI; //uri to sd-card
		String[] Selection = new String[] {
				android.provider.MediaStore.Audio.Media._ID,
				android.provider.MediaStore.Audio.Media.TITLE,
				android.provider.MediaStore.Audio.Media.DATA,
				android.provider.MediaStore.Audio.Media.ARTIST,
				android.provider.MediaStore.Audio.Media.ALBUM, };
		Cursor mCursor = managedQuery(Uri, Selection,null, null, null);
		mCursor.moveToFirst();
		ArrayList<String> mp3List = new ArrayList<String>();
		while(mCursor.moveToNext()) {
			mp3List.add(mCursor.getString(mCursor.getColumnIndexOrThrow("ARTIST"))); //Adds the artist to the list
		}
		 */



	}

	public void updateSongList() {
		//recursiveSearch(parent.getSongs(), Environment.getExternalStorageDirectory().listFiles(), ".mp3");
		ArrayAdapter<String> songList = new ArrayAdapter<String>(this,R.layout.song,parent.getSongs());
		setListAdapter(songList);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		parent.getCurrentAlarm().setAudio((parent.getSongs().get(position)=="Default")?"":parent.getSongs().get(position));
		parent.updateText();
		parent.setScroll(position);
		//parent.setScroll(thisView.getScrollY());
		Log.d("Kevin", "Updated audio to "+ parent.getSongs().get(position));
		finish();
	}
}