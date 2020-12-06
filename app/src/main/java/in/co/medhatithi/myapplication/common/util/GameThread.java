package in.co.medhatithi.myapplication.common.util;

import android.os.SystemClock;

public class GameThread extends Thread implements Observable<GameThread.Listener> {

    public interface Listener {
        void updateGame(int delta);
    }

    private Listener mListener;
    private boolean running;
    private long t1, t2;

    @Override
    public void registerListener(Listener listener) {
        mListener = listener;
    }

    @Override
    public void unregisterListener(Listener listener) {
        mListener = null;
    }

    public void setRunning(boolean threadRunning) {
        if (threadRunning) start();
        running = threadRunning;
    }

    @Override
    public void run() {
        t1 = System.currentTimeMillis();
        // keep on firing every 150 milliseconds...
        while (running) {
            SystemClock.sleep(10);
            t2 = System.currentTimeMillis();
            mListener.updateGame((int) (t2 - t1));
            t1 = t2;
        }

    }
}


