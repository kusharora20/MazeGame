package in.co.medhatithi.myapplication.screens.toolbar;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.Toolbar;

import in.co.medhatithi.myapplication.R;
import in.co.medhatithi.myapplication.screens.common.BaseViewMvc;

public class ToolbarViewMvcImpl extends BaseViewMvc<androidx.appcompat.widget.Toolbar> {

    public ToolbarViewMvcImpl(LayoutInflater layoutInflater, ViewGroup parent) {
        Toolbar toolbar = (Toolbar) layoutInflater.inflate(R.layout.layout_toolbar, parent, false);
        setRootView(toolbar);

    }

}
