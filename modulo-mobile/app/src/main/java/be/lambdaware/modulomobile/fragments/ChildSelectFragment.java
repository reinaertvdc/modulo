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
import be.lambdaware.modulomobile.adapters.ChildListAdapter;
import be.lambdaware.modulomobile.api.ApiAuthentication;
import be.lambdaware.modulomobile.api.RestCall;
import be.lambdaware.modulomobile.api.RestCallback;
import be.lambdaware.modulomobile.models.User;


public class ChildSelectFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RestCallback {

    // This recyclerview will contain all the scores as cards.
    private RecyclerView rvRecylcerView;
    private RecyclerView.Adapter childAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SwipeRefreshLayout srSwipeRefreshLayout;

    private RestCall restCall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_select, null);

        rvRecylcerView = (RecyclerView) view.findViewById(R.id.rv_child_list);
        rvRecylcerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvRecylcerView.setLayoutManager(layoutManager);

        ArrayList<User> data = new ArrayList<>();
        childAdapter = new ChildListAdapter(getContext(), data);
        rvRecylcerView.setAdapter(childAdapter);

        srSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_child_refresh_layout);
        srSwipeRefreshLayout.setOnRefreshListener(this);

        srSwipeRefreshLayout.setRefreshing(true);
        loadChildren();
        return view;
    }

    @Override
    public void onRefresh() {
        loadChildren();
    }

    @Override
    public String toString() {
        return "TaskFragment";
    }

    @Override
    public void onSuccess(String response) throws JSONException {
        srSwipeRefreshLayout.setRefreshing(false);

        JSONArray JSONArray = new JSONArray(response);
        ArrayList<User> data = new ArrayList<>();
        for (int i = 0; i < JSONArray.length(); i++) {
            JSONObject JSONUser = JSONArray.getJSONObject(i);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            User user = gson.fromJson(JSONUser.toString(), User.class);
            data.add(user);
        }

        childAdapter = new ChildListAdapter(getContext(), data);
        rvRecylcerView.setAdapter(childAdapter);
    }

    private void loadChildren() {
        Log.i("ChildSelectFragment", "Getting al users...");
        restCall = new RestCall(this);
        restCall.execute("http://10.0.2.2:8080/user/id/" + ApiAuthentication.getAuthenticatedUser().getId() + "/children");
    }

}