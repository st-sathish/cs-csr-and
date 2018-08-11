package csr.capestart.com.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csr.capestart.com.LandingPageActivity;
import csr.capestart.com.R;
import csr.capestart.com.adapters.StockListAdapter;
import csr.capestart.com.data.ApiEndpoints;
import csr.capestart.com.data.models.Cookie;
import csr.capestart.com.extras.AppConstants;
import csr.capestart.com.extras.AppLog;
import csr.capestart.com.utils.Parser;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StockListFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "StockListFragment";

    private RecyclerView recyclerView;

    private ProgressBar mProgressbar;

    private StockListAdapter mStockListAdapter;

    public StockListFragment() {

    }

    public static StockListFragment newInstance(String title) {
        StockListFragment stockListFragment = new StockListFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(AppConstants.INTENT_PARAM_ONE, title);
        stockListFragment.setArguments(bundle);
        return stockListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fr_cookie_item, container, false);
        recyclerView = mParentView.findViewById(R.id.recycler_view);
        mProgressbar = mParentView.findViewById(R.id.progress_bar);
        mParentView.findViewById(R.id.add_cookie).setOnClickListener(this);
        mStockListAdapter = new StockListAdapter(getContext());
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        //final GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mStockListAdapter);
        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    page += page;
                    getStocks();
                }
            }
        });*/
        return mParentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getStocks();
    }

    public void getStocks() {
        Map<String, Integer> body = new HashMap<>();
        body.put("page", page);
        body.put("limit", limit);
        Rx2AndroidNetworking
                .post(ApiEndpoints.GET_COOKIE_ITEMS_API)
                .addHeaders("Content-Type","application/json")
                .addApplicationJsonBody(body)
                .build()
                .getJSONObjectObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mProgressbar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(JSONObject result) {
                        mProgressbar.setVisibility(View.INVISIBLE);
                        try {
                            List<Cookie> cookies = Parser.parseCookieItem(result.getJSONArray("data"));
                            mStockListAdapter.addAllAndRefresh(cookies);
                        } catch(JSONException e) {
                            AppLog.error(TAG, e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressbar.setVisibility(View.INVISIBLE);
                        AppLog.log(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switchFragment(LandingPageActivity.FRAGMENT_ADD_COOKIE, "Add Cookie", true);
    }
}
