package edu.fiusac.coecys.connection;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.fiusac.coecys.config.CoecysConfig;
import edu.fiusac.coecys.model.Place;
import edu.fiusac.coecys.model.interfaces.OnNoInternetListener;
import edu.fiusac.coecys.utils.TransformerJSONToObject;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class ConnectionGetPlaces extends PeticionWebGet {
    private OnGetPlacesListener listener;
    private OnNoInternetListener internetListener;

    public ConnectionGetPlaces(Context context, @NonNull OnGetPlacesListener onGetPlacesListener) {
        super(context);
        this.listener = onGetPlacesListener;
        this.setUrl(CoecysConfig.Urls.e);
    }


    public void getPlaces(){
        this.exec();
    }

    public ConnectionGetPlaces setOnNoInternetListener(OnNoInternetListener onNoInternetListener){
        this.internetListener = onNoInternetListener;
        return this;
    }

    @Override
    public void onResponseReceived(JSONObject jsonObject) {
        try {
            int success = jsonObject.getInt("success");
            if(success==1){
                List<Place> placeList = new ArrayList<>();
                JSONArray jsonArrayPlaces = jsonObject.getJSONArray("Lugares");
                for(int i=0; i<jsonArrayPlaces.length(); i++){
                    JSONObject jsonObjectPlace =jsonArrayPlaces.getJSONObject(i);
                    Place place = new TransformerJSONToObject().convertJSONToPlace(jsonObjectPlace);
                    if(place!=null) placeList.add(place);
                }
                this.listener.onGetPlacesSuccces(placeList);
            }else{
                this.listener.onGetPlacesError(new Exception("Server error, success 0"));
            }
        }catch (Exception ex){
            this.listener.onGetPlacesError(ex);
        }
    }

    @Override
    public void onErrorResponseReceived(Exception e) {
        listener.onGetPlacesError(e);
    }

    @Override
    public void onNoInternetConnection() {
        if(this.internetListener!=null) this.internetListener.onNoInternetConnection();
    }

    public interface OnGetPlacesListener{
        void onGetPlacesSuccces(List<Place> placeList);
        void onGetPlacesError(Exception ex);
    }
}
