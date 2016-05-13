package be.lambdaware.modulomobile.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.lambdaware.modulomobile.R;
import be.lambdaware.modulomobile.adapters.ScoreListAdapter;


public class BGVFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // This recyclerview will contain all the scores as cards.
    private RecyclerView rvRecylcerView;
    private RecyclerView.Adapter scoreAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SwipeRefreshLayout srSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bgv, null);



        rvRecylcerView = (RecyclerView) view.findViewById(R.id.rv_score_list);
        rvRecylcerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvRecylcerView.setLayoutManager(layoutManager);

        scoreAdapter = new ScoreListAdapter();
        rvRecylcerView.setAdapter(scoreAdapter);

        return view;
    }

    @Override
    public void onRefresh() {

    }
}