package be.lambdaware.modulomobile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.Random;

public class
MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout mMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mMainLayout = (LinearLayout) findViewById(R.id.ll_main_layout);

        View header = navigationView.getHeaderView(0);
        TextView textViewName = (TextView) header.findViewById(R.id.tv_name);
        TextView textViewMail = (TextView) header.findViewById(R.id.tv_mail);

        for (int i = 0; i < 6; i++) {
            Random random = new Random();
            int totalCount = random.nextInt(50) + 30;
            int practicedCount = random.nextInt(20) + 10;
            int acquiredCount = totalCount - practicedCount;
            addScore("Subcertificaat " + (i + 1), totalCount, practicedCount, acquiredCount);
        }

        new AsyncRestCall(textViewName, textViewMail).execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    int i = 0;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_progress) {
            //TODO do something with nav progress
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addScore(String competenceName, int totalCount, int practicedCount, int acquiredCount) {
        View scoreLayout = LayoutInflater.from(this).inflate(R.layout.score_layout, mMainLayout, false);

        TextView competenceView = (TextView) scoreLayout.findViewById(R.id.tv_competence);
        TextView totalView = (TextView) scoreLayout.findViewById(R.id.tv_total);
        TextView practicedView = (TextView) scoreLayout.findViewById(R.id.tv_practiced);
        TextView acquiredView = (TextView) scoreLayout.findViewById(R.id.tv_acquired);
        PieChart pieView = (PieChart) scoreLayout.findViewById(R.id.pc_chart);

        competenceView.setText(competenceName);
        totalView.setText(String.valueOf(totalCount));
        practicedView.setText(String.valueOf(practicedCount));
        acquiredView.setText(String.valueOf(acquiredCount));

        int practicedPercentage = (int) (((practicedCount / (double) totalCount)) * 100);
        int acquiredPercentage = (int) (((acquiredCount / (double) totalCount)) * 100);

        pieView.addPieSlice(new PieModel("I", practicedPercentage, Color.parseColor("#FF0000")));
        pieView.addPieSlice(new PieModel("V", acquiredPercentage, Color.parseColor("#00FF00")));


        mMainLayout.addView(scoreLayout);
    }
}
