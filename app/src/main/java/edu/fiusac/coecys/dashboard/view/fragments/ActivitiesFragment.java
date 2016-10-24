package edu.fiusac.coecys.dashboard.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.fiusac.coecys.R;
import edu.fiusac.coecys.connection.ConnectionGetDays;
import edu.fiusac.coecys.dashboard.adapter.DayAdapter;
import edu.fiusac.coecys.day_ditail.view.ActivitiesForDayActivity;
import edu.fiusac.coecys.model.DayInfo;
import edu.fiusac.coecys.model.interfaces.OnItemClickListener;
import edu.fiusac.coecys.model.interfaces.OnNoInternetListener;
import edu.fiusac.coecys.utils.DialogsManagement;
import edu.fiusac.coecys.utils.EmptyRecyclerAdapter;


/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class ActivitiesFragment extends Fragment implements ConnectionGetDays.OnGetDaysListener, OnNoInternetListener, OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rvDaysActivities) RecyclerView recycler;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swRefreshLayout;

    private Unbinder unbinder;

    private ConnectionGetDays connectionGetDays;
    public ActivitiesFragment() {
    }

    public static ActivitiesFragment newInstance() {
        ActivitiesFragment fragment = new ActivitiesFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.connectionGetDays = new ConnectionGetDays(getActivity().getApplicationContext(), this);
        this.connectionGetDays.setOnNoInternetListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setupRecycler();
        swRefreshLayout.setOnRefreshListener(this);
        swRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swRefreshLayout.setRefreshing(true);
                getDayFromWebService();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void getDayFromWebService(){
        this.connectionGetDays.getDays();
    }

    private void setupRecycler(){
        recycler.setHasFixedSize(true);
        LinearLayoutManager lManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(lManager);
        recycler.setAdapter(new EmptyRecyclerAdapter());
    }

    private void setAdapterRecyclerView(List<DayInfo> dayInfoList){
        DayAdapter adapter = new DayAdapter(dayInfoList, this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onGetDaysSuccces(List<DayInfo> dayInfoList) {
        swRefreshLayout.setRefreshing(false);
        if(dayInfoList!=null) this.setAdapterRecyclerView(dayInfoList);
        else this.messageAlgoASalidoMal();
    }

    @Override
    public void onGetDaysError(Exception ex) {
        swRefreshLayout.setRefreshing(false);
        this.messageAlgoASalidoMal();
    }

    @Override
    public void onNoInternetConnection() {
        swRefreshLayout.setRefreshing(false);
        new DialogsManagement().alertNoInternet(getContext()).show();
    }

    private void messageAlgoASalidoMal(){
        Snackbar.make(getView(), getString(R.string.algo_a_salido_mal), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(Object item) {
        this.startActivitiesForDayActivity((DayInfo) item);
    }

    private void startActivitiesForDayActivity(DayInfo dayInfo){
        Intent intent = new Intent(getContext(), ActivitiesForDayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ActivitiesForDayActivity.ID_DAY, dayInfo.getId());
        bundle.putString(ActivitiesForDayActivity.DATE, dayInfo.getDate());
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        getDayFromWebService();
    }
}
