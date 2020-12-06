package in.co.medhatithi.myapplication.screens.common;

import android.view.View;

import java.util.List;

public interface ObservableViewMvc<ListenerType, T extends View> extends ViewMvc<T> {

    void registerListener(ListenerType listener);

    void unregisterListener(ListenerType listener);

    List<ListenerType> getListeners();

}
