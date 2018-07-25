package csr.capestart.com.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import csr.capestart.com.LoginActivity;
import csr.capestart.com.R;
import csr.capestart.com.extras.AppConstants;

public class BaseFragment extends Fragment {

    public String mTitle = "My Title";
    protected boolean mIsVisible = false;
    protected View mParentView = null;
    protected int page = 0;
    protected boolean isInfiniteScroll = false;
    ProgressDialog progressDialog = null;
    protected int limit = 10;

    public BaseFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //reset
        page = 0;
        if(null != getArguments()) {
            mTitle = getArguments().getString(AppConstants.INTENT_PARAM_ONE);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toolbar mToolbar = getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsVisible) {
            mIsVisible = true;
           ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle(mTitle);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsVisible = false;
    }

    protected void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
