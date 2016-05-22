package be.lambdaware.modulomobile.adapters;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import be.lambdaware.modulomobile.R;
import be.lambdaware.modulomobile.models.Task;

/**
 * Author: Hendrik Lievens
 * Date: 13/05/2016
 * UHasselt / Software Engineering / 2015 - 2016
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    /**
     * ViewHolder class for this adapter. This is a class that contains the views for each record
     * in the main list.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public View parent;
        public TextView tvTaskName;
        public TextView tvTaskScore;
        public TextView tvTaskDate;
        public TextView tvRemarksHeader;
        public TextView tvDetailsHeader;
        public TextView tvRemarks;
        public TextView tvDetails;
        private boolean remarksCollapsed = true;
        private boolean detailsCollapsed = true;

        public ViewHolder(View parent) {
            super(parent);
            this.parent = parent;
            this.tvTaskName = (TextView) parent.findViewById(R.id.tv_task_name);
            this.tvTaskScore = (TextView) parent.findViewById(R.id.tv_task_score);
            this.tvTaskDate = (TextView) parent.findViewById(R.id.tv_task_date);
            this.tvRemarksHeader = (TextView) parent.findViewById(R.id.tv_remarks_header);
            this.tvDetailsHeader = (TextView) parent.findViewById(R.id.tv_details_header);
            this.tvRemarks = (TextView) parent.findViewById(R.id.tv_remarks);
            this.tvDetails = (TextView) parent.findViewById(R.id.tv_details);
            this.tvRemarksHeader.setOnClickListener(this);
            this.tvDetailsHeader.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Toggle remarks
            if (v == tvRemarksHeader) {
                if (remarksCollapsed) {
                    tvRemarksHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_expand_less_black_24dp, 0, 0, 0);
                    tvRemarks.setVisibility(View.VISIBLE);
                } else {
                    tvRemarksHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_expand_more_black_24dp, 0, 0, 0);
                    tvRemarks.setVisibility(View.GONE);
                }
                remarksCollapsed = !remarksCollapsed;
            }
            // Toggle details
            else if (v == tvDetailsHeader) {
                if (detailsCollapsed) {
                    tvDetailsHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_expand_less_black_24dp, 0, 0, 0);
                    tvDetails.setVisibility(View.VISIBLE);
                } else {
                    tvDetailsHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_expand_more_black_24dp, 0, 0, 0);
                    tvDetails.setVisibility(View.GONE);
                }
                detailsCollapsed = !detailsCollapsed;
            }
        }
    }

    private Context context;
    private List<Task> data;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public TaskListAdapter(Context context, List<Task> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = data.get(position);
        if (task != null) {
            // get the colors
            int colorRed = ResourcesCompat.getColor(holder.parent.getResources(), R.color.colorRed, null);
            int colorGreen = ResourcesCompat.getColor(holder.parent.getResources(), R.color.colorGreen, null);
            
            // set info
            holder.tvTaskName.setText(task.getName());
            holder.tvTaskDate.setText(dateFormat.format(task.getDeadLine()));
            holder.tvRemarks.setText(task.getRemarks());
            holder.tvDetails.setText(task.getDescription());

            // default to green color
            holder.parent.setBackgroundColor(colorGreen);
            // If graded, we show score and set color to green
            if (task.getStatus() == Task.TaskStatus.GRADED) {
                holder.tvTaskScore.setText(context.getString(R.string.score,task.getScore()));
            }
            // No uploads? Show in red color
            else if (task.getStatus() == Task.TaskStatus.EMPTY) {
                holder.tvTaskScore.setText(R.string.no_submissions);
                holder.parent.setBackgroundColor(colorRed);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
