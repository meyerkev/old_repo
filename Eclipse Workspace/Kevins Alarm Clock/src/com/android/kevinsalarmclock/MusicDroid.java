package com.android.kevinsalarmclock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MusicDroid extends ListActivity {

	//private static final String MEDIA_PATH = new String("/sdcard/");
	private List<String> songs = new ArrayList<String>();
	private MediaPlayer mp = new MediaPlayer();
	MediaScannerConnection msc;
	private static Information parent;

	public static void setParent(Information k){
		parent = k;
	}

	@Override
	public void onCreate(Bundle icicle) {
		try {
			super.onCreate(icicle);
			setContentView(R.layout.songlist);
			updateSongList();
		} catch (NullPointerException e) {
			Log.v(getString(R.string.app_name), e.getMessage());
		}
	}

	public void recursiveSearch(List<String> l, File allfile[],String endsWith){
		if(allfile == null){
			return;
		}

		for(int i = 0; i<allfile.length; i++){
			File file = allfile[i];
			if(file.getAbsolutePath().endsWith(endsWith)){
				songs.add(file.getAbsolutePath());
			}
			recursiveSearch(l,file.listFiles(),endsWith);
		}
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



	}



	public void updateSongList() {
		recursiveSearch(songs, Environment.getExternalStorageDirectory().listFiles(), ".mp3");
		ArrayAdapter<String> songList = new ArrayAdapter<String>(this,R.layout.song,songs);
		setListAdapter(songList);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		parent.getCurrentAlarm().setAudio(songs.get(position));
		parent.updateText();
		Log.d("Kevin", "Updated audio to "+ songs.get(position));
		finish();
	}
}