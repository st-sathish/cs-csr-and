package csr.capestart.com.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import csr.capestart.com.R;
import csr.capestart.com.data.ApiEndpoints;
import csr.capestart.com.data.domain.Category;
import csr.capestart.com.data.models.CookieRequest;
import csr.capestart.com.extras.AppConstants;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddCookieFragment extends BaseFragment implements View.OnClickListener, DatePickerFragment.OnDatePickerFragmentListener {

    EditText etCookieName;

    EditText etBarcode;

    EditText etPurchasePrice;

    EditText etSellingPrice;

    EditText etExpiryDate;

    Spinner mSpinnerCategory;

    public AddCookieFragment() {

    }

    public static AddCookieFragment newInstance(String title) {
        AddCookieFragment addCookieFragment = new AddCookieFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(AppConstants.INTENT_PARAM_ONE, title);
        addCookieFragment.setArguments(bundle);
        return addCookieFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParentView = inflater.inflate(R.layout.fr_add_cookie, container, false);
        etCookieName = mParentView.findViewById(R.id.input_cookie_name);
        etBarcode = mParentView.findViewById(R.id.input_barcode);
        etExpiryDate = mParentView.findViewById(R.id.input_expiry_date);
        etPurchasePrice = mParentView.findViewById(R.id.input_purchase_price);
        etSellingPrice = mParentView.findViewById(R.id.input_selling_price);
        mSpinnerCategory = mParentView.findViewById(R.id.spinner_category);
        etExpiryDate.setOnClickListener(this);
        mParentView.findViewById(R.id.btn_save).setOnClickListener(this);
        getCategories();
        return mParentView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.input_expiry_date:
                DatePickerFragment dFragment = new DatePickerFragment();
                dFragment.setOnDatePickerFragmentListener(this);
                dFragment.show(getFragmentManager(), "Date Picker");
                break;
            case R.id.btn_save:
                saveCookie();
                break;
            default:
                break;
        }
    }

    public void getCategories() {
        Rx2AndroidNetworking.get(ApiEndpoints.GET_CATEGORIES_API)
                .addHeaders("Content-Type", "application/json")
                .build()
                .getObjectListObservable(Category.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Category>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Category> categories) {
                        ArrayAdapter<Category> adapter = new ArrayAdapter<>(getContext(),
                                android.R.layout.simple_spinner_dropdown_item, categories);
                        mSpinnerCategory.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "Problem occured while loading categories", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void saveCookie() {
        String itemName = etCookieName.getText().toString();
        if(itemName.equals("")) {
            etCookieName.setError("Please fill cookie name");
            return;
        }
        String barcode = etBarcode.getText().toString();
        if(barcode.equals("")) {
            etBarcode.setError("Please fill barcode");
            return;
        }
        String expiryDate = etExpiryDate.getText().toString();
        if(expiryDate.equals("")) {
            etExpiryDate.setError("please fill expiry date");
            return;
        }
        String purchasePrice = etPurchasePrice.getText().toString();
        if(purchasePrice.equals("")) {
            etPurchasePrice.setError("Please fill Original Price");
            return;
        }
        String sellingPrice = etSellingPrice.getText().toString();
        if(sellingPrice.equals("")) {
            etSellingPrice.setError("Please fill selling price");
            return;
        }
        Category category = (Category) mSpinnerCategory.getSelectedItem();
        String c = String.valueOf(category.getId());
        CookieRequest request = new CookieRequest(itemName, barcode, purchasePrice, sellingPrice, expiryDate, c);
        Rx2AndroidNetworking.post(ApiEndpoints.POST_SAVE_COOKIE)
                .addHeaders("Content-Type", "application/json")
                .addApplicationJsonBody(request)
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
                                reset();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Failed while saving cookie", Toast.LENGTH_LONG).show();
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

    @Override
    public void onDateSetCallback(String date) {
        etExpiryDate.setText(date);
    }

    public void reset() {
        etCookieName.setText("");
        etCookieName.setFocusable(true);
        etBarcode.setText("");
        etSellingPrice.setText("");
        etExpiryDate.setText("");
        etPurchasePrice.setText("");
    }
}
