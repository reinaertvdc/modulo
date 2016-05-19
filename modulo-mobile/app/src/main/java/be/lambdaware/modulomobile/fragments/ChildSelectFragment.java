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

import java.sql.Date;
import java.util.ArrayList;

import be.lambdaware.modulomobile.R;
import be.lambdaware.modulomobile.adapters.ChildListAdapter;
import be.lambdaware.modulomobile.adapters.TaskListAdapter;
import be.lambdaware.modulomobile.models.Task;
import be.lambdaware.modulomobile.models.User;


public class ChildSelectFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // This recyclerview will contain all the scores as cards.
    private RecyclerView rvRecylcerView;
    private RecyclerView.Adapter scoreAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SwipeRefreshLayout srSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_select, null);

        rvRecylcerView = (RecyclerView) view.findViewById(R.id.rv_child_list);
        rvRecylcerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvRecylcerView.setLayoutManager(layoutManager);

        ArrayList<User> data = new ArrayList<>();
        data.add(new User(1, "email@vankind.be", "Zoon", "Van ouder", "MALE", "STUDENT", true));
        scoreAdapter = new ChildListAdapter(getContext(), data);
        rvRecylcerView.setAdapter(scoreAdapter);

        srSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_child_refresh_layout);
        srSwipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onRefresh() {
        Log.i("GeneralFragment", "Refreshing list...");
        //TODO implement refresh from database.
        srSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public String toString() {
        return "TaskFragment";
    }
}