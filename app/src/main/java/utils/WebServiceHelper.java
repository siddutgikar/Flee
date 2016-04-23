package utils;


import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by siddh on 4/22/2016.
 */
public class WebServiceHelper {

    public static final String TAG="WebServiceHelper";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();


    /**
     * Will handle the Get Requests
     *
     * @param url
     * @return
     */
    public Response GET(String url) {
        try {
            Log.d(TAG+" GET",url);
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response;

        } catch (Exception ex) {
            Log.e(TAG+" GET",ex.toString());
            return null;
        }
    }


    /**
     * Will handle Post Requests ..
     * THe Json String is the data being poseted to the server
     * @param url
     * @param json
     * @return
     */
    public Response POST(String url, String json) {
        try {
            Log.d(TAG+" POST",url + "  data: "+json);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response;
        } catch (Exception ex) {
            Log.e(TAG+" POST",ex.toString());
            return null;
        }
    }


}
