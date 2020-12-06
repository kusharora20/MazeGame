package in.co.medhatithi.myapplication.screens.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import in.co.medhatithi.myapplication.common.vo.GamePadView;
import in.co.medhatithi.myapplication.common.vo.maze.MazeDrawable;
import in.co.medhatithi.myapplication.common.vo.maze.MazeSquareMatrix;
import in.co.medhatithi.myapplication.common.vo.maze.Square;
import in.co.medhatithi.myapplication.screens.gamescreen.GameViewMvcImpl;
import in.co.medhatithi.myapplication.screens.toolbar.ToolbarViewMvcImpl;

public class ViewMvcFactory {

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public ViewMvcFactory(LayoutInflater layoutInflater, Context context) {
        mLayoutInflater = layoutInflater;
        mContext = context;
    }

    public ToolbarViewMvcImpl getToolbarViewMvc(@Nullable ViewGroup parent) {
        return new ToolbarViewMvcImpl(mLayoutInflater, parent);
    }

    public GameViewMvcImpl getGameViewMvc(ViewGroup parent, MazeDrawable drawable, int size, Square[][] matrix, int cellLength) {
        return new GameViewMvcImpl(mLayoutInflater, parent, drawable, size, matrix, cellLength, this);
    }

    public GamePadView getGamePadViewMvc(@Nullable ViewGroup parent) {
        return new GamePadView(mLayoutInflater, parent, mContext);
    }
}
