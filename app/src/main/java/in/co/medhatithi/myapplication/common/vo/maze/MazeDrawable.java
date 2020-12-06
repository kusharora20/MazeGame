package in.co.medhatithi.myapplication.common.vo.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import in.co.medhatithi.myapplication.common.util.Constants;
import in.co.medhatithi.myapplication.common.util.Observable;

public class MazeDrawable extends Drawable implements Observable<MazeDrawable.Listener> {

    private Rect mazeBoundaryRect;
    private static final String TAG = "MazeDrawable";
    private boolean firstTimeDrawCalled = true;
    private Listener mListener;
    private float[] mazePoints;

    public interface Listener {
        void onDrawingMazeComplete();
    }

    @Override
    public void registerListener(Listener listener) {
        mListener = listener;
    }

    @Override
    public void unregisterListener(Listener listener) {
        mListener = null;
    }

    // TODO: uncomment code lines and make it work
    public MazeDrawable(float[] pointsForMaze, Rect mazeBoundaryRect) {
        mazePoints = pointsForMaze;
        this.mazeBoundaryRect = mazeBoundaryRect;

        init();
    }

    private void init() {
        setBounds(mazeBoundaryRect);
        Log.d(TAG, "init: called");
    }

    @Override
    public void draw(Canvas canvas) {
        // draw Maze...
//        canvas.drawRect(mazeBoundaryRect, Constants.mMazePaint); // drawing outermost enclosing rectangle
        canvas.drawLines(mazePoints, Constants.mMazePaint);
        canvas.drawLines(mazePoints, new Paint(Color.RED));

        while (firstTimeDrawCalled) {
            if(mListener!=null) mListener.onDrawingMazeComplete();
            firstTimeDrawCalled = false;
        }
    }

    @Override
    public void setAlpha(int i) {
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
    }

    /**
     * @deprecated
     */
    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

}
