/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.kevinsalarmclock;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.Collator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import android.app.ExpandableListActivity;
import android.app.SearchManager;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
public class MusicList extends ExpandableListActivity
implements View.OnCreateContextMenuListener
{	
	private static Information info;
	private MusicAlphabetIndexer mIndexer;
	private String mCurrentArtistId;
	private String mCurrentArtistName;
	private String mCurrentAlbumId;
	private String mCurrentAlbumName;
	private String mCurrentArtistNameForAlbum;
	boolean mIsUnknownArtist;
	boolean mIsUnknownAlbum;
	private ArtistAlbumListAdapter mAdapter;
	private boolean mAdapterSent;
	private final static int SEARCH = 13;
	private static int mLastListPosCourse = -1;
	private static int mLastListPosFine = -1;
	//private ServiceToken mToken;
	Cursor mArtistCursor;
	MediaScannerConnection msc;
	
	public static void setParent(Information i){
		info = i;
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setVolumeControlStream(AudioManager.STREAM_MUSIC);
		if (icicle != null) {
			mCurrentAlbumId = icicle.getString("selectedalbum");
			mCurrentAlbumName = icicle.getString("selectedalbumname");
			mCurrentArtistId = icicle.getString("selectedartist");
			mCurrentArtistName = icicle.getString("selectedartistname");
		}
		//mToken = MusicUtils.bindToService(this, this);

		IntentFilter f = new IntentFilter();
		f.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
		f.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		f.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		f.addDataScheme("file");


		setContentView(R.layout.media_picker_activity_expanding);
		//MusicUtils.updateButtonBar(this, R.id.artisttab);
		ExpandableListView lv = getExpandableListView();
		lv.setOnCreateContextMenuListener(this);
		lv.setTextFilterEnabled(true);

		mAdapter = (ArtistAlbumListAdapter) getLastNonConfigurationInstance();
		if (mAdapter == null) {
			//Log.i("@@@", "starting query");
			mAdapter = new ArtistAlbumListAdapter(
					getApplication(),
					this,
					null, // cursor
					R.layout.track_list_item_group,
					new String[] {},
					new int[] {},
					R.layout.track_list_item_child,
					new String[] {},
					new int[] {});
			setListAdapter(mAdapter);
			setTitle(R.string.working_artists);
			getArtistCursor(mAdapter.getQueryHandler(), null);
		} else {
			mAdapter.setActivity(this);
			setListAdapter(mAdapter);
			mArtistCursor = mAdapter.getCursor();
			if (mArtistCursor != null) {
				init(mArtistCursor);
			} else {
				getArtistCursor(mAdapter.getQueryHandler(), null);
			}
		}
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		mAdapterSent = true;
		return mAdapter;
	}

	@Override
	public void onSaveInstanceState(Bundle outcicle) {
		// need to store the selected item so we don't lose it in case
		// of an orientation switch. Otherwise we could lose it while
		// in the middle of specifying a playlist to add the item to.
		outcicle.putString("selectedalbum", mCurrentAlbumId);
		outcicle.putString("selectedalbumname", mCurrentAlbumName);
		outcicle.putString("selectedartist", mCurrentArtistId);
		outcicle.putString("selectedartistname", mCurrentArtistName);
		super.onSaveInstanceState(outcicle);
	}

	@Override
	public void onDestroy() {
		ExpandableListView lv = getExpandableListView();
		if (lv != null) {
			mLastListPosCourse = lv.getFirstVisiblePosition();
			View cv = lv.getChildAt(0);
			if (cv != null) {
				mLastListPosFine = cv.getTop();
			}
		}
		// If we have an adapter and didn't send it off to another activity yet, we should
		// close its cursor, which we do by assigning a null cursor to it. Doing this
		// instead of closing the cursor directly keeps the framework from accessing
		// the closed cursor later.
		if (!mAdapterSent && mAdapter != null) {
			mAdapter.changeCursor(null);
		}
		// Because we pass the adapter to the next activity, we need to make
		// sure it doesn't keep a reference to this activity. We can do this
		// by clearing its DatasetObservers, which setListAdapter(null) does.
		setListAdapter(null);
		mAdapter = null;
		setListAdapter(null);
		super.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
	}


	public void init(Cursor c) {

		if (mAdapter == null) {
			return;
		}
		mAdapter.changeCursor(c); // also sets mArtistCursor

		if (mArtistCursor == null) {
			closeContextMenu();
			return;
		}

		// restore previous position
		if (mLastListPosCourse >= 0) {
			ExpandableListView elv = getExpandableListView();
			elv.setSelectionFromTop(mLastListPosCourse, mLastListPosFine);
			mLastListPosCourse = -1;
		}
		setTitle();
	}

	private void setTitle() {
		setTitle(R.string.artists_title);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

		mCurrentAlbumId = Long.valueOf(id).toString();

		Intent intent = new Intent(v.getContext(),SongLists.class);
		/*
		intent = new Intent(ACTION_PICK);
		intent.setDataAndType(Uri.EMPTY, "vnd.android.cursor.dir/track")
		//*/
		intent.putExtra("album", mCurrentAlbumId);
		Cursor c = (Cursor) getExpandableListAdapter().getChild(groupPosition, childPosition);
		String album = c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
		if (album == null || album.equals(MediaStore.UNKNOWN_STRING)) {
			// unknown album, so we should include the artist ID to limit the songs to songs only by that artist 
			mArtistCursor.moveToPosition(groupPosition);
			mCurrentArtistId = mArtistCursor.getString(mArtistCursor.getColumnIndex(MediaStore.Audio.Artists._ID));
			intent.putExtra("artist", mCurrentArtistId);
		}
		intent.putExtra("song", "");
		intent.putExtra("songs", getSongs(album, mCurrentArtistId));
		//intent.addCategory("mine.MINE");
		Log.d("Kevin", "You are here.");
		startActivity(intent);
		finish();
		return true;
	}
	
	String[] getSongs(String Album, String Artist){
		String[] songs = new String[0];


		msc = new MediaScannerConnection(this,
				new MediaScannerConnectionClient() {

			public void onMediaScannerConnected() {
				for (final String file : MusicList.info.getSongs()) {
					File mpFile = new File(file);
					msc.scanFile(mpFile.getAbsolutePath(), null);
					//Log.d("MSC", mpFile.getAbsolutePath());
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
		int pos = 0;
		while(mCursor.moveToNext()) {
			if(mCursor.getString(mCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ALBUM))== Album ||
					mCursor.getString(mCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST))== Artist){
				String[] newSongs = new String[songs.length+1];
				for(int i = 0; i<songs.length; i++){
					newSongs[i] = songs[i];
				}
				newSongs[songs.length] = info.getSongs().get(pos);
				songs = newSongs;
				pos++;
			}
		}
		mCursor.close();
		return songs;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfoIn) {
		//        menu.add(0, PLAY_SELECTION, 0, R.string.play_selection);
		//        SubMenu sub = menu.addSubMenu(0, ADD_TO_PLAYLIST, 0, R.string.add_to_playlist);
		//        MusicUtils.makePlaylistMenu(this, sub);
		//        menu.add(0, DELETE_ITEM, 0, R.string.delete_item);

		ExpandableListContextMenuInfo mi = (ExpandableListContextMenuInfo) menuInfoIn;

		int itemtype = ExpandableListView.getPackedPositionType(mi.packedPosition);
		int gpos = ExpandableListView.getPackedPositionGroup(mi.packedPosition);
		int cpos = ExpandableListView.getPackedPositionChild(mi.packedPosition);
		if (itemtype == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
			if (gpos == -1) {
				// this shouldn't happen
				Log.d("Artist/Album", "no group");
				return;
			}
			gpos = gpos - getExpandableListView().getHeaderViewsCount();
			mArtistCursor.moveToPosition(gpos);
			mCurrentArtistId = mArtistCursor.getString(mArtistCursor.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID));
			mCurrentArtistName = mArtistCursor.getString(mArtistCursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST));
			mCurrentAlbumId = null;
			mIsUnknownArtist = mCurrentArtistName == null ||
			mCurrentArtistName.equals(MediaStore.UNKNOWN_STRING);
			mIsUnknownAlbum = true;
			if (mIsUnknownArtist) {
				menu.setHeaderTitle(getString(R.string.unknown_artist_name));
			} else {
				menu.setHeaderTitle(mCurrentArtistName);
				menu.add(0, SEARCH, 0, R.string.search_title);
			}
			return;
		} else if (itemtype == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			if (cpos == -1) {
				// this shouldn't happen
				Log.d("Artist/Album", "no child");
				return;
			}
			Cursor c = (Cursor) getExpandableListAdapter().getChild(gpos, cpos);
			c.moveToPosition(cpos);
			mCurrentArtistId = null;
			mCurrentAlbumId = Long.valueOf(mi.id).toString();
			mCurrentAlbumName = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM));
			gpos = gpos - getExpandableListView().getHeaderViewsCount();
			mArtistCursor.moveToPosition(gpos);
			mCurrentArtistNameForAlbum = mArtistCursor.getString(
					mArtistCursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST));
			mIsUnknownArtist = mCurrentArtistNameForAlbum == null ||
			mCurrentArtistNameForAlbum.equals(MediaStore.UNKNOWN_STRING);
			mIsUnknownAlbum = mCurrentAlbumName == null ||
			mCurrentAlbumName.equals(MediaStore.UNKNOWN_STRING);
			if (mIsUnknownAlbum) {
				menu.setHeaderTitle(getString(R.string.unknown_album_name));
			} else {
				menu.setHeaderTitle(mCurrentAlbumName);
			}
			if (!mIsUnknownAlbum || !mIsUnknownArtist) {
				menu.add(0, SEARCH, 0, R.string.search_title);
			}
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case SEARCH:
			doSearch();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	void doSearch() {
		CharSequence title = null;
		String query = null;

		Intent i = new Intent();
		i.setAction(MediaStore.INTENT_ACTION_MEDIA_SEARCH);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		if (mCurrentArtistId != null) {
			title = mCurrentArtistName;
			query = mCurrentArtistName;
			i.putExtra(MediaStore.EXTRA_MEDIA_ARTIST, mCurrentArtistName);
			i.putExtra(MediaStore.EXTRA_MEDIA_FOCUS, MediaStore.Audio.Artists.ENTRY_CONTENT_TYPE);
		} else {
			if (mIsUnknownAlbum) {
				title = query = mCurrentArtistNameForAlbum;
			} else {
				title = query = mCurrentAlbumName;
				if (!mIsUnknownArtist) {
					query = query + " " + mCurrentArtistNameForAlbum;
				}
			}
			i.putExtra(MediaStore.EXTRA_MEDIA_ARTIST, mCurrentArtistNameForAlbum);
			i.putExtra(MediaStore.EXTRA_MEDIA_ALBUM, mCurrentAlbumName);
			i.putExtra(MediaStore.EXTRA_MEDIA_FOCUS, MediaStore.Audio.Albums.ENTRY_CONTENT_TYPE);
		}
		title = getString(R.string.mediasearch, title);
		i.putExtra(SearchManager.QUERY, query);

		startActivity(Intent.createChooser(i, title));
	}

	private Cursor getArtistCursor(AsyncQueryHandler async, String filter) {

		StringBuilder where = new StringBuilder();
		where.append(MediaStore.Audio.Artists.ARTIST + " != ''");

		// Add in the filtering constraints
		String [] keywords = null;
		if (filter != null) {
			String [] searchWords = filter.split(" ");
			keywords = new String[searchWords.length];
			Collator col = Collator.getInstance();
			col.setStrength(Collator.PRIMARY);
			for (int i = 0; i < searchWords.length; i++) {
				String key = MediaStore.Audio.keyFor(searchWords[i]);
				key = key.replace("\\", "\\\\");
				key = key.replace("%", "\\%");
				key = key.replace("_", "\\_");
				keywords[i] = '%' + key + '%';
			}
			for (int i = 0; i < searchWords.length; i++) {
				where.append(" AND ");
				where.append(MediaStore.Audio.Media.ARTIST_KEY + " LIKE ? ESCAPE '\\'");
			}
		}

		String whereclause = where.toString();  
		String[] cols = new String[] {
				MediaStore.Audio.Artists._ID,
				MediaStore.Audio.Artists.ARTIST,
				MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
				MediaStore.Audio.Artists.NUMBER_OF_TRACKS
		};
		Cursor ret = null;
		if (async != null) {
			async.startQuery(0, null, MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
					cols, whereclause , keywords, MediaStore.Audio.Artists.ARTIST_KEY);
		} else {
			ret = query(this, MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
					cols, whereclause , keywords, MediaStore.Audio.Artists.ARTIST_KEY);
		}
		return ret;
	}

	public static Cursor query(Context context, Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder, int limit) {
		try {
			ContentResolver resolver = context.getContentResolver();
			if (resolver == null) {
				return null;
			}
			if (limit > 0) {
				uri = uri.buildUpon().appendQueryParameter("limit", "" + limit).build();
			}
			return resolver.query(uri, projection, selection, selectionArgs, sortOrder);
		} catch (UnsupportedOperationException ex) {
			return null;
		}

	}
	public static Cursor query(Context context, Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		return query(context, uri, projection, selection, selectionArgs, sortOrder, 0);
	}

	


	static class ArtistAlbumListAdapter extends SimpleCursorTreeAdapter implements SectionIndexer {

		private final BitmapDrawable mDefaultAlbumIcon;
		private int mGroupArtistIdIdx;
		private int mGroupArtistIdx;
		private int mGroupAlbumIdx;
		private int mGroupSongIdx;
		private final Context mContext;
		private final Resources mResources;
		private final String mAlbumSongSeparator;
		private final String mUnknownAlbum;
		private final String mUnknownArtist;
		private final StringBuilder mBuffer = new StringBuilder();
		private final Object[] mFormatArgs = new Object[1];
		private final Object[] mFormatArgs3 = new Object[3];
		private MusicAlphabetIndexer mIndexer;
		private MusicList mActivity;
		private AsyncQueryHandler mQueryHandler;
		private String mConstraint = null;
		private boolean mConstraintIsValid = false;

		static class ViewHolder {
			TextView line1;
			TextView line2;
			ImageView play_indicator;
			ImageView icon;
		}

		class QueryHandler extends AsyncQueryHandler {
			QueryHandler(ContentResolver res) {
				super(res);
			}

			@Override
			protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
				//Log.i("@@@", "query complete");
				mActivity.init(cursor);
			}
		}

		ArtistAlbumListAdapter(Context context, MusicList currentactivity,
				Cursor cursor, int glayout, String[] gfrom, int[] gto, 
				int clayout, String[] cfrom, int[] cto) {
			super(context, cursor, glayout, gfrom, gto, clayout, cfrom, cto);
			mActivity = currentactivity;
			mQueryHandler = new QueryHandler(context.getContentResolver());

			Resources r = context.getResources();
			mDefaultAlbumIcon = (BitmapDrawable) r.getDrawable(R.drawable.albumart_mp_unknown_list);
			// no filter or dither, it's a lot faster and we can't tell the difference
			mDefaultAlbumIcon.setFilterBitmap(false);
			mDefaultAlbumIcon.setDither(false);

			mContext = context;
			getColumnIndices(cursor);
			mResources = context.getResources();
			mAlbumSongSeparator = context.getString(R.string.albumsongseparator);
			mUnknownAlbum = context.getString(R.string.unknown_album_name);
			mUnknownArtist = context.getString(R.string.unknown_artist_name);
			if (mIndexer != null) {
				mIndexer.setCursor(cursor);
			} else {
				mIndexer = new MusicAlphabetIndexer(cursor, mGroupArtistIdx, 
						mResources.getString(R.string.fast_scroll_alphabet));
			}
		}

		private void getColumnIndices(Cursor cursor) {
			if (cursor != null) {
				mGroupArtistIdIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID);
				mGroupArtistIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST);
				mGroupAlbumIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);
				mGroupSongIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);
				if (mIndexer != null) {
					mIndexer.setCursor(cursor);
				} else {
					mIndexer = new MusicAlphabetIndexer(cursor, mGroupArtistIdx, 
							mResources.getString(R.string.fast_scroll_alphabet));
				}
			}
		}

		public void setActivity(MusicList newactivity) {
			mActivity = newactivity;
		}

		public AsyncQueryHandler getQueryHandler() {
			return mQueryHandler;
		}

		@Override
		public View newGroupView(Context context, Cursor cursor, boolean isExpanded, ViewGroup parent) {
			View v = super.newGroupView(context, cursor, isExpanded, parent);
			ImageView iv = (ImageView) v.findViewById(R.id.icon);
			ViewGroup.LayoutParams p = iv.getLayoutParams();
			p.width = ViewGroup.LayoutParams.WRAP_CONTENT;
			p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
			ViewHolder vh = new ViewHolder();
			vh.line1 = (TextView) v.findViewById(R.id.line1);
			vh.line2 = (TextView) v.findViewById(R.id.line2);
			vh.play_indicator = (ImageView) v.findViewById(R.id.play_indicator);
			vh.icon = (ImageView) v.findViewById(R.id.icon);
			vh.icon.setPadding(0, 0, 1, 0);
			v.setTag(vh);
			return v;
		}

		@Override
		public View newChildView(Context context, Cursor cursor, boolean isLastChild,
				ViewGroup parent) {
			View v = super.newChildView(context, cursor, isLastChild, parent);
			ViewHolder vh = new ViewHolder();
			vh.line1 = (TextView) v.findViewById(R.id.line1);
			vh.line2 = (TextView) v.findViewById(R.id.line2);
			vh.play_indicator = (ImageView) v.findViewById(R.id.play_indicator);
			vh.icon = (ImageView) v.findViewById(R.id.icon);
			vh.icon.setBackgroundDrawable(mDefaultAlbumIcon);
			vh.icon.setPadding(0, 0, 1, 0);
			v.setTag(vh);
			return v;
		}

		@Override
		public void bindGroupView(View view, Context context, Cursor cursor, boolean isexpanded) {

			ViewHolder vh = (ViewHolder) view.getTag();

			String artist = cursor.getString(mGroupArtistIdx);
			String displayartist = artist;
			boolean unknown = artist == null || artist.equals(MediaStore.UNKNOWN_STRING);
			if (unknown) {
				displayartist = mUnknownArtist;
			}
			vh.line1.setText(displayartist);

			int numalbums = cursor.getInt(mGroupAlbumIdx);
			int numsongs = cursor.getInt(mGroupSongIdx);

			String songs_albums = makeAlbumsLabel(context,
					numalbums, numsongs, unknown);

			vh.line2.setText(songs_albums);


			vh.play_indicator.setImageDrawable(null);

		}

		@Override
		public void bindChildView(View view, Context context, Cursor cursor, boolean islast) {

			ViewHolder vh = (ViewHolder) view.getTag();

			String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM));
			String displayname = name;
			boolean unknown = name == null || name.equals(MediaStore.UNKNOWN_STRING); 
			if (unknown) {
				displayname = mUnknownAlbum;
			}
			vh.line1.setText(displayname);

			int numsongs = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
			int numartistsongs = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS_FOR_ARTIST));

			final StringBuilder builder = mBuffer;
			builder.delete(0, builder.length());
			if (unknown) {
				numsongs = numartistsongs;
			}

			if (numsongs == 1) {
				builder.append(context.getString(R.string.onesong));
			} else {
				if (numsongs == numartistsongs) {
					final Object[] args = mFormatArgs;
					args[0] = numsongs;
					builder.append(mResources.getQuantityString(R.plurals.Nsongs, numsongs, args));
				} else {
					final Object[] args = mFormatArgs3;
					args[0] = numsongs;
					args[1] = numartistsongs;
					args[2] = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST));
					builder.append(mResources.getQuantityString(R.plurals.Nsongscomp, numsongs, args));
				}
			}
			vh.line2.setText(builder.toString());

			ImageView iv = vh.icon;
			// We don't actually need the path to the thumbnail file,
			// we just use it to see if there is album art or not
			String art = cursor.getString(cursor.getColumnIndexOrThrow(
					MediaStore.Audio.Albums.ALBUM_ART));
			if (unknown || art == null || art.length() == 0) {
				iv.setBackgroundDrawable(mDefaultAlbumIcon);
				iv.setImageDrawable(null);
			} else {
				long artIndex = cursor.getLong(0);
				Drawable d = getCachedArtwork(context, artIndex, mDefaultAlbumIcon);
				iv.setImageDrawable(d);
			}
			iv.setImageDrawable(null);

		}


		@Override
		protected Cursor getChildrenCursor(Cursor groupCursor) {

			long id = groupCursor.getLong(groupCursor.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID));

			String[] cols = new String[] {
					MediaStore.Audio.Albums._ID,
					MediaStore.Audio.Albums.ALBUM,
					MediaStore.Audio.Albums.NUMBER_OF_SONGS,
					MediaStore.Audio.Albums.NUMBER_OF_SONGS_FOR_ARTIST,
					MediaStore.Audio.Albums.ALBUM_ART
			};
			Cursor c = query(mActivity,
					MediaStore.Audio.Artists.Albums.getContentUri("external", id),
					cols, null, null, MediaStore.Audio.Albums.DEFAULT_SORT_ORDER);

			class MyCursorWrapper extends CursorWrapper {
				String mArtistName;
				int mMagicColumnIdx;
				MyCursorWrapper(Cursor c, String artist) {
					super(c);
					mArtistName = artist;
					if (mArtistName == null || mArtistName.equals(MediaStore.UNKNOWN_STRING)) {
						mArtistName = mUnknownArtist;
					}
					mMagicColumnIdx = c.getColumnCount();
				}

				@Override
				public String getString(int columnIndex) {
					if (columnIndex != mMagicColumnIdx) {
						return super.getString(columnIndex);
					}
					return mArtistName;
				}

				@Override
				public int getColumnIndexOrThrow(String name) {
					if (MediaStore.Audio.Albums.ARTIST.equals(name)) {
						return mMagicColumnIdx;
					}
					return super.getColumnIndexOrThrow(name); 
				}

				@Override
				public String getColumnName(int idx) {
					if (idx != mMagicColumnIdx) {
						return super.getColumnName(idx);
					}
					return MediaStore.Audio.Albums.ARTIST;
				}

				@Override
				public int getColumnCount() {
					return super.getColumnCount() + 1;
				}
			}
			return new MyCursorWrapper(c, groupCursor.getString(mGroupArtistIdx));
		}

		@Override
		public void changeCursor(Cursor cursor) {
			if (mActivity.isFinishing() && cursor != null) {
				cursor.close();
				cursor = null;
			}
			if (cursor != mActivity.mArtistCursor) {
				mActivity.mArtistCursor = cursor;
				getColumnIndices(cursor);
				super.changeCursor(cursor);
			}
		}

		@Override
		public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
			String s = constraint.toString();
			if (mConstraintIsValid && (
					(s == null && mConstraint == null) ||
					(s != null && s.equals(mConstraint)))) {
				return getCursor();
			}
			Cursor c = mActivity.getArtistCursor(null, s);
			mConstraint = s;
			mConstraintIsValid = true;
			return c;
		}

		public Object[] getSections() {
			return mIndexer.getSections();
		}

		public int getPositionForSection(int sectionIndex) {
			return mIndexer.getPositionForSection(sectionIndex);
		}

		public int getSectionForPosition(int position) {
			return 0;
		}
	}
	/*
	 * Copyright (C) 2008 The Android Open Source Project
	 *
	 * Licensed under the Apache License, Version 2.0 (the "License");
	 * you may not use this file except in compliance with the License.
	 * You may obtain a copy of the License at
	 *
	 *      http://www.apache.org/licenses/LICENSE-2.0
	 *
	 * Unless required by applicable law or agreed to in writing, software
	 * distributed under the License is distributed on an "AS IS" BASIS,
	 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	 * See the License for the specific language governing permissions and
	 * limitations under the License.
	 */


	//private static final String TAG = "MusicUtils";


	public static String makeAlbumsLabel(Context context, int numalbums, int numsongs, boolean isUnknown) {
		// There are two formats for the albums/songs information:
		// "N Song(s)"  - used for unknown artist/album
		// "N Album(s)" - used for known albums

		StringBuilder songs_albums = new StringBuilder();

		Resources r = context.getResources();
		if (isUnknown) {
			if (numsongs == 1) {
				songs_albums.append(context.getString(R.string.onesong));
			} else {
				String f = r.getQuantityText(R.plurals.Nsongs, numsongs).toString();
				sFormatBuilder.setLength(0);
				sFormatter.format(f, Integer.valueOf(numsongs));
				songs_albums.append(sFormatBuilder);
			}
		} else {
			String f = r.getQuantityText(R.plurals.Nalbums, numalbums).toString();
			sFormatBuilder.setLength(0);
			sFormatter.format(f, Integer.valueOf(numalbums));
			songs_albums.append(sFormatBuilder);
			songs_albums.append(context.getString(R.string.albumsongseparator));
		}
		return songs_albums.toString();
	}

	/**
	 * This is now only used for the query screen
	 */
	public static String makeAlbumsSongsLabel(Context context, int numalbums, int numsongs, boolean isUnknown) {
		// There are several formats for the albums/songs information:
		// "1 Song"   - used if there is only 1 song
		// "N Songs" - used for the "unknown artist" item
		// "1 Album"/"N Songs" 
		// "N Album"/"M Songs"
		// Depending on locale, these may need to be further subdivided

		StringBuilder songs_albums = new StringBuilder();

		if (numsongs == 1) {
			songs_albums.append(context.getString(R.string.onesong));
		} else {
			Resources r = context.getResources();
			if (! isUnknown) {
				String f = r.getQuantityText(R.plurals.Nalbums, numalbums).toString();
				sFormatBuilder.setLength(0);
				sFormatter.format(f, Integer.valueOf(numalbums));
				songs_albums.append(sFormatBuilder);
				songs_albums.append(context.getString(R.string.albumsongseparator));
			}
			String f = r.getQuantityText(R.plurals.Nsongs, numsongs).toString();
			sFormatBuilder.setLength(0);
			sFormatter.format(f, Integer.valueOf(numsongs));
			songs_albums.append(sFormatBuilder);
		}
		return songs_albums.toString();
	}



	/*
	 * Returns true if a file is currently opened for playback (regardless
	 * of whether it's playing or paused).
	 */

	private final static long [] sEmptyList = new long[0];

	public static long [] getSongListForCursor(Cursor cursor) {
		if (cursor == null) {
			return sEmptyList;
		}
		int len = cursor.getCount();
		long [] list = new long[len];
		cursor.moveToFirst();
		int colidx = -1;
		try {
			colidx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.AUDIO_ID);
		} catch (IllegalArgumentException ex) {
			colidx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
		}
		for (int i = 0; i < len; i++) {
			list[i] = cursor.getLong(colidx);
			cursor.moveToNext();
		}
		return list;
	}

	public static long [] getSongListForArtist(Context context, long id) {
		final String[] ccols = new String[] { MediaStore.Audio.Media._ID };
		String where = MediaStore.Audio.Media.ARTIST_ID + "=" + id + " AND " + 
		MediaStore.Audio.Media.IS_MUSIC + "=1";
		Cursor cursor = query(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				ccols, where, null,
				MediaStore.Audio.Media.ALBUM_KEY + ","  + MediaStore.Audio.Media.TRACK);

		if (cursor != null) {
			long [] list = getSongListForCursor(cursor);
			cursor.close();
			return list;
		}
		return sEmptyList;
	}

	public static long [] getSongListForAlbum(Context context, long id) {
		final String[] ccols = new String[] { MediaStore.Audio.Media._ID };
		String where = MediaStore.Audio.Media.ALBUM_ID + "=" + id + " AND " + 
		MediaStore.Audio.Media.IS_MUSIC + "=1";
		Cursor cursor = query(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				ccols, where, null, MediaStore.Audio.Media.TRACK);

		if (cursor != null) {
			long [] list = getSongListForCursor(cursor);
			cursor.close();
			return list;
		}
		return sEmptyList;
	}

	public static long [] getSongListForPlaylist(Context context, long plid) {
		final String[] ccols = new String[] { MediaStore.Audio.Playlists.Members.AUDIO_ID };
		Cursor cursor = query(context, MediaStore.Audio.Playlists.Members.getContentUri("external", plid),
				ccols, null, null, MediaStore.Audio.Playlists.Members.DEFAULT_SORT_ORDER);

		if (cursor != null) {
			long [] list = getSongListForCursor(cursor);
			cursor.close();
			return list;
		}
		return sEmptyList;
	}

	public static long [] getAllSongs(Context context) {
		Cursor c = query(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				new String[] {MediaStore.Audio.Media._ID}, MediaStore.Audio.Media.IS_MUSIC + "=1",
				null, null);
		try {
			if (c == null || c.getCount() == 0) {
				return null;
			}
			int len = c.getCount();
			long [] list = new long[len];
			for (int i = 0; i < len; i++) {
				c.moveToNext();
				list[i] = c.getLong(0);
			}

			return list;
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}

	/**
	 * Fills out the given submenu with items for "new playlist" and
	 * any existing playlists. When the user selects an item, the
	 * application will receive PLAYLIST_SELECTED with the Uri of
	 * the selected playlist, NEW_PLAYLIST if a new playlist
	 * should be created, and QUEUE if the "current playlist" was
	 * selected.
	 * @param context The context to use for creating the menu items
	 * @param sub The submenu to add the items to.
	 */

	private static ContentValues[] sContentValuesCache = null;

	/**
	 * @param ids The source array containing all the ids to be added to the playlist
	 * @param offset Where in the 'ids' array we start reading
	 * @param len How many items to copy during this pass
	 * @param base The play order offset to use for this pass
	 */
	
	static protected Uri getContentURIForPath(String path) {
		return Uri.fromFile(new File(path));
	}


	/*  Try to use String.format() as little as possible, because it creates a
	 *  new Formatter every time you call it, which is very inefficient.
	 *  Reusing an existing Formatter more than tripled the speed of
	 *  makeTimeString().
	 *  This Formatter/StringBuilder are also used by makeAlbumSongsLabel()
	 */
	private static StringBuilder sFormatBuilder = new StringBuilder();
	private static Formatter sFormatter = new Formatter(sFormatBuilder, Locale.getDefault());
	private static final Object[] sTimeArgs = new Object[5];

	public static String makeTimeString(Context context, long secs) {
		String durationformat = context.getString(
				secs < 3600 ? R.string.durationformatshort : R.string.durationformatlong);

		/* Provide multiple arguments so the format can be changed easily
		 * by modifying the xml.
		 */
		sFormatBuilder.setLength(0);

		final Object[] timeArgs = sTimeArgs;
		timeArgs[0] = secs / 3600;
		timeArgs[1] = secs / 60;
		timeArgs[2] = (secs / 60) % 60;
		timeArgs[3] = secs;
		timeArgs[4] = secs % 60;

		return sFormatter.format(durationformat, timeArgs).toString();
	}


	// A really simple BitmapDrawable-like class, that doesn't do
	// scaling, dithering or filtering.
	private static class FastBitmapDrawable extends Drawable {
		private Bitmap mBitmap;
		public FastBitmapDrawable(Bitmap b) {
			mBitmap = b;
		}
		@Override
		public void draw(Canvas canvas) {
			canvas.drawBitmap(mBitmap, 0, 0, null);
		}
		@Override
		public int getOpacity() {
			return PixelFormat.OPAQUE;
		}
		@Override
		public void setAlpha(int alpha) {
		}
		@Override
		public void setColorFilter(ColorFilter cf) {
		}
	}
	private static final BitmapFactory.Options sBitmapOptionsCache = new BitmapFactory.Options();
	private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
	private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
	private static final HashMap<Long, Drawable> sArtCache = new HashMap<Long, Drawable>();

	static {
		// for the cache, 
		// 565 is faster to decode and display
		// and we don't want to dither here because the image will be scaled down later
		sBitmapOptionsCache.inPreferredConfig = Bitmap.Config.RGB_565;
		sBitmapOptionsCache.inDither = false;

		sBitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		sBitmapOptions.inDither = false;
	}

	public static void initAlbumArtCache() {
	}

	public static void clearAlbumArtCache() {
		synchronized(sArtCache) {
			sArtCache.clear();
		}
	}

	public static Drawable getCachedArtwork(Context context, long artIndex, BitmapDrawable defaultArtwork) {
		Drawable d = null;
		synchronized(sArtCache) {
			d = sArtCache.get(artIndex);
		}
		if (d == null) {
			d = defaultArtwork;
			final Bitmap icon = defaultArtwork.getBitmap();
			int w = icon.getWidth();
			int h = icon.getHeight();
			Bitmap b = getArtworkQuick(context, artIndex, w, h);
			if (b != null) {
				d = new FastBitmapDrawable(b);
				synchronized(sArtCache) {
					// the cache may have changed since we checked
					Drawable value = sArtCache.get(artIndex);
					if (value == null) {
						sArtCache.put(artIndex, d);
					} else {
						d = value;
					}
				}
			}
		}
		return d;
	}

	// Get album art for specified album. This method will not try to
	// fall back to getting artwork directly from the file, nor will
	// it attempt to repair the database.
	private static Bitmap getArtworkQuick(Context context, long album_id, int w, int h) {
		// NOTE: There is in fact a 1 pixel border on the right side in the ImageView
		// used to display this drawable. Take it into account now, so we don't have to
		// scale later.
		w -= 1;
		ContentResolver res = context.getContentResolver();
		Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
		if (uri != null) {
			ParcelFileDescriptor fd = null;
			try {
				fd = res.openFileDescriptor(uri, "r");
				int sampleSize = 1;

				// Compute the closest power-of-two scale factor 
				// and pass that to sBitmapOptionsCache.inSampleSize, which will
				// result in faster decoding and better quality
				sBitmapOptionsCache.inJustDecodeBounds = true;
				BitmapFactory.decodeFileDescriptor(
						fd.getFileDescriptor(), null, sBitmapOptionsCache);
				int nextWidth = sBitmapOptionsCache.outWidth >> 1;
				int nextHeight = sBitmapOptionsCache.outHeight >> 1;
		while (nextWidth>w && nextHeight>h) {
			sampleSize <<= 1;
			nextWidth >>= 1;
			nextHeight >>= 1;
		}

		sBitmapOptionsCache.inSampleSize = sampleSize;
		sBitmapOptionsCache.inJustDecodeBounds = false;
		Bitmap b = BitmapFactory.decodeFileDescriptor(
				fd.getFileDescriptor(), null, sBitmapOptionsCache);

		if (b != null) {
			// finally rescale to exactly the size we need
			if (sBitmapOptionsCache.outWidth != w || sBitmapOptionsCache.outHeight != h) {
				Bitmap tmp = Bitmap.createScaledBitmap(b, w, h, true);
				// Bitmap.createScaledBitmap() can return the same bitmap
				if (tmp != b) b.recycle();
				b = tmp;
			}
		}

		return b;
			} catch (FileNotFoundException e) {
			} finally {
				try {
					if (fd != null)
						fd.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	/** Get album art for specified album. You should not pass in the album id
	 * for the "unknown" album here (use -1 instead)
	 * This method always returns the default album art icon when no album art is found.
	 */
	public static Bitmap getArtwork(Context context, long song_id, long album_id) {
		return getArtwork(context, song_id, album_id, true);
	}

	/** Get album art for specified album. You should not pass in the album id
	 * for the "unknown" album here (use -1 instead)
	 */
	public static Bitmap getArtwork(Context context, long song_id, long album_id,
			boolean allowdefault) {

		if (album_id < 0) {
			// This is something that is not in the database, so get the album art directly
			// from the file.
			if (song_id >= 0) {
				Bitmap bm = getArtworkFromFile(context, song_id, -1);
				if (bm != null) {
					return bm;
				}
			}
			if (allowdefault) {
				return getDefaultArtwork(context);
			}
			return null;
		}

		ContentResolver res = context.getContentResolver();
		Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
		if (uri != null) {
			InputStream in = null;
			try {
				in = res.openInputStream(uri);
				return BitmapFactory.decodeStream(in, null, sBitmapOptions);
			} catch (FileNotFoundException ex) {
				// The album art thumbnail does not actually exist. Maybe the user deleted it, or
				// maybe it never existed to begin with.
				Bitmap bm = getArtworkFromFile(context, song_id, album_id);
				if (bm != null) {
					if (bm.getConfig() == null) {
						bm = bm.copy(Bitmap.Config.RGB_565, false);
						if (bm == null && allowdefault) {
							return getDefaultArtwork(context);
						}
					}
				} else if (allowdefault) {
					bm = getDefaultArtwork(context);
				}
				return bm;
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (IOException ex) {
				}
			}
		}

		return null;
	}

	// get album art for specified file
	private static final String sExternalMediaUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString();
	
	private static Bitmap getArtworkFromFile(Context context, long songid, long albumid) {
		Bitmap bm = null;
		byte [] art = null;
		String path = null;

		if (albumid < 0 && songid < 0) {
			throw new IllegalArgumentException("Must specify an album or a song id");
		}

		try {
			if (albumid < 0) {
				Uri uri = Uri.parse("content://media/external/audio/media/" + songid + "/albumart");
				ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
				if (pfd != null) {
					FileDescriptor fd = pfd.getFileDescriptor();
					bm = BitmapFactory.decodeFileDescriptor(fd);
				}
			} else {
				Uri uri = ContentUris.withAppendedId(sArtworkUri, albumid);
				ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
				if (pfd != null) {
					FileDescriptor fd = pfd.getFileDescriptor();
					bm = BitmapFactory.decodeFileDescriptor(fd);
				}
			}
		} catch (FileNotFoundException ex) {
			//
		}
		return bm;
	}

	private static Bitmap getDefaultArtwork(Context context) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		return BitmapFactory.decodeStream(
				context.getResources().openRawResource(R.drawable.albumart_mp_unknown), null, opts);
	}

	static int getIntPref(Context context, String name, int def) {
		SharedPreferences prefs =
			context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		return prefs.getInt(name, def);
	}

	static void setIntPref(Context context, String name, int value) {
		SharedPreferences prefs =
			context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		Editor ed = prefs.edit();
		ed.putInt(name, value);
		ed.commit();
	}

	static int sActiveTabIndex = -1;




	static void setBackground(View v, Bitmap bm) {

		if (bm == null) {
			v.setBackgroundResource(0);
			return;
		}

		int vwidth = v.getWidth();
		int vheight = v.getHeight();
		int bwidth = bm.getWidth();
		int bheight = bm.getHeight();
		float scalex = (float) vwidth / bwidth;
		float scaley = (float) vheight / bheight;
		float scale = Math.max(scalex, scaley) * 1.3f;

		Bitmap.Config config = Bitmap.Config.ARGB_8888;
		Bitmap bg = Bitmap.createBitmap(vwidth, vheight, config);
		Canvas c = new Canvas(bg);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		ColorMatrix greymatrix = new ColorMatrix();
		greymatrix.setSaturation(0);
		ColorMatrix darkmatrix = new ColorMatrix();
		darkmatrix.setScale(.3f, .3f, .3f, 1.0f);
		greymatrix.postConcat(darkmatrix);
		ColorFilter filter = new ColorMatrixColorFilter(greymatrix);
		paint.setColorFilter(filter);
		Matrix matrix = new Matrix();
		matrix.setTranslate(-bwidth/2, -bheight/2); // move bitmap center to origin
		matrix.postRotate(10);
		matrix.postScale(scale, scale);
		matrix.postTranslate(vwidth/2, vheight/2);  // Move bitmap center to view center
		c.drawBitmap(bm, matrix, paint);
		v.setBackgroundDrawable(new BitmapDrawable(bg));
	}

	static int getCardId(Context context) {
		ContentResolver res = context.getContentResolver();
		Cursor c = res.query(Uri.parse("content://media/external/fs_id"), null, null, null, null);
		int id = -1;
		if (c != null) {
			c.moveToFirst();
			id = c.getInt(0);
			c.close();
		}
		return id;
	}

	static class LogEntry {
		Object item;
		long time;

		LogEntry(Object o) {
			item = o;
			time = System.currentTimeMillis();
		}

		void dump(PrintWriter out) {
			sTime.set(time);
			out.print(sTime.toString() + " : ");
			if (item instanceof Exception) {
				((Exception)item).printStackTrace(out);
			} else {
				out.println(item);
			}
		}
	}

	private static LogEntry[] sMusicLog = new LogEntry[100];
	private static int sLogPtr = 0;
	private static Time sTime = new Time();

	static void debugLog(Object o) {

		sMusicLog[sLogPtr] = new LogEntry(o);
		sLogPtr++;
		if (sLogPtr >= sMusicLog.length) {
			sLogPtr = 0;
		}
	}

	static void debugDump(PrintWriter out) {
		for (int i = 0; i < sMusicLog.length; i++) {
			int idx = (sLogPtr + i);
			if (idx >= sMusicLog.length) {
				idx -= sMusicLog.length;
			}
			LogEntry entry = sMusicLog[idx];
			if (entry != null) {
				entry.dump(out);
			}
		}
	}
}




