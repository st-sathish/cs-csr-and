package csr.capestart.com.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import csr.capestart.com.LandingPageActivity;
import csr.capestart.com.R;
import csr.capestart.com.adapters.NavItemDrawerAdapter;
import csr.capestart.com.data.models.NavItem;
import csr.capestart.com.extras.AppLog;
import csr.capestart.com.extras.SessionStore;
import csr.capestart.com.extras.SimpleDividerItemDecoration;

public class FragmentDrawer extends Fragment implements View.OnClickListener {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private FragmentDrawerListener drawerListener;

    private RecyclerView recyclerView;
    private NavItemDrawerAdapter adapter;
    private TextView tvName;
    private View highlightedView;

    List<NavItem> mNavItems = new ArrayList<NavItem>();

    public FragmentDrawer() {
        //must need default constructor
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mNavItems.add(new NavItem("Home", R.drawable.icons8_home));
        mNavItems.add(new NavItem("Categories", R.drawable.icons8_categorize));
        mNavItems.add(new NavItem("Cookie Items", R.drawable.icons8_cookies));
        mNavItems.add(new NavItem("Expired Items", R.drawable.icons8_cookies));
        mNavItems.add(new NavItem("Notifications", R.drawable.icons8_notification));
        mNavItems.add(new NavItem("Settings", R.drawable.icons8_settings));
        mNavItems.add(new NavItem("Logout", R.drawable.icons8_shutdown));
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        AppLog.log("Navigation", "onCreateView");
        View layout = inflater.inflate(R.layout.fr_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        tvName = layout.findViewById(R.id.tv_name);
        adapter = new NavItemDrawerAdapter(getActivity(), mNavItems);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(
                getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (highlightedView != null) {
                    highlightedView.setSelected(false);
                }
                view.setSelected(true);
                highlightedView = view;
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String name = SessionStore.user.getFirstName()+" "+SessionStore.user.getLastName();
        tvName.setText(name);
    }

    @Override
    public void onPause() {
        AppLog.log("Navigation", "onPause");
        super.onPause();
    }

    @Override
    public void onResume() {
        AppLog.log("Navigation", "onResume");
        super.onResume();
    }

    @Override
    public void onStart() {
        AppLog.log("Navigation", "onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (null != getActivity()) {
                    getActivity().invalidateOptionsMenu();
                }
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context,
                                     final RecyclerView recyclerView,
                                     final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context,
                    new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public void onLongPress(MotionEvent e) {
                            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                            if (child != null && clickListener != null) {
                                clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                            }
                        }
                    });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null
                    && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(
                boolean disallowIntercept) {

        }

    }

    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);
    }

    @Override
    public void onClick(View v) {
        mDrawerLayout.closeDrawer(containerView);
    }

    public void toggleDrawer() {
        mDrawerLayout.openDrawer(containerView);
    }
}
