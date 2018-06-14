package csr.capestart.com.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import csr.capestart.com.R;

public class CategoryFragment extends BaseFragment {

    public CategoryFragment() {

    }

    public static CategoryFragment newInstance(String title) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        return categoryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fr_category, container);
        return mParentView;
    }
}
