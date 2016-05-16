package be.lambdaware.modulomobile.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.List;

import be.lambdaware.modulomobile.R;
import be.lambdaware.modulomobile.models.Score;
import be.lambdaware.modulomobile.models.Task;

/**
 * Created by Hendrik on 13/05/2016.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public View parent;

        public ViewHolder(View parent) {
            super(parent);
        }
    }

    private Context context;
    private List<Task> data;

    public TaskListAdapter(Context context, List<Task> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        // set view layouting here.

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = data.get(position);

        if (task != null) {
            //TODO update task views
//            holder.tvSubCertificate.setText(score.getName());
//            holder.tvTotalCount.setText(String.valueOf(score.getTotalCompetences()));
//            holder.tvPassedScore.setText(String.valueOf(score.getTotalPassed()));
//            holder.tvFailedScore.setText(String.valueOf(score.getTotalFailed()));
//            holder.pcChart.addPieSlice(new PieModel("Niet Behaald", score.getFailedPercentage(), ContextCompat.getColor(context,R.color.colorRed)));
//            holder.pcChart.addPieSlice(new PieModel("Behaald", score.getPassedPercentage(), ContextCompat.getColor(context,R.color.colorGreen)));
//
//            holder.tvOffered.setText(String.valueOf(score.getOffered()));
//            holder.tvPracticed.setText(String.valueOf(score.getPracticed()));
//            holder.tvAcquired.setText(String.valueOf(score.getAcquired()));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
