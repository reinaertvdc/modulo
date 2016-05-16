package be.lambdaware.modulomobile.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import be.lambdaware.modulomobile.R;
import be.lambdaware.modulomobile.models.Task;

/**
 * Created by Hendrik on 13/05/2016.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case

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

        private Drawable iconLess;
        private Drawable iconMore;

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

            this.iconLess = ResourcesCompat.getDrawable(parent.getResources(), R.drawable.ic_expand_less_black_24dp, null);
            this.iconMore = ResourcesCompat.getDrawable(parent.getResources(), R.drawable.ic_expand_more_black_24dp, null);

            this.tvRemarksHeader.setOnClickListener(this);
            this.tvDetailsHeader.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (v == tvRemarksHeader) {
                if (remarksCollapsed) {
                    tvRemarksHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_expand_less_black_24dp, 0, 0, 0);
                    tvRemarks.setVisibility(View.VISIBLE);
                } else {
                    tvRemarksHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_expand_more_black_24dp, 0, 0, 0);
                    tvRemarks.setVisibility(View.GONE);
                }
                remarksCollapsed = !remarksCollapsed;
            } else if (v == tvDetailsHeader) {
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
        // set view layouting here.

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = data.get(position);

        if (task != null) {
            int colorRed = ResourcesCompat.getColor(holder.parent.getResources(), R.color.colorRed, null);
            int colorGreen = ResourcesCompat.getColor(holder.parent.getResources(), R.color.colorGreen, null);

            holder.tvTaskName.setText(task.getName());
            holder.tvTaskDate.setText(dateFormat.format(task.getDeadLine()));
            if (task.getStatus() == Task.TaskStatus.GRADED) {
                holder.tvTaskScore.setText("Score: " + task.getScore());
                holder.parent.setBackgroundColor(colorGreen);
            } else if (task.getStatus() == Task.TaskStatus.EMPTY) {
                holder.tvTaskScore.setText("Geen inzending");
                holder.parent.setBackgroundColor(colorRed);
            } else {
                holder.parent.setBackgroundColor(colorGreen);
            }
            holder.tvRemarks.setText(task.getRemarks());
            holder.tvDetails.setText(task.getDescription());


        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
