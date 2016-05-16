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
import be.lambdaware.modulomobile.adapters.TaskListAdapter;
import be.lambdaware.modulomobile.models.Task;


public class TaskFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // This recyclerview will contain all the scores as cards.
    private RecyclerView rvRecylcerView;
    private RecyclerView.Adapter scoreAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SwipeRefreshLayout srSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, null);

        rvRecylcerView = (RecyclerView) view.findViewById(R.id.rv_task_list);
        rvRecylcerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvRecylcerView.setLayoutManager(layoutManager);

        ArrayList<Task> data = new ArrayList<>();
        data.add(new Task("Opdracht 1", Date.valueOf("2016-05-02"), "", "Remarks", "Description", Task.TaskStatus.EMPTY));
        data.add(new Task("Opdracht 2", Date.valueOf("2016-05-02"), "", "Remarks", "Description", Task.TaskStatus.EMPTY));
        data.add(new Task("Opdracht 3", Date.valueOf("2016-05-02"), "", "Remarks", "Description", Task.TaskStatus.SUBMITTED));
        data.add(new Task("Opdracht 4", Date.valueOf("2016-05-02"), "V", "Remarks", "Description", Task.TaskStatus.GRADED));

        scoreAdapter = new TaskListAdapter(getContext(), data);
        rvRecylcerView.setAdapter(scoreAdapter);

        srSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_task_refresh_layout);
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