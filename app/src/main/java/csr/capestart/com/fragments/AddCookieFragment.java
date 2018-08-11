package csr.capestart.com.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import csr.capestart.com.R;
import csr.capestart.com.extras.AppConstants;

public class AddCookieFragment extends BaseFragment implements View.OnClickListener {

    EditText etExpiryDate;

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
        etExpiryDate = mParentView.findViewById(R.id.input_expiry_date);
        etExpiryDate.setOnClickListener(this);
        return mParentView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.input_expiry_date:
                DatePickerFragment dFragment = new DatePickerFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
                break;
            default:
                break;
        }
    }
}
