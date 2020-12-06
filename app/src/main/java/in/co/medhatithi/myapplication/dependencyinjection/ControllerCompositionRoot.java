package in.co.medhatithi.myapplication.dependencyinjection;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.provider.SyncStateContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.Px;
import androidx.fragment.app.FragmentActivity;

import in.co.medhatithi.myapplication.common.util.Constants;

import in.co.medhatithi.myapplication.common.util.Util;
import in.co.medhatithi.myapplication.common.vo.maze.Maze;
import in.co.medhatithi.myapplication.common.vo.maze.MazeDrawable;
import in.co.medhatithi.myapplication.common.vo.maze.MazeSquareMatrix;
import in.co.medhatithi.myapplication.common.vo.maze.Square;
import in.co.medhatithi.myapplication.screens.common.ViewMvcFactory;

public class ControllerCompositionRoot {

    private static final String TAG = "ControllerCompositionRoot";
    private LayoutInflater mLayoutInflater;
    private ViewMvcFactory mViewMvcFactory;
    private Context mContext;
    private float innerLength;
    private Square[][] matrix;

    public ControllerCompositionRoot(LayoutInflater layoutInflater, FragmentActivity context) {
        mLayoutInflater = layoutInflater;
        mContext = context;
    }

    public ViewMvcFactory getViewMvcFactory() {
        return (mViewMvcFactory == null)
                ? mViewMvcFactory = new ViewMvcFactory(getLayoutInflater(), mContext)
                : mViewMvcFactory;
    }

    private LayoutInflater getLayoutInflater() {
        return (mLayoutInflater == null)
                ? mLayoutInflater = LayoutInflater.from(getContext())
                : mLayoutInflater;
    }

    private Context getContext() {
        return mContext;
    }

    public MazeDrawable getMazeDrawable(int numberOfRows, @Px int padding) {
        int orientation = getContext().getResources().getConfiguration().orientation;
        int gameScreenSize;
        Rect mazeBoundaryRect = new Rect();

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            gameScreenSize = getDisplayMetrics().widthPixels;
            mazeBoundaryRect = new Rect(
                    padding,
                    padding,
                    gameScreenSize - padding - (int) Constants.MAZE_PAINT_STROKE_WIDTH,
                    gameScreenSize - padding);
        } else {
            // if landscape
        }
        float cellLength = mazeBoundaryRect.width() / numberOfRows;
        innerLength = ((mazeBoundaryRect.width() - Constants.MAZE_PAINT_STROKE_WIDTH) / numberOfRows)
                - Constants.MAZE_PAINT_STROKE_WIDTH;
        Log.d(TAG, "mazeBoundaryRect.width(): " + mazeBoundaryRect.width());
        Maze maze = new Maze(numberOfRows, false);
        matrix = maze.getSquareMatrix();
        MazeSquareMatrix mMazeSquareMatrix = new MazeSquareMatrix(
                mazeBoundaryRect,
                numberOfRows,
                matrix,
                cellLength);
        Util.printMatrixToConsole(matrix);
        return new MazeDrawable(mMazeSquareMatrix.getPointsForMaze(), mazeBoundaryRect);

    }

    public int getInnerLength() {
        return (int) innerLength;
    }

    public Square[][] getSquareMatrix() {
        return matrix;
    }

    public DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        FragmentActivity activity = (FragmentActivity) (getContext());
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

}
