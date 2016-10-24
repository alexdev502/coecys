package edu.fiusac.coecys.activity_ditail.view.fragments;


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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.fiusac.coecys.R;
import edu.fiusac.coecys.model.ActivityDitail;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment fragment;
    private GoogleMap mMap;
    private LatLng location;
    private String namePlace, addressPlace;

    private int MARKER_WIDTH;
    private int MARKER_HEIGHT;

    public MapFragment() {
    }

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

        if (this.mMap != null) {
            this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(14.587622, -90.553162), 11f));
            this.setMarker();
        }
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

    public void setLocation(ActivityDitail activityDitail) {
        LatLng latLng = activityDitail.getLatLang();
        if (latLng != null) {
            this.location = latLng;
            this.namePlace = activityDitail.getPlace();
            this.addressPlace = activityDitail.getAddress();
            this.setMarker();
        }
    }

    private void setMarker() {
        if (this.mMap != null && this.location!=null) {
            this.mMap.clear();

            Bitmap bmpMarkerCoecys = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                    R.drawable.marker_coecys), MARKER_WIDTH, MARKER_HEIGHT,
                    true);

            this.mMap.addMarker(new MarkerOptions()
                    .position(this.location)
                    .title(this.namePlace)
                    .snippet(this.addressPlace)
                    .icon(BitmapDescriptorFactory.fromBitmap(bmpMarkerCoecys))
            ).showInfoWindow();
            this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.location, 15f));
        }
    }
}
