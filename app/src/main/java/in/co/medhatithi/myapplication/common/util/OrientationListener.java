package in.co.medhatithi.myapplication.common.util;

import android.content.Context;
import android.util.Log;
import android.view.OrientationEventListener;

public class OrientationListener extends OrientationEventListener implements Observable<OrientationListener.Listener> {

    /**
     * Listen for orientation change
     */
    public interface Listener {
        void onOrientationChanged(int orientation);
    }

    private Listener mListener;
    private static final String TAG = "OrientationListener";

    public OrientationListener(Context context, int rate) {
        super(context, rate);
    }

    @Override
    public void onOrientationChanged(int orientation) {
        mListener.onOrientationChanged(orientation);
//        Log.d(TAG, "onOrientationChanged: " + orientation);
    }

    @Override
    public void registerListener(Listener listener) {
        mListener = listener;
        this.enable();
    }

    @Override
    public void unregisterListener(Listener listener) {
        mListener = null;
        this.disable();
    }


}
