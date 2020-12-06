package in.co.medhatithi.myapplication.screens.common;

import android.content.Context;
import android.view.View;

public abstract class BaseViewMvc<ViewType extends View> implements ViewMvc<ViewType> {

    private ViewType root;

    protected <E extends View> E findViewById(int id){
        return root.findViewById(id);
    }

    protected void setRootView(ViewType view){
        root = view;
    }

    @Override
    public ViewType getRootView() {
        return root;
    }

    protected Context getContext(){
        return root.getContext();
    }


}
