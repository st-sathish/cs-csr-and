package csr.capestart.com.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import csr.capestart.com.LandingPageActivity;
import csr.capestart.com.R;
import csr.capestart.com.data.ApiEndpoints;
import csr.capestart.com.extras.AppConstants;
import csr.capestart.com.extras.SessionStore;
import csr.capestart.com.utils.NetworkConnectionUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class CategoryFragment extends BaseFragment implements View.OnClickListener {

    private EditText mCategoryName;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParentView = inflater.inflate(R.layout.fr_category, container, false);
        mCategoryName = mParentView.findViewById(R.id.input_category_name);
        mParentView.findViewById(R.id.btn_save).setOnClickListener(this);
        return mParentView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_save:
                if(null != getActivity()) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    if(imm != null && getActivity().getCurrentFocus() !=null) {
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }
                }
                saveCategory();
                break;
            default:
                break;
        }
    }

    public void saveCategory() {
        if(!NetworkConnectionUtils.isInternetOn(getContext())) {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        String itemName = mCategoryName.getText().toString();
        if(itemName.equals("")) {
            mCategoryName.setError("Please fill category name");
            return;
        }
        Map<String, String> payload = new HashMap<>();
        payload.put("category_name", itemName);
        payload.put("username", SessionStore.user.getEmail());
        Rx2AndroidNetworking.post(ApiEndpoints.POST_SAVE_CATEGORY)
                .addHeaders("Content-Type", "application/json")
                .addApplicationJsonBody(payload)
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
                    public void onNext(JSONObject jsonObject) {
                        dismissProgressDialog();
                        try {
                            if(jsonObject.getInt("success") == 1) {
                                String msg = jsonObject.getString("message");
                                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                                switchFragment(LandingPageActivity.FRAGMENT_LIST_CATEGORY, null, false);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Failed while saving category", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.getMessage());
                        dismissProgressDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
