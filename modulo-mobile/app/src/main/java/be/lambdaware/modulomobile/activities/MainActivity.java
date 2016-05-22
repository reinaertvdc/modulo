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

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

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
            goToLoginActivity();
            return;
        }

        // If the user is a parent, we load his children and finish the view later
        if (ApiAuthentication.getAuthenticatedUser().isParent()) {
            loadChildren();
        }
        // Else, we continue to load the view.
        else {
            Database.setSelectedUser(ApiAuthentication.getAuthenticatedUser());
            finishCreateView();
        }

    }

    /**
     * Finishes the current activity and redirects to the LoginActivity
     */
    private void goToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    /**
     * Finish loading the view. This happens instantly if the user is a student. When the user
     * is a parent, we first load all the children.
     */
    private void finishCreateView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Based on the users role, we hide some items.
        hideMenuItemsByRole();

        // Set users information in navigation drawer header
        setUserInfoInHeader();

        // Instanciate the fragments
        taskFragment = new TaskFragment();
        tabFragment = new TabFragment();
        childFragment = new ChildSelectFragment();

        // Handles the selection of the navigational items
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                // Go to TaskFragment
                if (item.getItemId() == R.id.nav_tasks) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, taskFragment, "fragmentTag").commit();
                    taskFragment.loadTasks();
                }
                // Go to TabFragment
                else if (item.getItemId() == R.id.nav_progress) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, tabFragment, "fragmentTag").commit();
                }
                // Go to ChildSelectFragment
                else if (item.getItemId() == R.id.nav_children) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, childFragment, "fragmentTag").commit();
                }
                // Delete application storage and go to login activity
                else if (item.getItemId() == R.id.nav_logout) {
                    ApiAuthentication.clear(getApplicationContext());
                    goToLoginActivity();
                }
                return false;
            }
        });

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerView, tabFragment, "fragmentTag").commit();

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    /**
     * Hides the "child select" menu item based on the authenticated user's role.
     */
    private void hideMenuItemsByRole() {
        if (ApiAuthentication.getAuthenticatedUser().isStudent()) {
            navigationView.getMenu().findItem(R.id.nav_children).setVisible(false);
        } else {
            if (Database.getSizeOfChildren() == 1) {
                navigationView.getMenu().findItem(R.id.nav_children).setVisible(false);
            }
        }
    }

    /**
     * Updates the header in the navigation drawer to display the user's information.
     * In case of a parent, the header also shows the child's information.
     */
    public void setUserInfoInHeader() {
        View header = navigationView.getHeaderView(0);
        TextView tvName = (TextView) header.findViewById(R.id.tv_name);
        TextView tvAlias = (TextView) header.findViewById(R.id.tv_alias);
        TextView tvMail = (TextView) header.findViewById(R.id.tv_mail);

        User user = ApiAuthentication.getAuthenticatedUser();
        if (user.isParent() && Database.getSelectedUser() != null) {
            tvAlias.setText("Leerling: " + Database.getSelectedUser().getFullName());
            tvAlias.setVisibility(View.VISIBLE);
        }

        tvName.setText(user.getFullName());
        tvMail.setText(user.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // If menu is open -> close menu
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // if we are in the TaskFragment -> go back to TabFragment
            if (taskFragment.equals(getSupportFragmentManager().findFragmentByTag("fragmentTag"))) {
                FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                xfragmentTransaction.replace(R.id.containerView, tabFragment, "fragmentTag").commit();
            }
            // if we are in the ChildSelectFragment -> go back to TabFragment
            else if (childFragment.equals(getSupportFragmentManager().findFragmentByTag("fragmentTag"))) {
                FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                xfragmentTransaction.replace(R.id.containerView, tabFragment, "fragmentTag").commit();
            }
            // We are in the TabFragment
            else if (tabFragment.equals(getSupportFragmentManager().findFragmentByTag("fragmentTag"))) {
                // If we are in the BGVFragment -> go to GeneralTab
                if (tabFragment.getCurrentPage() == 1) {
                    tabFragment.showGeneral();
                }
                // Suspends the application
                else {
                    super.onBackPressed();
                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Perform a REST request to load all the children of a user. This happens Asynchronous.
     * The result will be saved in a static list in the Database class. This list is cleared first.
     */
    private void loadChildren() {
        Database.clearChildren();
        // Use this class as a callback for the REST request
        getChildrenCall = new RestCall(this);
        getChildrenCall.execute(ApiSettings.URL + ":" + ApiSettings.PORT + "/user/id/" + ApiAuthentication.getAuthenticatedUser().getId() + "/children");
    }


    @Override
    /**
     * This function is called when the getChildrenCall is finished. This call will pass it's
     * response in this function.
     */
    public void onSuccess(String response) throws JSONException {
        JSONArray JSONChildren = new JSONArray(response);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        // Iterate over array of Users
        for (int i = 0; i < JSONChildren.length(); i++) {
            JSONObject JSONChild = JSONChildren.getJSONObject(i);
            // Convert JSON "automatically" with Gson and save in the static list
            Database.saveChild(gson.fromJson(JSONChild.toString(), User.class));
        }

        // Default select first child
        Database.setSelectedUser(0);
        finishCreateView();
    }
}
