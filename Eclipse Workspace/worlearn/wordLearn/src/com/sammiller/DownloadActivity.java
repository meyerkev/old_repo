package com.sammiller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.SimpleExpandableListAdapter;

public class DownloadActivity extends ExpandableListActivity {
	/** Called when the activity is first created. */

	private static final String NAME = "NAME";
	private static final String IS_EVEN = "IS_EVEN";
	private static final String DOWNLOADED = "DOWNLOADED";
	//private static final String TOTAL = "TOTAL";

	private ExpandableListAdapter mAdapter;

	@Override
	 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        String[] groupNames = {"undergrad tests","grad tests"};
        String[] undergradtests = {"PSAT", "SAT" ,"ACT"};
        String[] gradtests = {"LSAT", "MCAT" ,"GMAT", "GRE"};
        String[][] chi = {undergradtests,gradtests};
        
        
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        for (int i = 0; i < chi.length; i++) {
            Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            curGroupMap.put(NAME, groupNames[i]);
            curGroupMap.put(IS_EVEN, "Test = ??");// (i % 2 == 0) ? "This group is even" : "This group is odd");
            
            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for (int j = 0; j < chi[i].length; j++) {
                Map<String, String> curChildMap = new HashMap<String, String>();
                children.add(curChildMap);
                curChildMap.put(NAME, chi[i][j]);
                //curChildMap.put(IS_EVEN, (j % 2 == 0) ? "Blah, Blah" : "Hah Hah");
                curChildMap.put(DOWNLOADED, "Words Downloaded: "+ getNumDown()+"/"+getNumTotal()
                		);
                //curChildMap.put(TOTAL, getNumTotal()+"");
            }
            childData.add(children);
        }
        
        // Set up our adapter
        mAdapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { NAME, IS_EVEN },
                new int[] { android.R.id.text1, android.R.id.text2 },
                childData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] { NAME, DOWNLOADED},
                new int[] { android.R.id.text1, android.R.id.text2 }
                );
        setListAdapter(mAdapter);
    }
	
	public int getNumTotal(){
		return 50;
	}
	
	public int getNumDown(){
		return 10;
		
	}

}