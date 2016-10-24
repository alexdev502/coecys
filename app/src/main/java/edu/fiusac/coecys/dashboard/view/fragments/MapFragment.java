package edu.fiusac.coecys.dashboard.view.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import edu.fiusac.coecys.R;
import edu.fiusac.coecys.connection.ConnectionGetPlaces;
import edu.fiusac.coecys.model.Place;
import edu.fiusac.coecys.model.interfaces.OnNoInternetListener;
import edu.fiusac.coecys.utils.DialogsManagement;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, ConnectionGetPlaces.OnGetPlacesListener, OnNoInternetListener {
    private SupportMapFragment fragment;
    private GoogleMap mMap;


    private int MARKER_WIDTH;
    private int MARKER_HEIGHT;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View view = layoutInflater.inflate(R.layout.fragment_map, paramViewGroup, false);
        setupMarkerSize();
        createFragmentIfNeed();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void setupMarkerSize(){
        MARKER_WIDTH = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 47, getResources().getDisplayMetrics());
        MARKER_HEIGHT = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
    }

    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        if(this.mMap!=null){
            this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(14.587622, -90.553162), 11f));
            new ConnectionGetPlaces(getActivity().getApplicationContext(), this).setOnNoInternetListener(this).getPlaces();
        }
    }

    private void calZoom(final List<Marker> arrayList) {
        if (arrayList == null) return;

        LatLngBounds.Builder localBuilder = new LatLngBounds.Builder();
        for (Marker anArrayList : arrayList) {
            localBuilder.include((anArrayList).getPosition());
        }

        final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(localBuilder.build(), (int) TypedValue.applyDimension(1, 90.0F, getResources().getDisplayMetrics()));
        this.mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            public void onMapLoaded() {
                MapFragment.this.mMap.animateCamera(cameraUpdate);
            }
        });


    }

    private void createFragmentIfNeed() {
        FragmentManager localFragmentManager = getChildFragmentManager();
        this.fragment = ((SupportMapFragment) localFragmentManager.findFragmentById(R.id.map_container));
        if (this.fragment == null) {
            this.fragment = SupportMapFragment.newInstance();
            localFragmentManager.beginTransaction().replace(R.id.map_container, this.fragment).commit();
        }
    }

    private void setUpMapIfNeeded() {
        if (this.mMap == null) {
            this.fragment.getMapAsync(this);
        }
    }

    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onGetPlacesSuccces(List<Place> placeList) {
        if(placeList!=null) loadPlacesMarkers(placeList);
    }

    @Override
    public void onGetPlacesError(Exception ex) {
        ex.printStackTrace();
        Toast.makeText(getContext(), "Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoInternetConnection() {
        new DialogsManagement().alertNoInternet(getContext()).show();
    }

    private void loadPlacesMarkers(List<Place> places){
        List<Marker> markers = new ArrayList<>();
        Bitmap bmpMarkerCoecys = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                                                            R.drawable.marker_coecys), MARKER_WIDTH, MARKER_HEIGHT,
                                                            true);
        for(Place place : places){
            Marker marker = mMap.addMarker(new MarkerOptions()
                                                    .position(place.getLatLng())
                                                    .title(place.getName())
                                                    .snippet(place.getAddress())
                                                    .icon(BitmapDescriptorFactory.fromBitmap(bmpMarkerCoecys))
                                                    );
            markers.add(marker);
        }

        calZoom(markers);
    }


}
