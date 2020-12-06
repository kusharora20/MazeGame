package in.co.medhatithi.myapplication.common.vo.maze;

import android.graphics.Rect;
import java.util.ArrayList;
import in.co.medhatithi.myapplication.common.util.Constants;

public class MazeSquareMatrix {

    private ArrayList<Float> points;
    private int mMazeDimension;
    private Square[][] mSquareMatrix;
    private float cellLength, startPointX, startPointY;

    public MazeSquareMatrix(Rect mazeBoundaryRect, int mazeDimension, Square[][] squareMatrix, float cellLength) {
        mMazeDimension = mazeDimension;
        mSquareMatrix = squareMatrix;
        this.cellLength = cellLength;
        startPointX = mazeBoundaryRect.left + Constants.MAZE_PAINT_STROKE_WIDTH/2; // define the startPoint as per the padding of the MazeDrawable
        startPointY = mazeBoundaryRect.top + Constants.MAZE_PAINT_STROKE_WIDTH/2;
//        this.cellLength -= Constants.MAZE_PAINT_STROKE_WIDTH/4;
    }

    public float[] getPointsForMaze() {
        points = new ArrayList<>();

        Float pointX = 0f, pointY = 0f;
        for (int i = 0, j; i < mMazeDimension; i++)
            for (j = 0; j < mMazeDimension; j++) {

                if (mSquareMatrix[i][j].leftWall) {
                    pointX = startPointX + i * cellLength;
                    pointY = startPointY + j * cellLength;
                    addPoints(pointX, pointY);

                    pointY += (cellLength);
                    addPoints(pointX, pointY);
                }

                if (mSquareMatrix[i][j].topWall) {
                    pointX = startPointX + i * cellLength;
                    pointY = startPointY + j * cellLength;
                    addPoints(pointX, pointY);

                    pointX += (cellLength);
                    addPoints(pointX, pointY);
                }

                if (mSquareMatrix[i][j].rightWall) {
                    pointX = startPointX + (i + 1) * cellLength;
                    pointY = startPointY + j * cellLength;
                    addPoints(pointX, pointY);

                    pointY += (cellLength);
                    addPoints(pointX, pointY);
                }

                if (mSquareMatrix[i][j].bottomWall) {
                    pointX = startPointX + (i) * cellLength;
                    pointY = startPointY + (j + 1) * cellLength;
                    addPoints(pointX, pointY);

                    pointX += (cellLength);
                    addPoints(pointX, pointY);
                }
            }

        float[] pointsArray = new float[points.size()];
        for (int i = 0; i < points.size(); i++) pointsArray[i] = points.get(i);
        return pointsArray;
    }

    /**
     * this is a utility method which adds the points below, which define a length of cell {@link #cellLength}
     *
     * @param pointX point (x1, y1)
     * @param pointY point (x2, y2)
     */
    private void addPoints(Float pointX, Float pointY) {
        points.add(pointX);
        points.add(pointY);
    }


}
