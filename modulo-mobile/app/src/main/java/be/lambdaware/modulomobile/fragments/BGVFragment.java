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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import be.lambdaware.modulomobile.R;
import be.lambdaware.modulomobile.adapters.ScoreListAdapter;
import be.lambdaware.modulomobile.api.ApiAuthentication;
import be.lambdaware.modulomobile.api.ApiSettings;
import be.lambdaware.modulomobile.api.RestCall;
import be.lambdaware.modulomobile.api.RestCallback;
import be.lambdaware.modulomobile.database.Database;
import be.lambdaware.modulomobile.models.Score;


public class BGVFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RestCallback {

    // This recyclerview will contain all the scores as cards.
    private RecyclerView rvRecylcerView;
    private RecyclerView.Adapter scoreAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SwipeRefreshLayout srSwipeRefreshLayout;
    private RestCall restCall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bgv, null);

        rvRecylcerView = (RecyclerView) view.findViewById(R.id.rv_sub_certificate_score_list);
        rvRecylcerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvRecylcerView.setLayoutManager(layoutManager);

        ArrayList<Score> data = new ArrayList<>();
        scoreAdapter = new ScoreListAdapter(getContext(), data);
        rvRecylcerView.setAdapter(scoreAdapter);

        srSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_bgv_refresh_layout);
        srSwipeRefreshLayout.setOnRefreshListener(this);
        loadBgvScores();
        return view;
    }

    @Override
    public void onRefresh() {
        loadBgvScores();
    }

    private void loadBgvScores() {
        Log.i("GeneralFragment", "Loading scores for " + Database.getSelectedUser().toString());
        restCall = new RestCall(this);
        restCall.execute(ApiSettings.URL+":"+ApiSettings.PORT+"/score/id/" + Database.getSelectedUser().getId() + "/mobile");
    }

    @Override
    public void onSuccess(String response) throws JSONException {
        srSwipeRefreshLayout.setRefreshing(false);
        JSONObject scores = new JSONObject(response);
        ArrayList<Score> data = new ArrayList<>();
        JSONArray bgv = scores.getJSONArray("bgv");
        for (int i = 0; i < bgv.length(); i++) {
            JSONObject JSONScore = bgv.getJSONObject(i);
            Gson gson = new Gson();
            Score score = gson.fromJson(JSONScore.toString(),Score.class);
            Log.i("Score",score.toString());
            data.add(score);
        }
        scoreAdapter = new ScoreListAdapter(getContext(), data);
        rvRecylcerView.setAdapter(scoreAdapter);

    }
}