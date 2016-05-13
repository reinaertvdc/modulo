package be.lambdaware.modulomobile.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;

import java.util.ArrayList;

import be.lambdaware.modulomobile.R;
import be.lambdaware.modulomobile.models.SubCertificateScore;

/**
 * Created by Hendrik on 13/05/2016.
 */
public class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public View parent;

        public TextView tvSubCertificate;
        public TextView tvTotalCount;
        public TextView tvFailedScore;
        public TextView tvPassedScore;
        public PieChart pcChart;
        public TextView tvOffered;
        public TextView tvPracticed;
        public TextView tvAcquired;

        public ViewHolder(View parent) {
            super(parent);
            tvSubCertificate = (TextView) parent.findViewById(R.id.tv_sub_certificate);
            tvTotalCount = (TextView) parent.findViewById(R.id.tv_total_count);
            tvFailedScore = (TextView) parent.findViewById(R.id.tv_failed_score);
            tvPassedScore = (TextView) parent.findViewById(R.id.tv_passed_score);
            tvOffered = (TextView) parent.findViewById(R.id.tv_offered);
            tvPracticed = (TextView) parent.findViewById(R.id.tv_practiced);
            tvAcquired = (TextView) parent.findViewById(R.id.tv_acquired);
            pcChart = (PieChart) parent.findViewById(R.id.pc_chart);
        }
    }

    private ArrayList<SubCertificateScore> data;

    public ScoreListAdapter() {
        data = new ArrayList<>();
        data.add(new SubCertificateScore("Bekisting", 50, 20, 30, 25, 1, 24));
        data.add(new SubCertificateScore("Bekisting", 50, 20, 30, 25, 1, 24));
        data.add(new SubCertificateScore("Bekisting", 50, 20, 30, 25, 1, 24));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_layout, parent, false);
        // set view layouting here.

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SubCertificateScore score = data.get(position);

        if (score != null) {
            holder.tvSubCertificate.setText(score.getName());
            holder.tvTotalCount.setText(String.valueOf(score.getTotalCompetences()));
            holder.tvPassedScore.setText(String.valueOf(score.getTotalPassed()));
            holder.tvFailedScore.setText(String.valueOf(score.getTotalFailed()));
            //TODO update pie chart
            holder.tvOffered.setText(String.valueOf(score.getOffered()));
            holder.tvPracticed.setText(String.valueOf(score.getPracticed()));
            holder.tvAcquired.setText(String.valueOf(score.getAcquired()));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
