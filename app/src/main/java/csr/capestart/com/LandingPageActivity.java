package csr.capestart.com;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import csr.capestart.com.fragments.CategoryFragment;
import csr.capestart.com.fragments.ComingSoonFragment;
import csr.capestart.com.fragments.CookieItemFragment;
import csr.capestart.com.fragments.ExpiredItemFragment;
import csr.capestart.com.fragments.FragmentDrawer;
import csr.capestart.com.fragments.HomeFragment;

public class LandingPageActivity extends BaseAppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    public static final int FRAGMENT_DEFAULT = 1;
    public static final int FRAGMENT_HOME = 2;
    public static final int FRAGMENT_CATEGORY = 3;
    public static final int FRAGMENT_COOKIE_ITEM = 4;
    public static final int FRAGMENT_EXPIRED_ITEM = 5;

    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_landing_page);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(mToolbar);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

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
            case FRAGMENT_COOKIE_ITEM:
                title = "Cookie Items";
                fragment = CookieItemFragment.newInstance(title);
                break;
            case FRAGMENT_EXPIRED_ITEM:
                title = "Expired Items";
                fragment = ExpiredItemFragment.newInstance(title);
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
        // setActionBarTitle(title);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        switch(position) {
            case 1:
                displayView(FRAGMENT_CATEGORY, "Category", true);
                break;
            case 2:
                displayView(FRAGMENT_COOKIE_ITEM, "Cookie Items", true);
                break;
            case 3:
                displayView(FRAGMENT_EXPIRED_ITEM, "Expired item", true);
                break;
            case 4:
                displayView(FRAGMENT_DEFAULT, "Notifications", true);
                break;
            case 5:
                displayView(FRAGMENT_DEFAULT, "Settings", true);
                break;
            case 6:
                displayView(FRAGMENT_DEFAULT, "Logout", true);
                break;
            default:
                displayView(FRAGMENT_HOME, "Home", true);
                break;
        }
    }
}
