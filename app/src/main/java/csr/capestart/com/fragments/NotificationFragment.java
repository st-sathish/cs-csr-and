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

import java.util.List;

import csr.capestart.com.R;
import csr.capestart.com.adapters.StockListAdapter;
import csr.capestart.com.data.ApiEndpoints;
import csr.capestart.com.data.models.Cookie;
import csr.capestart.com.extras.AppLog;
import csr.capestart.com.utils.Parser;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static csr.capestart.com.extras.AppConstants.INTENT_PARAM_ONE;

public class NotificationFragment extends BaseFragment {

    private static final String TAG = "NotificationFragment";

    private RecyclerView recyclerView;

    private StockListAdapter mStockListAdapter;

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
        recyclerView = mParentView.findViewById(R.id.recycler_view);
        recyclerView = mParentView.findViewById(R.id.recycler_view);
        mStockListAdapter = new StockListAdapter(getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mStockListAdapter);
        return mParentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCookieItems();
    }

    public void getCookieItems() {
        Rx2AndroidNetworking
                .get(ApiEndpoints.GET_NOTIFICATION_ITEMS)
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
                    public void onNext(JSONArray items) {
                        dismissProgressDialog();
                        List<Cookie> cookies = Parser.parseCookieItem(items);
                        mStockListAdapter.refresh(cookies);
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
