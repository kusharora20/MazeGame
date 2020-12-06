package in.co.medhatithi.myapplication.screens.gamescreen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import in.co.medhatithi.myapplication.R;
import in.co.medhatithi.myapplication.common.util.GameThread;
import in.co.medhatithi.myapplication.common.util.Util;
import in.co.medhatithi.myapplication.common.vo.GamePadView;
import in.co.medhatithi.myapplication.common.vo.maze.Square;

import static in.co.medhatithi.myapplication.common.util.Constants.MAZE_PADDING;
import static in.co.medhatithi.myapplication.common.util.Constants.MAZE_PAINT_STROKE_WIDTH;
import static in.co.medhatithi.myapplication.common.util.Constants.SHRINK_DIAMETER;

public class CustomMazeView extends View implements GamePadView.GestureListener {

    private float radius, posX, posY, initialPos, innerLength, leftStart, cellLength, initialYoffset;
    private Paint mBallPaint;
    private static final String TAG = "CustomMazeView";
    private Square[][] mMatrix;
    private MOVEMENT_STATE movementState = MOVEMENT_STATE.PAUSE;
    private int shift;

    private enum MOVEMENT_STATE {
        RIGHT, LEFT, BOTTOM, UP, PAUSE;
    }

    @Override
    public void onSwipeRight() {
        movementState = MOVEMENT_STATE.RIGHT;
    }

    @Override
    public void onSwipeLeft() {
        movementState = MOVEMENT_STATE.LEFT;
    }

    @Override
    public void onSwipeUp() {
        movementState = MOVEMENT_STATE.UP;
    }

    @Override
    public void onSwipeBottom() {
        movementState = MOVEMENT_STATE.BOTTOM;
    }

    @Override
    public void onTouch() {
        movementState = MOVEMENT_STATE.PAUSE;
    }

    enum DIRECTION {LEFT, RIGHT, DOWN, UP, NONE}

    public void passInfoInnerLength(int innerLength) {
        this.innerLength = innerLength;
        radius = (innerLength - SHRINK_DIAMETER) / 2f;
        leftStart = MAZE_PAINT_STROKE_WIDTH + MAZE_PADDING;
        initialPos = (radius + leftStart);
        posX = initialPos;
        initialYoffset = innerLength - (2 * radius);
        posY = initialPos + initialYoffset;

        Log.d(TAG, "innerLength: " + innerLength + "; initialPos: " + initialPos
                + "; leftStart: " + leftStart + "; radius: " + radius);
    }

    public void passMatrixInfo(Square[][] matrix) {
        this.mMatrix = matrix;
//        Util.printMatrixToConsole(matrix);

        cellLength = (MAZE_PAINT_STROKE_WIDTH + (MAZE_PAINT_STROKE_WIDTH + innerLength) * matrix.length)
                / matrix.length;
        Log.d(TAG, "cellLength: " + cellLength + "; matrix.length: " + matrix.length);
        Log.d(TAG, "initialPos: " + initialPos + "; leftStart: " + leftStart +
                "; radius: " + radius);
    }

    public CustomMazeView(Context context) {
        super(context);
        init(context, null, 0);

    }

    public CustomMazeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);

    }

    public CustomMazeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        mBallPaint = new Paint();
        mBallPaint.setColor(Util.getColor(context, R.color.ballColor));
        mBallPaint.setStyle(Paint.Style.FILL);
        shift = SHRINK_DIAMETER / 4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(posX, posY, radius, mBallPaint);

        Log.d(TAG, "posX; posY: " + posX + "; " + posY);
    }

    private int[] getIJ(float posX, float posY) {
        int[] array = new int[2];
        array[0] = (int) ((posX - leftStart) / cellLength);
        array[1] = (int) ((posY - leftStart) / cellLength);
        Log.d(TAG, "getIJ: " + array[0] + "; " + array[1]
                + "; " + mMatrix[array[0]][array[1]]);
        return array;
    }

    public void runGame() {
        float temp;
        int i, j;
        int[] array;
        switch (movementState) {
            case RIGHT:
                temp = posX;
                temp += shift;
                array = getIJ(posX, posY);
                i = array[0];
                j = array[1];
                if (!mMatrix[i][j].rightWall ||
                        (temp + radius) <= (leftStart + ((innerLength * (i + 1)) + (MAZE_PAINT_STROKE_WIDTH * i)))) {
                    if (posY - radius >= leftStart + (innerLength + MAZE_PAINT_STROKE_WIDTH) * j &&
                            posY + radius <= leftStart + innerLength * (j + 1) + MAZE_PAINT_STROKE_WIDTH * j) {
                        posX = temp;
                    }
                }

                invalidate();
                Log.d(TAG, "i: " + i);
                break;

            case LEFT:
                temp = posX;
                temp -= shift;
                array = getIJ(posX, posY);
                i = array[0];
                j = array[1];
                if (!mMatrix[i][j].leftWall ||
                        (temp - radius) >= (leftStart + ((innerLength * i) + (MAZE_PAINT_STROKE_WIDTH * i))))
                    if (posY - radius >= leftStart + (innerLength + MAZE_PAINT_STROKE_WIDTH) * j &&
                            posY + radius <= leftStart + innerLength * (j + 1) + MAZE_PAINT_STROKE_WIDTH * j)
                        posX = temp;
                invalidate();
                break;

            case BOTTOM:
                temp = posY;
                temp += shift;
                array = getIJ(posX, posY);
                i = array[0];
                j = array[1];
                if (!mMatrix[i][j].bottomWall ||
                        (temp + radius) <= (leftStart + (innerLength * (j + 1) + (MAZE_PAINT_STROKE_WIDTH * j))))
                    if (posX - radius >= leftStart + (innerLength + MAZE_PAINT_STROKE_WIDTH) * i &&
                            posX + radius <= leftStart + innerLength * (i + 1) + MAZE_PAINT_STROKE_WIDTH * i)
                        posY = temp;
                invalidate();
                break;

            case UP:
                temp = posY;
                temp -= shift;
                array = getIJ(posX, posY);
                i = array[0];
                j = array[1];
                if (!mMatrix[i][j].topWall ||
                        (temp - radius) >= (leftStart + (innerLength * j) + (MAZE_PAINT_STROKE_WIDTH * j)))
                    if (posX - radius >= leftStart + (innerLength + MAZE_PAINT_STROKE_WIDTH) * i &&
                            posX + radius <= leftStart + innerLength * (i + 1) + MAZE_PAINT_STROKE_WIDTH * i)
                    posY = temp;
                invalidate();
                break;

            case PAUSE:
                // do nothing
                break;
        }
    }
}