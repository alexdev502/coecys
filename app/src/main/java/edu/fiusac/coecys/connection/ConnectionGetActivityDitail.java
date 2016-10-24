package edu.fiusac.coecys.connection;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONObject;

import edu.fiusac.coecys.config.CoecysConfig;
import edu.fiusac.coecys.model.ActivityDitail;
import edu.fiusac.coecys.model.interfaces.OnNoInternetListener;
import edu.fiusac.coecys.utils.TransformerJSONToObject;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class ConnectionGetActivityDitail extends PeticionWebGet {
    private OnGetActivityDitailListener listener;
    private OnNoInternetListener internetListener;

    public ConnectionGetActivityDitail(Context context, @NonNull OnGetActivityDitailListener onGetActivityDitailListener) {
        super(context);
        this.listener = onGetActivityDitailListener;
    }


    public void getDitail(String idActivity){
        this.setUrl(CoecysConfig.Urls.d +idActivity);
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
                JSONObject jsonObjectDitail = jsonObject.getJSONObject("Detalle");
                ActivityDitail activityDitail = new TransformerJSONToObject().convertJSONToActivityDitail(jsonObjectDitail);

                if(activityDitail!=null) this.listener.onActivityDitailSuccces(activityDitail);
                else this.listener.onActivityDitailError(new Exception("Error parser json"));
            }else{
                this.listener.onActivityDitailError(new Exception("Server error, success 0"));
            }
        }catch (Exception ex){
            this.listener.onActivityDitailError(ex);
        }
    }

    @Override
    public void onErrorResponseReceived(Exception e) {
        listener.onActivityDitailError(e);
    }

    @Override
    public void onNoInternetConnection() {
        if(this.internetListener!=null) this.internetListener.onNoInternetConnection();
    }

    public interface OnGetActivityDitailListener{
        void onActivityDitailSuccces(ActivityDitail activityDitail);
        void onActivityDitailError(Exception ex);
    }
}
