package com.example.test1.volleys;

import android.content.Context;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.test1.adapters.InterestAdapter;
import com.example.test1.adapters.SpinnerAdapter;
import com.example.test1.interfaces.InterestListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class FunctionGetListVolley {

    public void getListAddressAPI(Context context, Spinner spnAddress,List<String> addressStudyList) {
        String url = "https://poly-dating.herokuapp.com/api/data/facilities";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        addressStudyList.add(response.get(i).toString());
                    }
                    SpinnerAdapter spinnerAdapter = new SpinnerAdapter(context,addressStudyList);
                    spnAddress.setAdapter(spinnerAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    public void getListCourseAPI(Context context,Spinner spinnerDanhSach,List<String> courseList) {
        String url = "https://poly-dating.herokuapp.com/api/data/course";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        courseList.add(response.get(i).toString());
                    }
                    SpinnerAdapter spinnerAdapter = new SpinnerAdapter(context,courseList);
                    spinnerDanhSach.setAdapter(spinnerAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    public void getListInterestAPI(Context context, RecyclerView rycInterest, List<String> interestList, InterestListener interestListener) {
        String url = "https://poly-dating.herokuapp.com/api/data/hobbies";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        interestList.add(response.get(i).toString());
                    }
                    InterestAdapter interestAdapter = new InterestAdapter(context,interestList,interestListener);
                    rycInterest.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL, false));
                    rycInterest.setAdapter(interestAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    public void getListSpecializedAPI(Context context, Spinner spnChuyenNganh,List<String> specializedList) {
        String url = "https://poly-dating.herokuapp.com/api/data/specialized";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        specializedList.add(response.get(i).toString());
                    }
                    SpinnerAdapter spinnerAdapter = new SpinnerAdapter(context,specializedList);
                    spnChuyenNganh.setAdapter(spinnerAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

}
