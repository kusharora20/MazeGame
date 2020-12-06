package in.co.medhatithi.myapplication.screens.common;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseObservableViewMvc<ListenerType, ViewType extends View> implements ObservableViewMvc<ListenerType, ViewType> {

    private List<ListenerType> mListeners = new ArrayList<>();

    @Override
    public ViewType getRootView() {
        return null;
    }

    @Override
    public void registerListener(ListenerType listener) {
        mListeners.add(listener);
    }

    @Override
    public void unregisterListener(ListenerType listener) {
        mListeners.remove(listener);
    }

    @Override
    public List<ListenerType> getListeners() {
        return mListeners;
    }
}
