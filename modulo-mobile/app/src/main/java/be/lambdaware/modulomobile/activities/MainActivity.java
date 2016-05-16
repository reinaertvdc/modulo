package be.lambdaware.modulomobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import be.lambdaware.modulomobile.R;
import be.lambdaware.modulomobile.api.ApiAuthentication;
import be.lambdaware.modulomobile.fragments.TabFragment;
import be.lambdaware.modulomobile.fragments.TaskFragment;
import be.lambdaware.modulomobile.models.User;

public class
MainActivity extends AppCompatActivity {

//    private LinearLayout mMainLayout;

    // Includes
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    private TabFragment tabFragment;
    private TaskFragment taskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize required settings
        ApiAuthentication.init(this);

        // Check if the application has been used before and has had a successful login
        // Redirect back to the login activity when this is not the case
        if (ApiAuthentication.getAuthenticationHeader().equals("empty")) {
            Log.i("MainActivity", "No valid login! Redirecting...");
            goToLoginActivity();
            return;
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        // Based on the users role, we hide some items.
        hideMenuItemsByRole();

        // Set users information in navigation drawer header
        setUserInfoInHeader();

        taskFragment = new TaskFragment();
        tabFragment = new TabFragment();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mDrawerLayout.closeDrawers();
                if (item.getItemId() == R.id.nav_tasks) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, taskFragment, "fragmentTag").commit();
                } else if (item.getItemId() == R.id.nav_progress) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, tabFragment, "fragmentTag").commit();
                } else if (item.getItemId() == R.id.nav_logout) {
                    ApiAuthentication.clear(getApplicationContext());
                    goToLoginActivity();
                }
                return false;
            }
        });

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, tabFragment, "fragmentTag").commit();

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }

    private void goToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void hideMenuItemsByRole() {
        if (ApiAuthentication.getAuthenticatedUser().isStudent()) {
            mNavigationView.getMenu().findItem(R.id.nav_children).setVisible(false);
        }
    }

    private void setUserInfoInHeader() {
        View header = mNavigationView.getHeaderView(0);
        TextView tvName = (TextView) header.findViewById(R.id.tv_name);
        TextView tvMail = (TextView) header.findViewById(R.id.tv_mail);

        User user = ApiAuthentication.getAuthenticatedUser();

        tvName.setText(user.getFullName());
        tvMail.setText(user.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (taskFragment.equals(getSupportFragmentManager().findFragmentByTag("fragmentTag"))) {
                FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                xfragmentTransaction.replace(R.id.containerView, tabFragment, "fragmentTag").commit();
            } else if (tabFragment.equals(getSupportFragmentManager().findFragmentByTag("fragmentTag"))) {
                if (tabFragment.getCurrentPage() == 1) {
                    tabFragment.showGeneral();
                } else {
                    super.onBackPressed();
                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


//    public void addScore(String competenceName, int totalCount, int practicedCount, int acquiredCount) {
//        View scoreLayout = LayoutInflater.from(this).inflate(R.layout.score_layout, mMainLayout, false);
//
//        TextView competenceView = (TextView) scoreLayout.findViewById(R.id.tv_competence);
//        TextView totalView = (TextView) scoreLayout.findViewById(R.id.tv_total);
//        TextView practicedView = (TextView) scoreLayout.findViewById(R.id.tv_practiced);
//        TextView acquiredView = (TextView) scoreLayout.findViewById(R.id.tv_acquired);
//        PieChart pieView = (PieChart) scoreLayout.findViewById(R.id.pc_chart);
//
//        competenceView.setText(competenceName);
//        totalView.setText(String.valueOf(totalCount));
//        practicedView.setText(String.valueOf(practicedCount));
//        acquiredView.setText(String.valueOf(acquiredCount));
//
//        int practicedPercentage = (int) (((practicedCount / (double) totalCount)) * 100);
//        int acquiredPercentage = (int) (((acquiredCount / (double) totalCount)) * 100);
//
//        pieView.addPieSlice(new PieModel("I", practicedPercentage, Color.parseColor("#FF0000")));
//        pieView.addPieSlice(new PieModel("V", acquiredPercentage, Color.parseColor("#00FF00")));
//
//
//        mMainLayout.addView(scoreLayout);
//    }
}
