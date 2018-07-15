package csr.capestart.com.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csr.capestart.com.R;
import csr.capestart.com.adapters.CategoryAdapter;
import csr.capestart.com.data.ApiEndpoints;
import csr.capestart.com.data.models.Category;
import csr.capestart.com.extras.AppConstants;
import csr.capestart.com.extras.AppLog;
import csr.capestart.com.extras.SessionStore;
import csr.capestart.com.utils.Parser;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CategoryFragment extends BaseFragment {

    private static final String TAG = "CategoryFragment";

    private RecyclerView recyclerView;

    private List<Category> mCategoryList = new ArrayList<Category>();

    private CategoryAdapter mCategoryAdapter;

    public CategoryFragment() {

    }

    public static CategoryFragment newInstance(String title) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(AppConstants.INTENT_PARAM_ONE, title);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fr_category, container, false);
        recyclerView = mParentView.findViewById(R.id.recycler_view);
        mCategoryAdapter = new CategoryAdapter(mCategoryList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mCategoryAdapter);
        return mParentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCategories();
    }

    public void getCategories() {
        Rx2AndroidNetworking
                .post(ApiEndpoints.GET_CATEGORIES_API)
                .addHeaders("Content-Type","application/json")
                .build()
                .getJSONArrayObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONArray>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        showProgressDialog();
                    }

                    @Override
                    public void onNext(JSONArray categories) {
                        dismissProgressDialog();
                        Parser.addParsedCategory(categories, mCategoryList);
                        mCategoryAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressDialog();
                        AppLog.log(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
