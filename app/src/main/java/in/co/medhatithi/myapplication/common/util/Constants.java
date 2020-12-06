package in.co.medhatithi.myapplication.common.util;

import android.graphics.Color;
import android.graphics.Paint;

public class Constants {

    public static final Paint mMazeBackgroundPaint = new Paint();
    public static final Paint mMazePaint = new Paint();
    public static final float MAZE_PAINT_STROKE_WIDTH = 12f;
    public static final int MAZE_PADDING = 20;
    public static final int ORIENTATION_TOLERANCE = 3;
    public static final int SHRINK_DIAMETER = 20; // additional shrink from perfect fit on the walls;
    // max speed of ball is close to 17, hence the no. 20 (so that in single step, ball cannot jump
    // across to another cell)

    static{
        mMazeBackgroundPaint.setAlpha(10);
        mMazeBackgroundPaint.setStyle(Paint.Style.FILL);
        mMazePaint.setColor(Color.BLUE);
        mMazePaint.setStrokeWidth(MAZE_PAINT_STROKE_WIDTH);
        mMazePaint.setStyle(Paint.Style.STROKE);
    }


}
