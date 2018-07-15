package csr.capestart.com.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import csr.capestart.com.R;

import static csr.capestart.com.extras.AppConstants.INTENT_PARAM_ONE;

public class NotificationFragment extends BaseFragment {

    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance(String title) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(INTENT_PARAM_ONE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParentView = inflater.inflate(R.layout.fr_notification, container, false);
        return mParentView;
    }
}
