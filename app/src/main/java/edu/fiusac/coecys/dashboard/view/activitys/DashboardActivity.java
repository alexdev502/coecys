package edu.fiusac.coecys.dashboard.view.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.fiusac.coecys.R;
import edu.fiusac.coecys.coecys_info.view.COECYSinfoActivity;
import edu.fiusac.coecys.dashboard.view.fragments.AboutFragment;
import edu.fiusac.coecys.dashboard.view.fragments.ActivitiesFragment;
import edu.fiusac.coecys.dashboard.view.fragments.MapFragment;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class DashboardActivity extends AppCompatActivity {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private int mSelectednNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_base);
        ButterKnife.bind(this);
        setupToolbar();
        setupDrawer();

        this.selectItem(R.id.nav_activities);
    }

    private void setupDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        selectItem(menuItem.getItemId());
                        return true;
                    }
                }
        );
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.dashboard_titulo));
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectItem(int idView) {
        if (idView == R.id.nav_activities) {
            if(mSelectednNav!=R.id.nav_activities){
                Fragment fragment = ActivitiesFragment.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .commit();
            }
        } else if (idView == R.id.nav_map) {
            if(mSelectednNav!=R.id.nav_map) {
                Fragment fragment = MapFragment.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .commit();
            }
        } else if(idView == R.id.nav_coecys){
            startActivity(new Intent(this, COECYSinfoActivity.class));
        }else if(idView == R.id.nav_about){
            if(mSelectednNav!=R.id.nav_about) {
                Fragment fragment = AboutFragment.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .commit();
            }
        } else{
            Toast.makeText(this, "Otra seleccion", Toast.LENGTH_LONG).show();
        }
        this.mSelectednNav = idView;
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawers();
    }

}
