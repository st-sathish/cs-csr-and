package csr.capestart.com.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import csr.capestart.com.R;
import csr.capestart.com.extras.AppConstants;

public class AddCookieFragment extends BaseFragment {

    public AddCookieFragment() {

    }

    public static AddCookieFragment newInstance(String title) {
        AddCookieFragment addCookieFragment = new AddCookieFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(AppConstants.INTENT_PARAM_ONE, title);
        addCookieFragment.setArguments(bundle);
        return addCookieFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParentView = inflater.inflate(R.layout.fr_add_cookie, container, false);
        return mParentView;
    }
}
