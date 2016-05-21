package be.lambdaware.modulomobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.lambdaware.modulomobile.R;
import be.lambdaware.modulomobile.api.ApiAuthentication;
import be.lambdaware.modulomobile.api.ApiSettings;
import be.lambdaware.modulomobile.api.RestCall;
import be.lambdaware.modulomobile.api.RestCallback;
import be.lambdaware.modulomobile.database.Database;
import be.lambdaware.modulomobile.fragments.ChildSelectFragment;
import be.lambdaware.modulomobile.fragments.TabFragment;
import be.lambdaware.modulomobile.fragments.TaskFragment;
import be.lambdaware.modulomobile.models.User;

public class
MainActivity extends AppCompatActivity implements RestCallback {

//    private LinearLayout mMainLayout;

    // Includes
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    private TabFragment tabFragment;
    private ChildSelectFragment childFragment;
    private TaskFragment taskFragment;
    private RestCall getChildrenCall;

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

        if (ApiAuthentication.getAuthenticatedUser().isParent()) {
            Log.i("MainActivity", "Authenticated user is parent. Loading children");
            loadChildren();
        } else {
            Database.setSelectedUser(ApiAuthentication.getAuthenticatedUser());
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        // Based on the users role, we hide some items.
        hideMenuItemsByRole();

        // Set users information in navigation drawer header
        setUserInfoInHeader();

        taskFragment = new TaskFragment();
        tabFragment = new TabFragment();
        childFragment = new ChildSelectFragment();

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
                } else if (item.getItemId() == R.id.nav_children) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, childFragment, "fragmentTag").commit();
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

    public void setUserInfoInHeader() {
        View header = mNavigationView.getHeaderView(0);
        TextView tvName = (TextView) header.findViewById(R.id.tv_name);
        TextView tvAlias = (TextView) header.findViewById(R.id.tv_alias);
        TextView tvMail = (TextView) header.findViewById(R.id.tv_mail);

        User user = ApiAuthentication.getAuthenticatedUser();
        if(user.isParent() && Database.getSelectedUser()!=null){
            tvAlias.setText("Leerling: "+Database.getSelectedUser().getFullName());
            tvAlias.setVisibility(View.VISIBLE);
        }

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
            } else if (childFragment.equals(getSupportFragmentManager().findFragmentByTag("fragmentTag"))) {
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

    private void loadChildren() {
        Database.clearChildren();;
        getChildrenCall = new RestCall(this);
        getChildrenCall.execute(ApiSettings.URL + ":" + ApiSettings.PORT + "/user/id/" + ApiAuthentication.getAuthenticatedUser().getId() + "/children");
    }


    @Override
    public void onSuccess(String response) throws JSONException {
        JSONArray JSONChildren = new JSONArray(response);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        for (int i = 0; i < JSONChildren.length(); i++) {
            JSONObject JSONChild = JSONChildren.getJSONObject(i);
            Database.saveChild(gson.fromJson(JSONChild.toString(), User.class));
        }

        // Default select first child
        Database.setSelectedUser(0);
        setUserInfoInHeader();
    }
}
