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
import be.lambdaware.modulomobile.api.RestCall;
import be.lambdaware.modulomobile.api.RestCallback;
import be.lambdaware.modulomobile.models.Score;


public class GeneralFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RestCallback {

    // This recyclerviehibfw will contain all the scores as cards.
    private RecyclerView rvRecylcerView;
    private RecyclerView.Adapter scoreAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SwipeRefreshLayout srSwipeRefreshLayout;
    private RestCall restCall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general, null);

        rvRecylcerView = (RecyclerView) view.findViewById(R.id.rv_general_score_list);
        rvRecylcerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvRecylcerView.setLayoutManager(layoutManager);

        ArrayList<Score> data = new ArrayList<>();

        scoreAdapter = new ScoreListAdapter(getContext(),data);
        rvRecylcerView.setAdapter(scoreAdapter);

        srSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_refresh_layout);
        srSwipeRefreshLayout.setOnRefreshListener(this);
        srSwipeRefreshLayout.setRefreshing(true);

        loadScores();

        return view;
    }

    @Override
    public void onRefresh() {
        Log.i("GeneralFragment","Refreshing list...");
        loadScores();
    }

    private void loadScores() {
        restCall = new RestCall(this);
        restCall.execute("http://10.0.2.2:8080/score/id/" + ApiAuthentication.getAuthenticatedUser().getId() + "/mobile");
    }

    @Override
    public void onSuccess(String response) throws JSONException {
        srSwipeRefreshLayout.setRefreshing(false);
        JSONObject scores = new JSONObject(response);
        ArrayList<Score> data = new ArrayList<>();
        JSONArray JSONarr = scores.getJSONArray("pav");
        JSONObject jsonScore = JSONarr.getJSONObject(0);
        Gson gson = new Gson();
        Score score = gson.fromJson(jsonScore.toString(),Score.class);
        Log.i("Score",score.toString());
        data.add(score);

        jsonScore = scores.getJSONObject("general");
        gson = new Gson();
        score = gson.fromJson(jsonScore.toString(),Score.class);
        Log.i("Score",score.toString());
        data.add(score);

        scoreAdapter = new ScoreListAdapter(getContext(), data);
        rvRecylcerView.setAdapter(scoreAdapter);
    }
}