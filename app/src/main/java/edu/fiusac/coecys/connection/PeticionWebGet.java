package edu.fiusac.coecys.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public abstract class PeticionWebGet implements Response.Listener<JSONObject>, Response.ErrorListener {
    private final Context context;
    private HashMap<String, String> data;
    protected final RequestQueue manager;
    protected String url;

    private RetryPolicy retryPolicy;

    public PeticionWebGet(Context context) {
        this.context = context;
        this.manager = Volley.newRequestQueue(context);
        this.retryPolicy = new DefaultRetryPolicy(30000, 1, 1.0F);
    }

    private boolean isConnected() {
        NetworkInfo localNetworkInfo = ((ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return (localNetworkInfo != null) && (localNetworkInfo.isConnectedOrConnecting());
    }

    protected void setRetryPolicy(RetryPolicy retryPolicy){
        this.retryPolicy = retryPolicy;
    }

    public void exec() {
        if (!isConnected()) {
            onNoInternetConnection();
            return;
        }

        Request request;
        request = new JsonObjectRequest(Request.Method.GET, url, this, this);

        request.setRetryPolicy(this.retryPolicy);
        this.manager.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if(volleyError instanceof NoConnectionError) {
            this.onNoInternetConnection();
        }

        onErrorResponseReceived(volleyError);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject != null) {
            onResponseReceived(jsonObject);
        } else {
            onErrorResponseReceived(new NullPointerException());
        }
    }

    protected void setUrl(String paramString) {
        this.url = paramString;
    }

    public abstract void onErrorResponseReceived(Exception e);

    public abstract void onNoInternetConnection();

    public abstract void onResponseReceived(JSONObject jsonObject);

}
