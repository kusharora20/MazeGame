package in.co.medhatithi.myapplication.screens.gamescreen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import in.co.medhatithi.myapplication.common.util.Constants;
import in.co.medhatithi.myapplication.common.util.GameThread;
import in.co.medhatithi.myapplication.common.vo.maze.MazeDrawable;
import in.co.medhatithi.myapplication.common.vo.maze.Square;
import in.co.medhatithi.myapplication.screens.common.BaseFragment;

public class GameFragment extends BaseFragment implements GameThread.Listener{

    private MazeDrawable mazeDrawable;
    private GameViewMvcImpl gameViewMvc;
    private int numberOfRows = 8;
    int gameScreenSize;
    private GameThread mGameThread;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mazeDrawable = getControllerCompositionRoot().getMazeDrawable(numberOfRows, Constants.MAZE_PADDING);
        mGameThread = new GameThread();
        mGameThread.registerListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        gameScreenSize = getControllerCompositionRoot().getDisplayMetrics().widthPixels;
        Square[][] matrix = getControllerCompositionRoot().getSquareMatrix();
        int innerLength = getControllerCompositionRoot().getInnerLength();
        gameViewMvc = getControllerCompositionRoot()
                .getViewMvcFactory()
                .getGameViewMvc(container, mazeDrawable, gameScreenSize, matrix, innerLength);

        return gameViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        gameViewMvc.onStart();
        mGameThread.setRunning(true);
        super.onViewCreated(view, savedInstanceState);
    }

    public void setMazeSize(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    @Override
    public void onStop() {
        super.onStop();
        mGameThread.setRunning(false);
        mGameThread.unregisterListener(this);
        gameViewMvc.onStop();
    }

    @Override
    public void updateGame(int delta) {
        gameViewMvc.runGame();
    }
}
