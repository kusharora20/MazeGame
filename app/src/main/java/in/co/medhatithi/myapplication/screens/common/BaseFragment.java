package in.co.medhatithi.myapplication.screens.common;

import androidx.fragment.app.Fragment;
import in.co.medhatithi.myapplication.BaseActivity;
import in.co.medhatithi.myapplication.dependencyinjection.ControllerCompositionRoot;

public class BaseFragment extends Fragment {

    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getControllerCompositionRoot() {
        return (mControllerCompositionRoot == null)
                ? ((BaseActivity) getActivity()).getControllerCompositionRoot()
                : mControllerCompositionRoot;
    }
}