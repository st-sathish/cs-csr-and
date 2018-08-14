package csr.capestart.com.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import csr.capestart.com.LandingPageActivity;
import csr.capestart.com.R;
import csr.capestart.com.adapters.CategoryAdapter;
import csr.capestart.com.data.ApiEndpoints;
import csr.capestart.com.data.models.Category;
import csr.capestart.com.extras.AppConstants;
import csr.capestart.com.extras.AppLog;
import csr.capestart.com.utils.NetworkConnectionUtils;
import csr.capestart.com.utils.Parser;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CategoryListFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "CategoryListFragment";

    private RecyclerView recyclerView;

    private List<Category> mCategoryList;

    private CategoryAdapter mCategoryAdapter;

    public CategoryListFragment() {

    }

    public static CategoryListFragment newInstance(String title) {
        CategoryListFragment categoryListFragment = new CategoryListFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(AppConstants.INTENT_PARAM_ONE, title);
        categoryListFragment.setArguments(bundle);
        return categoryListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fr_categories, container, false);
        mCategoryList = new ArrayList<>();
        mParentView.findViewById(R.id.add_category).setOnClickListener(this);
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
        if(!NetworkConnectionUtils.isInternetOn(getContext())) {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        Rx2AndroidNetworking
                .post(ApiEndpoints.GET_CATEGORIES_API)
                .addHeaders("Content-Type","application/json")
                .build()
                .getJSONObjectObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        showProgressDialog();
                    }

                    @Override
                    public void onNext(JSONObject categories) {
                        dismissProgressDialog();
                        try {
                            if(categories.getJSONArray("data").length() == 0) {
                                Toast.makeText(getContext(), "No Record Found", Toast.LENGTH_LONG).show();
                                return;
                            }
                            Parser.addParsedCategory(categories.getJSONArray("data"), mCategoryList);
                            mCategoryAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.getMessage();
                        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_category:
                switchFragment(LandingPageActivity.FRAGMENT_CATEGORY, "Add Category", true);
                break;
        }
    }
}
