package in.co.medhatithi.myapplication.common.vo.maze;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class generates a maze by Recursive Backtracking algorithm given below.
 * Call {@link Maze#getSquareMatrix()} method to get square matrix object which encapsulates the maze
 * <p>
 * Recursive Backtracking Algorithm:
 * <p>
 * Choose a starting point in the field.
 * Randomly choose a wall at that point and carve a passage through to the adjacent cell,
 * but only if the adjacent cell has not been visited yet. This becomes the new current cell.
 * If all adjacent cells have been visited, back up to the last cell that has uncarved walls and repeat.
 * The algorithm ends when the process has backed all the way up to the starting point.
 */

public class Maze {

    private int mMazeDimension;
    private boolean isComplex;
    private Square[][] mSquareMatrix;
    private List<Point> backstack;
    private static final String TAG = "Maze";
    private Random random;

    public Maze(int mazeDimension, boolean isComplex) {
        Log.d(TAG, "Maze: constructor called");
        this.isComplex = isComplex;
        this.mMazeDimension = mazeDimension;
        this.backstack = new ArrayList<>();
        random = new Random();
        this.mSquareMatrix = generateMazeMatrix(mMazeDimension, isComplex);
    }

    public Square[][] getSquareMatrix() {
        return mSquareMatrix;
    }

    /*-------------------------------------------------------------------------------------*/
    // Algorithm starts here

    /**
     * @param mazeDimension the dimension of the Maze to be generated
     * @param isComplex     complexity of the maze (to be made use of in later versions of apk)
     * @return the generated Matrix.
     */
    private Square[][] generateMazeMatrix(int mazeDimension, boolean isComplex) {
        this.mSquareMatrix = new Square[mazeDimension][mazeDimension];
        for (int i = 0, j; i < mazeDimension; i++) // initialize new Square object
            for (j = 0; j < mazeDimension; j++) {
                mSquareMatrix[i][j] = new Square();
            }
        Point currentPoint = new Point(0, 0);
        backstack.add(currentPoint);
        mSquareMatrix[currentPoint.x][currentPoint.y].visited = true;

        do currentPoint = randomTraverse(currentPoint);
        while (!(currentPoint.x == 0 && currentPoint.y == 0));
        addLimitsInfoToMatrix();
//        print(mSquareMatrix, mazeDimension);
        return mSquareMatrix;
    }

    private void addLimitsInfoToMatrix() {

        // left wall limit...
        for (int i = 0, j, k; i < mMazeDimension; i++)
            for (j = 0; j < mMazeDimension; j++)
                for (k = j; k >= 0; k--) {
                    if (mSquareMatrix[k][i].leftWall) {
                        mSquareMatrix[i][j].limit[0] = k;
                        break;
                    }
                }

        // right wall limit...
        for (int i = 0, j, k; i < mMazeDimension; i++)
            for (j = 0; j < mMazeDimension; j++)
                for (k = j; k < mMazeDimension; k++) {
                    if (mSquareMatrix[k][i].rightWall) {
                        mSquareMatrix[i][j].limit[2] = k;
                        break;
                    }
                }

        // top wall limit...
        for (int i = 0, j, k; i < mMazeDimension; i++)
            for (j = 0; j < mMazeDimension; j++)
                for (k = i; k >= 0; k--) {
                    if (mSquareMatrix[j][k].topWall) {
                        mSquareMatrix[i][j].limit[1] = k;
                        break;
                    }
                }

        // bottom wall limit...
        for (int i = 0, j, k; i < mMazeDimension; i++)
            for (j = 0; j < mMazeDimension; j++)
                for (k = i; k < mMazeDimension; k++) {
                    if (mSquareMatrix[j][k].bottomWall) {
                        mSquareMatrix[i][j].limit[3] = k;
                        break;
                    }
                }

    }

    /**
     * Randomly traverse through till a dead-end is reached
     *
     * @param currentPoint the starting point
     * @return {@link #getLastUncarvedPoint(List)} of the current backstack/ traversal
     * @see {@link #nextRandomPoint(Point)} method which is called in here, it not only
     * returns nextRandomPoint but also carves the path by breaking the wall between
     * currentCell and nextRandomCell
     */
    private Point randomTraverse(Point currentPoint) {
        while (allAdjacentCellsNotVisited(currentPoint.x, currentPoint.y)) {
            currentPoint = nextRandomPoint(currentPoint);
            backstack.add(currentPoint);
        }
        return getLastUncarvedPoint(backstack);
    }

    // returns the last uncarved point of the current backstack
    // (last uncarved point is the point in the current backstack which has adjacent unvisited cell(s) )
    private Point getLastUncarvedPoint(List<Point> backstack) {
        Point lastUncarvedPoint = new Point();
        int x, y;
        for (int i = backstack.size() - 1; i >= 0; i--) {
            x = backstack.get(i).x;
            y = backstack.get(i).y;

            if (allAdjacentCellsNotVisited(x, y)) { // checking if the point has any adjacent unvisited cells
                lastUncarvedPoint.set(x, y);
                break;
            }
            backstack.remove(i);
        }
        return lastUncarvedPoint;
    }

    /**
     * @param currentPoint the currentPoint i.e. the starting point of the traversal/ backstack (array)
     * @return next Random point by checking that adjacent cell is not visited,
     * is through an uncarved wall and is legitimate cell (lies inside maze)
     * Also removes the wall in between of currentcell which is common to nextRandomCell
     * and marks the nextRandomCell as visited
     */
    private Point nextRandomPoint(Point currentPoint) {
        //return new random point
        Point nextRandomPoint = new Point();
        int x, y;
        x = currentPoint.x;
        y = currentPoint.y;
        int choice = random.nextInt(4) + 1; // randomize the choosing of the walls...

        switch (choice) {
            case 1: // left wall preferred
                if (wayThroughLeftWallPossible(currentPoint)) {
                    nextRandomPoint.set(x - 1, y);
                    break;
                } else if (wayThroughRightWallPossible(currentPoint)) {
                    nextRandomPoint.set(x + 1, y);
                    break;
                } else if (wayThroughTopWallPossible(currentPoint)) {
                    nextRandomPoint.set(x, y - 1);
                    break;
                } else if (wayThroughBottomWallPossible(currentPoint)) {
                    nextRandomPoint.set(x, y + 1);
                    break;
                }
            case 2: // top Wall preferred
                if (wayThroughTopWallPossible(currentPoint)) {
                    nextRandomPoint.set(x, y - 1);
                    break;
                } else if (wayThroughLeftWallPossible(currentPoint)) {
                    nextRandomPoint.set(x - 1, y);
                    break;
                } else if (wayThroughRightWallPossible(currentPoint)) {
                    nextRandomPoint.set(x + 1, y);
                    break;
                } else if (wayThroughBottomWallPossible(currentPoint)) {
                    nextRandomPoint.set(x, y + 1);
                    break;
                }
            case 3: // right wall preferred
                if (wayThroughRightWallPossible(currentPoint)) {
                    nextRandomPoint.set(x + 1, y);
                    break;
                } else if (wayThroughLeftWallPossible(currentPoint)) {
                    nextRandomPoint.set(x - 1, y);
                    break;
                } else if (wayThroughTopWallPossible(currentPoint)) {
                    nextRandomPoint.set(x, y - 1);
                    break;
                } else if (wayThroughBottomWallPossible(currentPoint)) {
                    nextRandomPoint.set(x, y + 1);
                    break;
                }
            case 4: // bottom wall preferred
                if (wayThroughBottomWallPossible(currentPoint)) {
                    nextRandomPoint.set(x, y + 1);
                    break;
                } else if (wayThroughLeftWallPossible(currentPoint)) {
                    nextRandomPoint.set(x - 1, y);
                    break;
                } else if (wayThroughRightWallPossible(currentPoint)) {
                    nextRandomPoint.set(x + 1, y);
                    break;
                } else if (wayThroughTopWallPossible(currentPoint)) {
                    nextRandomPoint.set(x, y - 1);
                    break;
                }
        }
        return nextRandomPoint;
    }

    private boolean wayThroughLeftWallPossible(Point currentPoint) {
        int x = currentPoint.x;
        int y = currentPoint.y;
        if (mSquareMatrix[x][y].leftWall && x - 1 >= 0 && !mSquareMatrix[x - 1][y].visited) {
            mSquareMatrix[x][y].leftWall = false;
            mSquareMatrix[x - 1][y].rightWall = false;
            mSquareMatrix[x - 1][y].visited = true;

            return true;
        }
        return false;
    }

    private boolean wayThroughTopWallPossible(Point currentPoint) {
        int x = currentPoint.x;
        int y = currentPoint.y;
        if (mSquareMatrix[x][y].topWall && y - 1 >= 0 && !mSquareMatrix[x][y - 1].visited) {
            mSquareMatrix[x][y].topWall = false;
            mSquareMatrix[x][y - 1].bottomWall = false;
            mSquareMatrix[x][y - 1].visited = true;

            return true;
        }
        return false;
    }

    private boolean wayThroughRightWallPossible(Point currentPoint) {
        int x = currentPoint.x;
        int y = currentPoint.y;
        if (mSquareMatrix[x][y].rightWall && x + 1 < mMazeDimension && !mSquareMatrix[x + 1][y].visited) {
            mSquareMatrix[x][y].rightWall = false;
            mSquareMatrix[x + 1][y].leftWall = false;
            mSquareMatrix[x + 1][y].visited = true;

            return true;
        }
        return false;
    }

    private boolean wayThroughBottomWallPossible(Point currentPoint) {
        int x = currentPoint.x;
        int y = currentPoint.y;
        if (mSquareMatrix[x][y].bottomWall && y + 1 < mMazeDimension && !mSquareMatrix[x][y + 1].visited) {
            mSquareMatrix[x][y].bottomWall = false;
            mSquareMatrix[x][y + 1].topWall = false;
            mSquareMatrix[x][y + 1].visited = true;

            return true;
        }
        return false;
    }

    // returns true if all adjacent cells have been visited,
    // returns false if there is any unvisited cell in neighbourhood
    private boolean allAdjacentCellsNotVisited(int x, int y) {

        boolean visited = false;
        if (x - 1 >= 0 && !mSquareMatrix[x - 1][y].visited) visited = true; // upper cell visited
        if (y - 1 >= 0 && !mSquareMatrix[x][y - 1].visited) visited = true; // left cell visited
        if (x + 1 < mMazeDimension && !mSquareMatrix[x + 1][y].visited)
            visited = true; // lower cell visited
        if (y + 1 < mMazeDimension && !mSquareMatrix[x][y + 1].visited)
            visited = true; // right cell visited

        return visited;
    }

    // Algorithm ends here
    /*-------------------------------------------------------------------------------------*/


    private void print(Square[][] squareMatrix, int mazeDimension) {
        for (int i = 0, j; i < mazeDimension; i++) // initialize new Square object
            for (j = 0; j < mazeDimension; j++) {
                System.out.print("(" + i + "," + j + "): " + squareMatrix[i][j].leftWall + "; " + squareMatrix[i][j].topWall +
                        "; " + squareMatrix[i][j].rightWall + "; " + squareMatrix[i][j].bottomWall
                        + "\n");
            }
    }

}