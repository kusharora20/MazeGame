package in.co.medhatithi.myapplication.common.util;

import android.content.Context;
import android.os.Build;
import in.co.medhatithi.myapplication.common.vo.maze.Square;

public class Util {

    public static int getColor(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return context.getColor(id);
        else return context.getResources().getColor(id);
    }

    public static void printMatrixToConsole(Square[][] matrix) {
        int rows = matrix.length, i, j;
        for (i = 0; i < rows; i++) {
            for (j = 0; j < rows; j++) {
                System.out.println(i + ", " + j + ": " + matrix[j][i].leftWall + "; "
                        + matrix[j][i].topWall + "; " + matrix[j][i].rightWall + "; "
                        + matrix[j][i].bottomWall);
            }

        }
    }

    // Math.Sin approximation as per Bhaskara
    public static float sin(float degree) {
        return (4 * degree) * (180 - degree) / (40500 - degree * (180 - degree));
    }


}
