package csr.capestart.com;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import csr.capestart.com.fragments.CategoryFragment;
import csr.capestart.com.fragments.ComingSoonFragment;
import csr.capestart.com.fragments.HomeFragment;

public class LandingPageActivity extends BaseAppCompatActivity {

    public static final int FRAGMENT_DEFAULT = 1;
    public static final int FRAGMENT_HOME = 2;
    public static final int FRAGMENT_CATEGORY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_landing_page);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //load default
        displayView(FRAGMENT_HOME, "", false);
    }

    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setTitle(title);
        }
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
                title = "List Categories";
                fragment = CategoryFragment.newInstance(title);
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

    public void switchContent(Fragment fragment, String title, boolean aAddtoBackstack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        String backStateName = ft.getClass().getName();
        //ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        ft.replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        if (aAddtoBackstack)
            ft.addToBackStack(backStateName);
        ft.commit();
        setActionBarTitle(title);
    }
}
