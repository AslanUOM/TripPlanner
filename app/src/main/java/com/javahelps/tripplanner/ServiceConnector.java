package com.javahelps.tripplanner;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * This class is used to send HTTP request to the service endoint and to return back the result.
 *
 * @author gobinath
 */
public class ServiceConnector<T> {


    /**
     * AsyncTask to run in separate thread.
     */
    private final BackgroundTask task = new BackgroundTask();

    private OnResponseListener listener;

    public ServiceConnector(OnResponseListener listener) {
        this.listener = listener;
    }

    public void execute(Request<T> request) {
        task.execute(request);
    }

    private class BackgroundTask extends AsyncTask<Request<T>, Void, String> {
        @Override
        protected String doInBackground(Request<T>... params) {
            Request<T> request = params[0];

            try {

                // Create HttpHeaders
                HttpHeaders requestHeaders = new HttpHeaders();

                // Set the content type
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<T> entity = new HttpEntity<>(request.getEntity(), requestHeaders);


                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate(true);
                ResponseEntity<String> response = restTemplate.exchange(request.getUrl(), request.getHttpMethod(), entity, String.class, (Object[]) request.getUrlVariables());
                String body = response.getBody();
                //Log.i("RESPONSE", body);
                return body;
            } catch (Exception e) {
                Log.e(this.getClass().getName(), e.getMessage(), e);

                return "ERROR";
            }
        }

        @Override
        protected void onPostExecute(String message) {
            if (listener != null) {
                listener.onResponseReceived(message);
            }
        }
    }
}
