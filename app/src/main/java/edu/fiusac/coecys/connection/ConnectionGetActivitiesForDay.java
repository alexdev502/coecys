package edu.fiusac.coecys.connection;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.fiusac.coecys.config.CoecysConfig;
import edu.fiusac.coecys.model.ActivityInfo;
import edu.fiusac.coecys.model.interfaces.OnNoInternetListener;
import edu.fiusac.coecys.utils.TransformerJSONToObject;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class ConnectionGetActivitiesForDay extends PeticionWebGet {
    private OnGetActivitiesForDayListener listener;
    private OnNoInternetListener internetListener;

    public ConnectionGetActivitiesForDay(Context context, @NonNull OnGetActivitiesForDayListener onGetActivitiesForDayListener) {
        super(context);
        this.listener = onGetActivitiesForDayListener;
    }


    public void getActivities(String idDay){
        this.setUrl(CoecysConfig.Urls.c +idDay);
        this.exec();
    }

    public void setOnNoInternetListener(OnNoInternetListener onNoInternetListener){
        this.internetListener = onNoInternetListener;
    }

    @Override
    public void onResponseReceived(JSONObject jsonObject) {
        try {
            int success = jsonObject.getInt("success");
            if(success==1){
                List<ActivityInfo> activityInfoList = new ArrayList<>();
                JSONArray jsonArrayActivities = jsonObject.getJSONArray("Actividades");

                for(int i=0;i<jsonArrayActivities.length();i++){
                    JSONObject jsonObjectActivity =jsonArrayActivities.getJSONObject(i);
                    ActivityInfo activityInfo = new TransformerJSONToObject().convertJSONToActivityInfo(jsonObjectActivity);
                    if(activityInfo!=null) activityInfoList.add(activityInfo);
                }

                this.listener.onGetActivitiesForDaSuccces(activityInfoList);
            }else{
                this.listener.onGetActivitiesForDaError(new Exception("Server error, success 0"));
            }
        }catch (Exception ex){
            this.listener.onGetActivitiesForDaError(ex);
        }
    }

    @Override
    public void onErrorResponseReceived(Exception e) {
        listener.onGetActivitiesForDaError(e);
    }

    @Override
    public void onNoInternetConnection() {
        if(this.internetListener!=null) this.internetListener.onNoInternetConnection();
    }

    public interface OnGetActivitiesForDayListener{
        void onGetActivitiesForDaSuccces(List<ActivityInfo> activityInfoList);
        void onGetActivitiesForDaError(Exception ex);
    }
}
