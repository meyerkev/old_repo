package com.android.hockeyapp;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.MonthDisplayHelper;
import android.view.View;

public class CalenderView extends View{
	
	private static float mScale = 0; // Used for supporting different screen densities
    private static int WEEK_GAP = 0;
    private static int MONTH_DAY_GAP = 1;
    private static float HOUR_GAP = 0f;
    private static float MIN_EVENT_HEIGHT = 4f;
    private static int MONTH_DAY_TEXT_SIZE = 20;
    private static int WEEK_BANNER_HEIGHT = 17;
    private static int WEEK_TEXT_SIZE = 15;
    private static int WEEK_TEXT_PADDING = 3;
    private static int EVENT_DOT_TOP_MARGIN = 5;
    private static int EVENT_DOT_LEFT_MARGIN = 7;
    private static int EVENT_DOT_W_H = 10;
    private static int EVENT_NUM_DAYS = 31;
    private static int TEXT_TOP_MARGIN = 7;
    private static int BUSY_BITS_WIDTH = 6;
    private static int BUSY_BITS_MARGIN = 4;
    private static int DAY_NUMBER_OFFSET = 10;

    private static int HORIZONTAL_FLING_THRESHOLD = 50;
    MonthDisplayHelper mHelper;
    private static int mCellHeight;
    private static int mCellWidth;
    private static int mBorder;
	
	
	public CalenderView(GameCalendarActivity activity, int month, int year) {
        super(activity);
        mHelper = new MonthDisplayHelper(month, year);
        init(activity);
	}
	
	private void init(GameCalendarActivity activity){
		
	}
	
	private void drawBox(int day, int row, int column, Canvas canvas, Paint p,
            Rect r, boolean isLandscape) {

        int y = WEEK_GAP + row*(WEEK_GAP + mCellHeight);
        int x = mBorder + column*(MONTH_DAY_GAP + mCellWidth);

        r.left = x;
        r.top = y;
        r.right = x + mCellWidth;
        r.bottom = y + mCellHeight;


        // Adjust the left column, right column, and bottom row to leave
        // no border.
        if (column == 0) {
            r.left = -1;
        } else if (column == 6) {
            r.right += mBorder + 2;
        }

        if (row == 5) {
            r.bottom = getMeasuredHeight();
        }


         // Draw the monthDay number
        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias(true);
        p.setTypeface(null);
        p.setTextSize(MONTH_DAY_TEXT_SIZE);
        p.setColor(Color.WHITE);
        /*Drawing of day number is done here
         *easy to find tags draw number draw day*/
        p.setTextAlign(Paint.Align.LEFT);
        // center of text
        int textX = r.left + (r.right - BUSY_BITS_MARGIN - BUSY_BITS_WIDTH - r.left) / 2;
        int textY = (int) (r.top + p.getTextSize() + TEXT_TOP_MARGIN); // bottom of text
        canvas.drawText(String.valueOf(mHelper.getDayAt(row, column)), textX, textY, p);
    }
	
	protected void onDraw(Canvas canvas) {
        drawingCalc(getWidth(), getHeight());
    }
	
	protected void drawingCalc(int width, int height){
		 mCellHeight = (height - (6 * WEEK_GAP)) / 6;
	     mCellWidth = (width - (6 * MONTH_DAY_GAP)) / 7;
	     mBorder = (width - 6 * (mCellWidth + MONTH_DAY_GAP) - mCellWidth) / 2;

			   
	}

    private void doDraw(Canvas canvas) {
        boolean isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        Paint p = new Paint();
        Rect r = new Rect();

        // Get the Julian day for the date at row 0, column 0.
        int day = 0;
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 7; column++) {
                drawBox(day, row, column, canvas, p, r, isLandscape);
                day += 1;
            }
        }

        drawGrid(canvas, p);
    }
    
    private void drawGrid(Canvas canvas, Paint p) {
        p.setColor(Color.YELLOW);
        p.setAntiAlias(false);

        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
       

        for (int row = 0; row < 6; row++) {
            int y = WEEK_GAP + row * (WEEK_GAP + mCellHeight) - 1;
            canvas.drawLine(0, y, width, y, p);
        }
        for (int column = 1; column < 7; column++) {
            int x = mBorder + column * (MONTH_DAY_GAP + mCellWidth) - 1;
            canvas.drawLine(x, WEEK_GAP, x, height, p);
        }
    }
	
	
	
	
}
