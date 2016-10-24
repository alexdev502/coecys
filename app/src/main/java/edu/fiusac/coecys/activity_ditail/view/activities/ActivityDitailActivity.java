package edu.fiusac.coecys.activity_ditail.view.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.fiusac.coecys.R;
import edu.fiusac.coecys.activity_ditail.view.fragments.InfoFragment;
import edu.fiusac.coecys.activity_ditail.view.fragments.MapFragment;
import edu.fiusac.coecys.connection.ConnectionGetActivityDitail;
import edu.fiusac.coecys.model.ActivityDitail;
import edu.fiusac.coecys.model.interfaces.OnNoInternetListener;
import edu.fiusac.coecys.utils.DialogsManagement;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class ActivityDitailActivity extends AppCompatActivity implements ConnectionGetActivityDitail.OnGetActivityDitailListener, OnNoInternetListener {
    public static final String ID_ACTIVITY = "id_activity";

    private ConnectionGetActivityDitail connectionGetActivityDitail;

    private String sIdActivity;
    protected Fragment[] fragmentsAdapter;
    private ProgressDialog dialogWait;


    @BindView(R.id.pagerActivityDitail) ViewPager pagerActivityDitail;
    @BindView(R.id.tabsActivityDital) TabLayout tabLayoutActivityDitail;
    @BindView(R.id.coordinator) CoordinatorLayout view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_ditail);
        ButterKnife.bind(this);
        extractDate();
        setupFragments();
        setupToolbar();
        setupViewPager();
        setupTabLayout();

        connectionGetActivityDitail = new ConnectionGetActivityDitail(getApplicationContext(), this);
        connectionGetActivityDitail.setOnNoInternetListener(this);
        this.showDialgWait();
        connectionGetActivityDitail.getDitail(this.sIdActivity);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void extractDate() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.sIdActivity = bundle.getString(ID_ACTIVITY, null);
    }

    private void setupFragments() {
        Fragment infoFragment = InfoFragment.newInstance();
        Fragment mapFragment = MapFragment.newInstance();

        this.fragmentsAdapter = new Fragment[]{infoFragment, mapFragment};
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.activity_ditail));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupViewPager() {
        CustomePagerAdapter adapter = new CustomePagerAdapter(getSupportFragmentManager());
        pagerActivityDitail.setAdapter(adapter);
    }

    private void setupTabLayout() {
        tabLayoutActivityDitail.setupWithViewPager(pagerActivityDitail);
    }

    @Override
    public void onActivityDitailSuccces(ActivityDitail activityDitail) {
        this.hideDialogWait();
        ((InfoFragment)this.fragmentsAdapter[0]).setInformation(activityDitail);
        ((MapFragment)this.fragmentsAdapter[1]).setLocation(activityDitail);
    }

    @Override
    public void onActivityDitailError(Exception ex) {
        ex.printStackTrace();
        this.hideDialogWait();
        this.messageAlgoASalidoMal();
    }

    @Override
    public void onNoInternetConnection() {
        this.hideDialogWait();
        new DialogsManagement().alertNoInternet(this).show();
    }

    private void showDialgWait(){
        dialogWait = ProgressDialog.show(this, "",
                "Cargando información...", true);
    }

    private void hideDialogWait(){
        try{
            this.dialogWait.cancel();
        }catch (Exception ex){}
    }

    private void messageAlgoASalidoMal(){
        Snackbar.make(view, getString(R.string.algo_a_salido_mal), Snackbar.LENGTH_LONG).show();
    }


    private class CustomePagerAdapter extends FragmentPagerAdapter {
        String[] titles;

        public CustomePagerAdapter(FragmentManager fm) {
            super(fm);
            titles = new String[]{getString(R.string.info), getString(R.string.map)};
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentsAdapter[position];
        }

        @Override
        public int getCount() {
            return fragmentsAdapter.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

    }



}
