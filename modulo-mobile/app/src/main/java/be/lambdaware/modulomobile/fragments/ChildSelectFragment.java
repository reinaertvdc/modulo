package be.lambdaware.modulomobile.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import be.lambdaware.modulomobile.R;
import be.lambdaware.modulomobile.activities.MainActivity;
import be.lambdaware.modulomobile.adapters.ChildListAdapter;
import be.lambdaware.modulomobile.api.ApiAuthentication;
import be.lambdaware.modulomobile.api.RestCall;
import be.lambdaware.modulomobile.api.RestCallback;
import be.lambdaware.modulomobile.database.Database;
import be.lambdaware.modulomobile.models.User;


public class ChildSelectFragment extends Fragment {

    // This recyclerview will contain all the scores as cards.
    private RecyclerView rvRecylcerView;
    private RecyclerView.Adapter childAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RestCall restCall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_select, null);

        rvRecylcerView = (RecyclerView) view.findViewById(R.id.rv_child_list);
        rvRecylcerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvRecylcerView.setLayoutManager(layoutManager);

        childAdapter = new ChildListAdapter(getContext(), Database.getChildren(), (MainActivity) getActivity());
        rvRecylcerView.setAdapter(childAdapter);

        return view;
    }


    @Override
    public String toString() {
        return "TaskFragment";
    }


}