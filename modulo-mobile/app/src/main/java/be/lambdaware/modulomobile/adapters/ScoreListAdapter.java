package be.lambdaware.modulomobile.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

import be.lambdaware.modulomobile.R;
import be.lambdaware.modulomobile.models.Score;

/**
 * Author: Hendrik Lievens
 * Date: 13/05/2016
 * UHasselt / Software Engineering / 2015 - 2016
 */
public class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.ViewHolder> {

    /**
     * ViewHolder class for this adapter. This is a class that contains the views for each record
     * in the main list.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

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

    private Context context;
    private List<Score> data;

    public ScoreListAdapter(Context context, List<Score> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Score score = data.get(position);
        if (score != null) {
            holder.tvSubCertificate.setText(score.getName());
            holder.tvTotalCount.setText(String.valueOf(score.getTotalCompetences()));
            holder.tvPassedScore.setText(String.valueOf(score.getTotalPassed()));
            holder.tvFailedScore.setText(String.valueOf(score.getTotalFailed()));
            // rest the chart
            holder.pcChart.clearChart();
            // add data to chart
            holder.pcChart.addPieSlice(new PieModel(context.getString(R.string.passed), score.getPassedPercentage(), ContextCompat.getColor(context, R.color.colorGreen)));
            holder.pcChart.addPieSlice(new PieModel(context.getString(R.string.failed), score.getFailedPercentage(), ContextCompat.getColor(context, R.color.colorRed)));
            // start the animation
            holder.pcChart.startAnimation();
            holder.pcChart.setCurrentItem(0);
            // don't use rotate feature to change slices
            holder.pcChart.setUsePieRotation(false);
            // use on click to switch between slices
            holder.pcChart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PieChart chart = (PieChart) v;
                    chart.setCurrentItem(chart.getCurrentItem() == 1 ? 0 : 1);
                }
            });
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
