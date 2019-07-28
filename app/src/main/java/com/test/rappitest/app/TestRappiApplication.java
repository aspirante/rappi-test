package com.test.rappitest.app;

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class TestRappiApplication extends Application {

    private static final String ACCESS_TOKEN ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1MjhlZTBlZTYyMjliOGRmOWE4MmVmNjQ5M2I3NzdhNCIsInN1YiI6IjViYmVhY2UyYzNhMzY4MmIzNjAwNjY5NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.J2vqz_hxAGIsslU4rZk5e4o9gdK2J2Xa2HiGBdaDt-Y";
    private static final String API_KEY ="528ee0ee6229b8df9a82ef6493b777a4";
    public static final String BASE_URL_API = "https://api.themoviedb.org/4/list/1?page=1&api_key=" + API_KEY;
    public static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500";
    public static final int GRID_COULUMS_SIZE = 3;
    public static final String ARRAY_NAME_RESULTS = "results";
    public static boolean  filtering = false;

    public static final String TAG = TestRappiApplication.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static TestRappiApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized TestRappiApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static String getAccessToken() {
        return ACCESS_TOKEN;
    }
}
