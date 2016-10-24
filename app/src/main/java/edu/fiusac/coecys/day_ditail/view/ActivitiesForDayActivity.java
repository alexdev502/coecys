package edu.fiusac.coecys.day_ditail.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.fiusac.coecys.R;
import edu.fiusac.coecys.activity_ditail.view.activities.ActivityDitailActivity;
import edu.fiusac.coecys.connection.ConnectionGetActivitiesForDay;
import edu.fiusac.coecys.day_ditail.adapter.ActivitiesAdapter;
import edu.fiusac.coecys.model.ActivityInfo;
import edu.fiusac.coecys.model.interfaces.OnItemClickListener;
import edu.fiusac.coecys.model.interfaces.OnNoInternetListener;
import edu.fiusac.coecys.utils.DialogsManagement;
import edu.fiusac.coecys.utils.EmptyRecyclerAdapter;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class ActivitiesForDayActivity extends AppCompatActivity implements ConnectionGetActivitiesForDay.OnGetActivitiesForDayListener, OnNoInternetListener, OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String ID_DAY = "id_day";
    public static final String DATE = "date";

    @BindView(R.id.rvActivitiesForDay) RecyclerView recycler;
    @BindView(R.id.root_view) View view;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swRefreshLayout;

    private ConnectionGetActivitiesForDay connectionGetActivitiesForDay;
    private String sIdDay, sDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_for_day);
        ButterKnife.bind(this);
        this.extractData();
        this.setupToolbar();
        this.setupRecycler();

        this.connectionGetActivitiesForDay = new ConnectionGetActivitiesForDay(getApplicationContext(), this);
        this.connectionGetActivitiesForDay.setOnNoInternetListener(this);

        this.setupSwipeRefresh();
    }

    private void setupSwipeRefresh(){
        int mDistace = this.calcDistanceToTriggerSync();
        if(mDistace>0) this.swRefreshLayout.setDistanceToTriggerSync(mDistace);
        this.swRefreshLayout.setOnRefreshListener(this);
        this.swRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swRefreshLayout.setRefreshing(true);
                getActivitiesFromWebService();
            }
        });
    }

    private int calcDistanceToTriggerSync(){
        try{
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            return (int)(dpHeight*0.6f);
        }catch(Exception ex){}
        return -1;
    }

    private void extractData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.sIdDay = bundle.getString(ID_DAY, null);
        this.sDate = bundle.getString(DATE, "");
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.activities_for_day));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setSubtitle(this.sDate);
        }
    }

    private void setupRecycler(){
        recycler.setHasFixedSize(true);
        LinearLayoutManager lManager = new LinearLayoutManager(this);
        lManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(lManager);
        recycler.setAdapter(new EmptyRecyclerAdapter());
    }

    private void setAdapterRecyclerView(List<ActivityInfo> activityInfoList){
        ActivitiesAdapter adapter = new ActivitiesAdapter(this, activityInfoList, this);
        recycler.setAdapter(adapter);
    }

    private void getActivitiesFromWebService(){
        this.connectionGetActivitiesForDay.getActivities(sIdDay);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetActivitiesForDaSuccces(List<ActivityInfo> activityInfoList) {
        swRefreshLayout.setRefreshing(false);
        if(activityInfoList!=null) this.setAdapterRecyclerView(activityInfoList);
        else this.messageAlgoASalidoMal();
    }

    @Override
    public void onGetActivitiesForDaError(Exception ex) {
        swRefreshLayout.setRefreshing(false);
        ex.printStackTrace();
        this.messageAlgoASalidoMal();
    }

    @Override
    public void onNoInternetConnection() {
        swRefreshLayout.setRefreshing(false);
        new DialogsManagement().alertNoInternet(this).show();
    }

    @Override
    public void onItemClick(Object item) {
        ActivityInfo activityInfo = (ActivityInfo) item;
        Intent intent = new Intent(this, ActivityDitailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ActivityDitailActivity.ID_ACTIVITY, activityInfo.getId());
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void messageAlgoASalidoMal(){
        Snackbar.make(view, getString(R.string.algo_a_salido_mal), Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void onRefresh() {
        this.getActivitiesFromWebService();
    }
}
