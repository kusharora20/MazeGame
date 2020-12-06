package in.co.medhatithi.myapplication.common.vo.maze;

import androidx.annotation.NonNull;

public class Square {

    public boolean leftWall, topWall, rightWall, bottomWall, visited;
    public int[] limit = new int[4]; //represents next wall counting from present cell in the four directions

    public Square() {
        this.leftWall = true;
        this.topWall = true;
        this.rightWall = true;
        this.bottomWall = true;
        this.visited = false;

    }

    public boolean hasUncarvedWalls() {
        if(!leftWall || !topWall || !rightWall || !bottomWall) return true;
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return "Walls: " + leftWall + "; " + topWall + "; " + rightWall + "; " + bottomWall;
    }
}