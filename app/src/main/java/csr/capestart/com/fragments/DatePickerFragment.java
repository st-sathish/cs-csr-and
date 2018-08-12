package csr.capestart.com.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private OnDatePickerFragmentListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(mListener != null) {
            String monthString = String.valueOf(month);
            if (monthString.length() == 1) {
                monthString = "0" + monthString;
            }
            String dayString = String.valueOf(dayOfMonth);
            if (dayString.length() == 1) {
                dayString = "0" + dayString;
            }
            String date = String.valueOf(dayString)+"/"+monthString+"/"+String.valueOf(year);
            mListener.onDateSetCallback(date);
        }
    }

    public void setOnDatePickerFragmentListener(OnDatePickerFragmentListener listener) {
        this.mListener = listener;
    }

    public interface OnDatePickerFragmentListener {
        void onDateSetCallback(String date);
    }
}
