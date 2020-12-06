package in.co.medhatithi.myapplication.screens.gamescreen;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import in.co.medhatithi.myapplication.R;
import in.co.medhatithi.myapplication.common.vo.GamePadView;
import in.co.medhatithi.myapplication.common.vo.maze.MazeDrawable;
import in.co.medhatithi.myapplication.common.vo.maze.Square;
import in.co.medhatithi.myapplication.screens.common.BaseViewMvc;
import in.co.medhatithi.myapplication.screens.common.ViewMvcFactory;

public class GameViewMvcImpl extends BaseViewMvc<ConstraintLayout> implements GamePadView.GestureListener{

    private final CustomMazeView mCustomMazeView;
    private final GamePadView mGamePadView;
    
    public GameViewMvcImpl(LayoutInflater inflater, @Nullable ViewGroup parent,
                           MazeDrawable mazeDrawable, int size, Square[][] matrix, int innerLength, ViewMvcFactory viewMvcFactory) {
        setRootView((ConstraintLayout) inflater.inflate(R.layout.layout_game, parent, false));
        mCustomMazeView = findViewById(R.id.image_game);
        mCustomMazeView.passInfoInnerLength(innerLength);
        mCustomMazeView.passMatrixInfo(matrix);
        mCustomMazeView.setLayoutParams(new ConstraintLayout.LayoutParams(size, size));
        mCustomMazeView.setBackground(mazeDrawable);

        FrameLayout gamePadFrame = findViewById(R.id.frame_game_pad);
        mGamePadView = viewMvcFactory.getGamePadViewMvc(gamePadFrame);
        gamePadFrame.addView(mGamePadView.getRootView());
    }

    public void onStop() {
        mCustomMazeView.onTouch();
        mGamePadView.unregisterListener(this);
    }

    @Override
    public void onSwipeRight() {
        mCustomMazeView.onSwipeRight();
    }

    @Override
    public void onSwipeLeft() {
        mCustomMazeView.onSwipeLeft();
    }

    @Override
    public void onSwipeUp() {
        mCustomMazeView.onSwipeUp();
    }

    @Override
    public void onSwipeBottom() {
        mCustomMazeView.onSwipeBottom();
    }

    @Override
    public void onTouch() {
        mCustomMazeView.onTouch();
    }

    public void onStart(){
        mGamePadView.registerListener(this);
    }

    public void runGame(){
        mCustomMazeView.runGame();
    }

}
