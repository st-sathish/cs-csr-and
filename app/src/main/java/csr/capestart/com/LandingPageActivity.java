package csr.capestart.com;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import csr.capestart.com.extras.AppConstants;
import csr.capestart.com.extras.AppLog;
import csr.capestart.com.extras.SessionStore;
import csr.capestart.com.firebase.FirebaseUtils;
import csr.capestart.com.fragments.AddCookieFragment;
import csr.capestart.com.fragments.CategoryFragment;
import csr.capestart.com.fragments.ComingSoonFragment;
import csr.capestart.com.fragments.StockListFragment;
import csr.capestart.com.fragments.FragmentDrawer;
import csr.capestart.com.fragments.HomeFragment;

public class LandingPageActivity extends BaseAppCompatActivity implements FragmentDrawer.FragmentDrawerListener, OnSuccessListener<InstanceIdResult> {

    private static final String TAG = "LandingPageActivity";
    public static final int FRAGMENT_DEFAULT = 1;
    public static final int FRAGMENT_HOME = 2;
    public static final int FRAGMENT_CATEGORY = 3;
    public static final int FRAGMENT_STOCKS = 4;
    public static final int FRAGMENT_EXPIRED_ITEM = 5;
    public static final int FRAGMENT_NOTIFICATION = 6;
    public static final int FRAGMENT_SETTINGS = 7;
    public static final int FRAGMENT_LOGOUT = 8;
    public static final int FRAGMENT_ADD_COOKIE = 9;

    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_landing_page);
        Toolbar mToolbar = findViewById(R.id.toolbar);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // get firebase token
        FirebaseInstanceId
                .getInstance()
                .getInstanceId()
                .addOnSuccessListener(LandingPageActivity.this,  this);

        //load default
        displayView(FRAGMENT_HOME, "", false);
    }

    public void displayView(int position, String aTitle, boolean addToBackstack) {
        Fragment fragment = null;
        String title = null;
        switch (position) {
            case FRAGMENT_HOME:
                title = "Home";
                fragment = HomeFragment.newInstance(title);
                break;
            case FRAGMENT_CATEGORY:
                title = "Categories";
                fragment = CategoryFragment.newInstance(title);
                break;
            case FRAGMENT_STOCKS:
                title = "Stocks";
                fragment = StockListFragment.newInstance(title);
                break;
            case FRAGMENT_EXPIRED_ITEM:
                title = "Expired Items";
                //fragment = ExpiredItemFragment.newInstance(title);
                fragment = ComingSoonFragment.newInstance(title);
                break;
            case FRAGMENT_NOTIFICATION:
                title = "Notifications";
                //fragment = NotificationFragment.newInstance(title);
                fragment = ComingSoonFragment.newInstance(title);
                break;
            case FRAGMENT_ADD_COOKIE:
                title = "Add Cookie";
                fragment = AddCookieFragment.newInstance(title);
                break;
            case FRAGMENT_LOGOUT:
                doLogout();
                break;
            case FRAGMENT_DEFAULT:
            default:
                title = "Coming Soon";
                fragment = ComingSoonFragment.newInstance(title);
                break;
        }
        if (null != fragment) {
            switchContent(fragment, title, addToBackstack);
        }
    }

    private void doLogout() {
        new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                // Set Dialog Title
                .setTitle("Alert")
                // Set Dialog Message
                .setMessage("Do you want to logout?")
                .setCancelable(false)
                // Positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // reset
                        SessionStore.user = null;
                        Intent intent = new Intent(LandingPageActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    public void switchContent(Fragment fragment, String title, boolean aAddtoBackstack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        String backStateName = ft.getClass().getName();
        //ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        if (aAddtoBackstack)
            ft.addToBackStack(backStateName);
        ft.commit();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        switch(position) {
            case 1:
                displayView(FRAGMENT_CATEGORY, "Category", true);
                break;
            case 2:
                displayView(FRAGMENT_STOCKS, "Stocks", true);
                break;
            case 3:
                displayView(FRAGMENT_DEFAULT, "Debtors", true);
                break;
            case 4:
                displayView(FRAGMENT_DEFAULT, "Sold Items", true);
                break;
            case 5:
                displayView(FRAGMENT_EXPIRED_ITEM, "Expired item", true);
                break;
            case 6:
                displayView(FRAGMENT_NOTIFICATION, "Notifications", true);
                break;
            case 7:
                displayView(FRAGMENT_DEFAULT, "Settings", true);
                break;
            case 8:
                displayView(FRAGMENT_LOGOUT, "Logout", true);
                break;
            default:
                displayView(FRAGMENT_HOME, "Home", true);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Integer fragment = intent.getIntExtra(AppConstants.KEY_NOTIFICATION_FRAGMENT, 0);
        if(fragment != 0) {
            displayView(fragment, "", false);
        }
    }

    @Override
    public void onSuccess(InstanceIdResult instanceIdResult) {
        String updatedToken = instanceIdResult.getToken();
        AppLog.log("Updated Token", updatedToken);
        // SessionStore.saveToken(getApplicationContext(), updatedToken);
        FirebaseUtils.saveToken(getApplicationContext(), updatedToken);
    }
}
