package edu.fiusac.coecys.connection;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.fiusac.coecys.config.CoecysConfig;
import edu.fiusac.coecys.model.DayInfo;
import edu.fiusac.coecys.model.interfaces.OnNoInternetListener;
import edu.fiusac.coecys.utils.TransformerJSONToObject;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class ConnectionGetDays extends PeticionWebGet {
    private OnGetDaysListener listener;
    private OnNoInternetListener internetListener;

    public ConnectionGetDays(Context context, @NonNull OnGetDaysListener onGetDaysListener) {
        super(context);
        this.listener = onGetDaysListener;
        this.setUrl(CoecysConfig.Urls.b);
    }


    public void getDays(){
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
                List<DayInfo> dayInfoList = new ArrayList<>();
                JSONArray jsonArrayDays = jsonObject.getJSONArray("Dias");
                for(int i=0;i<jsonArrayDays.length();i++){
                    JSONObject jsonObjectDay =jsonArrayDays.getJSONObject(i);
                    DayInfo dayInfo = new TransformerJSONToObject().convertJSONToDayInfo(jsonObjectDay);
                    if(dayInfo!=null) dayInfoList.add(0,dayInfo);
                }
                this.listener.onGetDaysSuccces(dayInfoList);
            }else{
                this.listener.onGetDaysError(new Exception("Server error, success 0"));
            }
        }catch (Exception ex){
            this.listener.onGetDaysError(ex);
        }
    }

    @Override
    public void onErrorResponseReceived(Exception e) {
        listener.onGetDaysError(e);
    }

    @Override
    public void onNoInternetConnection() {
        if(this.internetListener!=null) this.internetListener.onNoInternetConnection();
    }

    public interface OnGetDaysListener{
         void onGetDaysSuccces(List<DayInfo> dayInfoList);
         void onGetDaysError(Exception ex);
    }
}
