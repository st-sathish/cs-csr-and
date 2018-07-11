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

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import csr.capestart.com.R;
import csr.capestart.com.adapters.CategoryAdapter;
import csr.capestart.com.adapters.ExpiredItemAdapter;
import csr.capestart.com.data.ApiEndpoints;
import csr.capestart.com.data.models.Category;
import csr.capestart.com.data.models.ExpiredItem;
import csr.capestart.com.extras.AppLog;
import csr.capestart.com.utils.Parser;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExpiredItemFragment extends BaseFragment {

    private static final String TAG = "ExpiredItemFragment";

    private RecyclerView recyclerView;

    private ExpiredItemAdapter mExpiredItemAdapter;

    public ExpiredItemFragment() {

    }

    public static ExpiredItemFragment newInstance(String title) {
        ExpiredItemFragment expiredItemFragment = new ExpiredItemFragment();
        Bundle bundle = new Bundle();
        return expiredItemFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fr_expired_item, container, false);
        recyclerView = mParentView.findViewById(R.id.recycler_view);
        mExpiredItemAdapter = new ExpiredItemAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mExpiredItemAdapter);
        return mParentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getExpiredItems();
    }

    public void getExpiredItems() {
        Rx2AndroidNetworking
                .post(ApiEndpoints.GET_EXPIRED_ITEMS_API)
                .addHeaders("Content-Type","application/json")
                .build()
                .getJSONArrayObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONArray>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONArray items) {
                        List<ExpiredItem> expiredItems = Parser.parseExpiredItem(items);
                        mExpiredItemAdapter.refresh(expiredItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.log(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}