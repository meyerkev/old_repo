package com.sammiller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryView extends Activity{
	ImageView imageView;
	
	private Integer[] pics;
    
    private Integer[] NoteCards = {
            R.drawable.sample_2s,
            R.drawable.sample_3s,
            R.drawable.sample_4s,
            R.drawable.sample_5s,
            R.drawable.sample_6s,
            R.drawable.sample_7s,
            R.drawable.sample_8s,
            R.drawable.sample_2i,
            R.drawable.sample_3i,
            R.drawable.sample_4i,
            R.drawable.sample_5i,
            R.drawable.sample_6i,
            R.drawable.sample_7i,
            R.drawable.sample_8,
            R.drawable.sample_2,
            R.drawable.sample_3,
            R.drawable.sample_4,
            R.drawable.sample_5,
            R.drawable.sample_6,
            R.drawable.sample_7
    };
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.gallery);
	    
	    Intent myIntent = getIntent();
        final int level = myIntent.getIntExtra("LEVEL",1);

	    Gallery gallery = (Gallery) findViewById(R.id.gallery);
	    gallery.setAdapter(new ImageAdapter(this, level));
	    
	    imageView = (ImageView)findViewById(R.id.image);
	    imageView.setImageResource(pics[0]);

	    gallery.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	imageView.setImageResource(pics[position]);
	            //Toast.makeText(SamsApp, "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
	}
	
	public class ImageAdapter extends BaseAdapter {
    	private Context ctx;
    	int imageBackground;
    	
    	public ImageAdapter(Context c, int request) {
			ctx = c;
			
			Log.d("Gallery", "Gallery choice: "+request);
	        
	        pics=NoteCards;
			
			TypedArray ta = obtainStyledAttributes(R.styleable.Gallery1);
			imageBackground = ta.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
			ta.recycle();
		}

		@Override
    	public int getCount() {
    		
    		return pics.length;
    	}

    	@Override
    	public Object getItem(int arg0) {
    		
    		return arg0;
    	}

    	@Override
    	public long getItemId(int arg0) {
    		
    		return arg0;
    	}

    	@Override
    	public View getView(int arg0, View arg1, ViewGroup arg2) {
    		ImageView iv = new ImageView(ctx);
    		iv.setImageResource(pics[arg0]);
    		iv.setScaleType(ImageView.ScaleType.FIT_XY);
    		iv.setLayoutParams(new Gallery.LayoutParams(150,120));
    		iv.setBackgroundResource(imageBackground);
    		return iv;
    	}

    }
}