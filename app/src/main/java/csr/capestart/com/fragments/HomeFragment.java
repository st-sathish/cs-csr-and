package csr.capestart.com.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import csr.capestart.com.LoginActivity;
import csr.capestart.com.R;
import csr.capestart.com.data.ApiEndpoints;
import csr.capestart.com.extras.AppLog;
import csr.capestart.com.extras.SessionStore;
import csr.capestart.com.utils.Parser;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static csr.capestart.com.extras.AppConstants.INTENT_PARAM_ONE;

public class HomeFragment extends BaseFragment {

    TextView banner;
    TextView total_stock;
    TextView total_expired;
    TextView total_sold;
    TextView total_profit;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String title) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(INTENT_PARAM_ONE, title);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParentView = inflater.inflate(R.layout.fr_home, container, false);
        banner = mParentView.findViewById(R.id.banner);
        total_stock = mParentView.findViewById(R.id.total_stock);
        total_expired = mParentView.findViewById(R.id.total_expired);
        total_sold = mParentView.findViewById(R.id.total_sold);
        total_profit = mParentView.findViewById(R.id.total_profit);
        getDashboardMetaData();
        return mParentView;
    }

    public void getDashboardMetaData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        Rx2AndroidNetworking
                .get(ApiEndpoints.GET_DASHBOARD_META_DATA)
                .addHeaders("Content-Type","application/json")
                .build()
                .getJSONObjectObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONObject dashboard) {
                        progressDialog.dismiss();
                        setText(dashboard);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setText(JSONObject dashboard) {
        try {
            banner.setText(dashboard.getString("banner_message"));
            total_stock.setText(dashboard.getJSONObject("meta_data").getString("total_stock"));
            total_expired.setText(dashboard.getJSONObject("meta_data").getString("total_expired"));
            total_sold.setText(dashboard.getJSONObject("meta_data").getString("total_sold"));
            total_profit.setText(dashboard.getJSONObject("meta_data").getString("total_profit"));
        } catch (JSONException e) {
            AppLog.error("HomeFragment", e.getMessage());
        }
    }
}
