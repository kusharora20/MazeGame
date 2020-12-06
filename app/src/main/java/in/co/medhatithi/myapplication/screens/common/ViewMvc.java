package in.co.medhatithi.myapplication.screens.common;

import android.view.View;

public interface ViewMvc<ViewType extends View> {

    ViewType getRootView();

}
