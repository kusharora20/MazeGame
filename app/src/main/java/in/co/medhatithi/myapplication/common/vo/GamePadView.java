package in.co.medhatithi.myapplication.common.vo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import in.co.medhatithi.myapplication.R;
import in.co.medhatithi.myapplication.common.util.Observable;
import in.co.medhatithi.myapplication.screens.common.BaseViewMvc;
import in.co.medhatithi.myapplication.screens.common.OnSwipeTouchListener;

public class GamePadView extends BaseViewMvc<ImageView> implements Observable<GamePadView.GestureListener> {

    private GestureListener mGestureListener;
    private static final String TAG = "GamePadView";

    @Override
    public void registerListener(GestureListener gestureListener) {
        mGestureListener = gestureListener;
    }

    @Override
    public void unregisterListener(GestureListener gestureListener) {
        mGestureListener = null;
    }

    public interface GestureListener {
        void onSwipeRight();

        void onSwipeLeft();

        void onSwipeUp();

        void onSwipeBottom();

        void onTouch();
    }

    @SuppressLint("ClickableViewAccessibility")
    public GamePadView(LayoutInflater inflater, ViewGroup parent, Context context) {
        ImageView imageView = (ImageView) inflater.inflate(R.layout.layout_game_pad, parent, false);
        setRootView(imageView);

        imageView.setOnTouchListener(new OnSwipeTouchListener(context) {

            @Override
            public void onSwipeRight() {
                mGestureListener.onSwipeRight();
            }

            @Override
            public void onSwipeLeft() {
                mGestureListener.onSwipeLeft();
            }

            @Override
            public void onSwipeUp() {
                mGestureListener.onSwipeUp();
            }

            @Override
            public void onSwipeBottom() {
                mGestureListener.onSwipeBottom();
            }

            @Override
            public void onTouch() {
                mGestureListener.onTouch();
            }
        });

    }
}
