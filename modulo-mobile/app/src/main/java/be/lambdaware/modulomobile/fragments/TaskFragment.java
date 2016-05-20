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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

import be.lambdaware.modulomobile.R;
import be.lambdaware.modulomobile.adapters.TaskListAdapter;
import be.lambdaware.modulomobile.api.ApiAuthentication;
import be.lambdaware.modulomobile.api.RestCall;
import be.lambdaware.modulomobile.api.RestCallback;
import be.lambdaware.modulomobile.models.Task;


public class TaskFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RestCallback {

    // This recyclerview will contain all the scores as cards.
    private RecyclerView rvRecylcerView;
    private RecyclerView.Adapter taskAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RestCall restCall;

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


        taskAdapter = new TaskListAdapter(getContext(), data);
        rvRecylcerView.setAdapter(taskAdapter);

        srSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_task_refresh_layout);
        srSwipeRefreshLayout.setOnRefreshListener(this);

        srSwipeRefreshLayout.setRefreshing(true);
        loadTasks();

        return view;
    }

    @Override
    public void onRefresh() {
        loadTasks();
    }

    private void loadTasks() {
        restCall = new RestCall(this);
        restCall.execute("http://10.0.2.2:8080/score/id/" + ApiAuthentication.getAuthenticatedUser().getId() + "/tasks");
    }


    @Override
    public void onSuccess(String response) throws JSONException {
        Log.i("Tasks", response);
        srSwipeRefreshLayout.setRefreshing(false);

        JSONArray JSONArray = new JSONArray(response);

        ArrayList<Task> tasks = new ArrayList<>();

        for (int i = 0; i < JSONArray.length(); i++) {
            JSONObject scoreObject = JSONArray.getJSONObject(i);
            JSONObject taskObject = scoreObject.getJSONObject("task");
            JSONObject clazzObject = taskObject.getJSONObject("clazz");

            Date deadline = Date.valueOf(taskObject.getString("deadline"));
            String name = taskObject.getString("name");
            String className = clazzObject.getString("name");
            String score = scoreObject.getString("score");
            String remarks = scoreObject.getString("remarks");
            String fileName = scoreObject.getString("fileName");

            String description = taskObject.getString("description");

            Task.TaskStatus status = Task.TaskStatus.EMPTY;
            if (!fileName.equals("null") && score.equals("null")) {
                status = Task.TaskStatus.SUBMITTED;
            }
            if (!score.equals("null")) {
                status = Task.TaskStatus.GRADED;
            }

            if (remarks.equals("null") || remarks.equals("")) remarks = "Geen opmerkingen.";
            if (description.equals("null") || description.equals(""))
                description = "Geen beschrijving.";
            if (score.equals("null") || score.equals("")) score = "Geen score.";
            if (fileName.equals("null") || fileName.equals("")) fileName = "Geen bestand.";

            tasks.add(new Task(className + " " + name, deadline, score, remarks, description, status));
        }

        taskAdapter = new TaskListAdapter(getContext(), tasks);
        rvRecylcerView.setAdapter(taskAdapter);

    }

    @Override
    public String toString() {
        return "TaskFragment";
    }


}