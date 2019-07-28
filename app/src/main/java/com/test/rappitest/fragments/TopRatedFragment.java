package com.test.rappitest.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.*;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.rappitest.R;
import com.test.rappitest.adapter.RecyclerViewAdapter;
import com.test.rappitest.app.TestRappiApplication;
import com.test.rappitest.model.CardItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopRatedFragment extends Fragment {
    private static final String TAG = TopRatedFragment.class.getSimpleName();
    private static final String TOP_RATED_URL = TestRappiApplication.BASE_URL_API + "&sort_by=vote_average.asc";
    private RecyclerView mRecyclerView;
    private List<CardItem> mItemsList;
    private RecyclerViewAdapter mAdapter;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.top_rated_fragment, container, false);
        mItemsList = new ArrayList<>();

        mRecyclerView = mView.findViewById(R.id.recyclerview);
        mAdapter = new RecyclerViewAdapter(getContext(), mItemsList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), TestRappiApplication.GRID_COULUMS_SIZE));
        mRecyclerView.setAdapter(mAdapter);
        setHasOptionsMenu(true);

        fetchTopRated();

        return mView;
    }

    /**
     * Fetch data from TheMoviesDb service
     */
    private void fetchTopRated() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, TOP_RATED_URL,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                JSONArray results = null;

                if (response == null) {
                    Toast.makeText(getContext().getApplicationContext(), "Couldn't fetch the movies/series! Pleas try again.", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    results = response.getJSONArray(TestRappiApplication.ARRAY_NAME_RESULTS);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                List<CardItem> items = new Gson().fromJson(results.toString(), new TypeToken<List<CardItem>>() {
                }.getType());

                // add popular list
                mItemsList.clear();
                mItemsList.addAll(items);

                // refres recyclerview
                mAdapter.setmData(mItemsList);
                mRecyclerView.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.e(TAG, "Site Info Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            /**
             * Request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + TestRappiApplication.getAccessToken());
                return headers;
            }
        };

        TestRappiApplication.getInstance().addToRequestQueue(request);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText == null || newText.isEmpty()) {
                    mAdapter.setmData(mItemsList);
                    mRecyclerView.setAdapter(mAdapter);
                    return false;
                }

                mAdapter.setmData(mAdapter.filteredList(newText, mItemsList));

                mRecyclerView.setAdapter(mAdapter);

                return false;
            }
        });

    }
}
