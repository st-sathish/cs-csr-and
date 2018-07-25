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
import android.widget.ProgressBar;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csr.capestart.com.R;
import csr.capestart.com.adapters.CookieItemAdapter;
import csr.capestart.com.data.ApiEndpoints;
import csr.capestart.com.data.models.CookieItem;
import csr.capestart.com.extras.AppConstants;
import csr.capestart.com.extras.AppLog;
import csr.capestart.com.utils.Parser;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CookieItemFragment extends BaseFragment{

    private static final String TAG = "CookieItemFragment";

    private RecyclerView recyclerView;

    private ProgressBar mProgressbar;

    private CookieItemAdapter mCookieItemAdapter;

    public CookieItemFragment() {

    }

    public static CookieItemFragment newInstance(String title) {
        CookieItemFragment cookieItemFragment = new CookieItemFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(AppConstants.INTENT_PARAM_ONE, title);
        cookieItemFragment.setArguments(bundle);
        return cookieItemFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fr_cookie_item, container, false);
        recyclerView = mParentView.findViewById(R.id.recycler_view);
        mProgressbar = mParentView.findViewById(R.id.progress_bar);
        mCookieItemAdapter = new CookieItemAdapter();
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mCookieItemAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    page += page;
                    getCookieItems();
                }
            }
        });
        return mParentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCookieItems();
    }

    public void getCookieItems() {
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
                            List<CookieItem> cookieItems = Parser.parseCookieItem(result.getJSONArray("data"));
                            mCookieItemAdapter.addAllAndRefresh(cookieItems);
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
}
