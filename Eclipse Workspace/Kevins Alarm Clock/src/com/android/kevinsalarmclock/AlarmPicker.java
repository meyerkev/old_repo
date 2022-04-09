package com.android.kevinsalarmclock;

import android.app.ListActivity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

public class AlarmPicker extends ListActivity{

	private static final int Set_Ringtone = 1;
	/*
	RingtoneManager r = new RingtoneManager(this);
	Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
	mMediaPlayer = new MediaPlayer();
	mMediaPlayer.setDataSource(this, alert);
	final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
	if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
		mMediaPlayer.setLooping(true);
		mMediaPlayer.prepare();
		mMediaPlayer.start();
	}	
	 */
	


	

	public void getRingtoneURL(){
		String uri = null;
		Intent intent = new Intent( RingtoneManager.ACTION_RINGTONE_PICKER);
		intent.putExtra( RingtoneManager.EXTRA_RINGTONE_TYPE,
				RingtoneManager.TYPE_NOTIFICATION);
		intent.putExtra( RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
		if( uri != null)
		{
			intent.putExtra( RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,
					Uri.parse( uri));
		}

		else
		{
			intent.putExtra( RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,
					(Uri)null);
		}
		startActivityForResult( intent, Set_Ringtone);

	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		super.onActivityResult(requestCode, resultCode, intent);
    	Bundle extras = intent.getExtras();
		
		if (resultCode == RESULT_OK) {
			Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
			if (uri != null) {
				String ringTonePath = uri.toString();
			}
		}
	}
}