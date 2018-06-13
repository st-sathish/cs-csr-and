package csr.capestart.com.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import csr.capestart.com.R;

public class ComingSoonFragment extends BaseFragment {

    public ComingSoonFragment() {

    }

    public static ComingSoonFragment newInstance(String title) {
        ComingSoonFragment comingsoonFragment = new ComingSoonFragment();
        return comingsoonFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParentView = inflater.inflate(R.layout.fr_coming_soon, container, false);
        return mParentView;
    }
}
