package in.co.medhatithi.myapplication;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import in.co.medhatithi.myapplication.dependencyinjection.ControllerCompositionRoot;

public abstract class BaseActivity extends FragmentActivity {

    private ControllerCompositionRoot mControllerCompositionRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mControllerCompositionRoot = new ControllerCompositionRoot(getLayoutInflater(), this);

    }

    public ControllerCompositionRoot getControllerCompositionRoot() {
        return mControllerCompositionRoot;
    }

}
