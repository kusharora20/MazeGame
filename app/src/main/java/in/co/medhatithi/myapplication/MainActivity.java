package in.co.medhatithi.myapplication;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import in.co.medhatithi.myapplication.screens.gamescreen.GameFragment;
import in.co.medhatithi.myapplication.screens.toolbar.ToolbarViewMvcImpl;

public class MainActivity extends BaseActivity {

    private ToolbarViewMvcImpl toolbarViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout frame = findViewById(R.id.frame_toolbar_main);
        toolbarViewMvc = getControllerCompositionRoot().getViewMvcFactory().getToolbarViewMvc(frame);
        frame.addView(toolbarViewMvc.getRootView());

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.frame_game, new GameFragment());
        ft.commit();


    }


}