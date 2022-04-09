package com.android.kevinsalarmclock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ExpandableListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;




public class MyMediaStore extends ExpandableListActivity{
	
//	private class Artist{
//		private String name;
//		ArrayList<Album> albums = new ArrayList<Album>();
//		
//		public String getName(){
//			return name;
//		}
//		public void setName(String s){
//			name = s;
//		}
//		
//		public ArrayList<Album> getAlbums(){
//			return albums;
//		}
//			
//	}
//
//	private class Album{
//		private String name;
//		ArrayList<SimpleSong> songs = new ArrayList<SimpleSong>();
//		
//		public String getName(){
//			return name;
//		}
//		public void setName(String s){
//			name = s;
//		}
//		
//		public ArrayList<SimpleSong> getSongs(){
//			return songs;
//		}
//	}
//	
//	
//	String NAME = "NAME";
//	String IS_EVEN = "IS_EVEN";
//	
//	ExpandableListAdapter mAdapter;
//	ArrayList<Artist> music;
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		//setContentView(R.layout.musiclist);
//		// Set up our adapter
//		List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
//		List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
//		
//		for (int i = 0; i < music.size(); i++) {
//            Map<String, String> curGroupMap = new HashMap<String, String>();
//            groupData.add(curGroupMap);
//            curGroupMap.put(NAME, music.get(i).getName());
//            curGroupMap.put(IS_EVEN, (i % 2 == 0) ? "This group is even" : "This group is odd");
//
//            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
//            for (int j = 0; j < music.get(i).getAlbums().size(); j++) {
//                Map<String, String> curChildMap = new HashMap<String, String>();
//                children.add(curChildMap);
//                curChildMap.put(NAME, music.get(i).getAlbums().get(j).getName());
//                curChildMap.put(IS_EVEN, (j % 2 == 0) ? "This child is even" : "This child is odd");
//            }
//            childData.add(children);
//        }
//		
//		
//		
//		
//		
//		
//		mAdapter = new SimpleExpandableListAdapter(
//				this,
//				groupData,
//				android.R.layout.simple_expandable_list_item_1,
//				new String[] { NAME, IS_EVEN },
//				new int[] { android.R.id.text1, android.R.id.text2 },
//				childData,
//				android.R.layout.simple_expandable_list_item_2,
//				new String[] { NAME, IS_EVEN },
//				new int[] { android.R.id.text1, android.R.id.text2 }
//		);
//		fillMusic();
//		//mAdapter = new MyMedia();
//		setListAdapter(mAdapter);
//		registerForContextMenu(getExpandableListView());
//		finish();
//	}
//	//MediaStore - ArrayList of Artists:select one
//	//Move to Artists Albums
//	//Move to Album - Select Song - exit all three
//
//	public void fillMusic(){
//		music.clear();
//		ContentResolver resolver = getBaseContext().getContentResolver();
//		 
//        
//		 
//        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String [] {"ARTIST"}, null, null, null);
//
//
//
//        while(cursor.moveToNext()) {
//
//                Log.d("TEST", cursor.getString(0));
//
//        }
//		/*
//		for(artists){
//			Artist a = new Artist();
//			a.setName(artistName);
//			music.add(a);
//			for(this artists albums)
//				Album al = new Album();
//				al.setName(albumName);
//				music.get(currentIndex).getAlbums().add(al);
//				for(){
//					
//					
//				}
//			}
//		}*/
//		
//
//	}
//
//	public boolean onContextItemSelected(MenuItem item) {
//		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();
//
//		String title = ((TextView) info.targetView).getText().toString();
//
//		int type = ExpandableListView.getPackedPositionType(info.packedPosition);
//		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
//			int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
//			int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition); 
//			Toast.makeText(this, title + ": Child " + childPos + " clicked in group " + groupPos,
//					Toast.LENGTH_SHORT).show();
//			return true;
//		} else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
//			int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
//			Toast.makeText(this, title + ": Group " + groupPos + " clicked", Toast.LENGTH_SHORT).show();
//			return true;
//		}
//
//		return false;
//	}
//	/*
//	public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
//        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
//        for (int i = 0; i < 20; i++) {
//            Map<String, String> curGroupMap = new HashMap<String, String>();
//            groupData.add(curGroupMap);
//            curGroupMap.put(NAME, "Group " + i);
//            curGroupMap.put(IS_EVEN, (i % 2 == 0) ? "This group is even" : "This group is odd");
//
//            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
//            for (int j = 0; j < 15; j++) {
//                Map<String, String> curChildMap = new HashMap<String, String>();
//                children.add(curChildMap);
//                curChildMap.put(NAME, "Child " + j);
//                curChildMap.put(IS_EVEN, (j % 2 == 0) ? "This child is even" : "This child is odd");
//            }
//            childData.add(children);
//        }
//
//        // Set up our adapter
//        mAdapter = new SimpleExpandableListAdapter(
//                this,
//                groupData,
//                android.R.layout.simple_expandable_list_item_1,
//                new String[] { NAME, IS_EVEN },
//                new int[] { android.R.id.text1, android.R.id.text2 },
//                childData,
//                android.R.layout.simple_expandable_list_item_2,
//                new String[] { NAME, IS_EVEN },
//                new int[] { android.R.id.text1, android.R.id.text2 }
//                );
//        setListAdapter(mAdapter);
//    }
//    //*/

}
