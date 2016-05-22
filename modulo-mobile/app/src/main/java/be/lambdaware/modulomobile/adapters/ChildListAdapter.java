package be.lambdaware.modulomobile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import be.lambdaware.modulomobile.R;
import be.lambdaware.modulomobile.activities.MainActivity;
import be.lambdaware.modulomobile.database.Database;
import be.lambdaware.modulomobile.models.User;

/**
 * Author: Hendrik Lievens
 * Date: 13/05/2016
 * UHasselt / Software Engineering / 2015 - 2016
 */
public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ViewHolder> {

    /**
     * ViewHolder class for this adapter. This is a class that contains the views for each record
     * in the main list.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        public View parent;
        public TextView tvName;
        public TextView tvEmail;
        public CheckBox cbSelect;

        private ChildListAdapter adapter;
        public int position;
        public boolean onBind;

        public ViewHolder(View parent, ChildListAdapter adapter) {
            super(parent);
            this.parent = parent;
            this.adapter = adapter;
            this.tvName = (TextView) parent.findViewById(R.id.tv_name);
            this.tvEmail = (TextView) parent.findViewById(R.id.tv_email);
            this.cbSelect = (CheckBox) parent.findViewById(R.id.cb_select);
            this.cbSelect.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // Fix to prevent invoking the onCheckedChanged while computing this view
            if (onBind) return;
            if (isChecked) {
                Database.setSelectedUser(position);
                adapter.notifyDataSetChanged();
                adapter.mainActivity.setUserInfoInHeader();
            }
        }
    }

    private Context context;
    private List<User> data;
    private MainActivity mainActivity;

    public ChildListAdapter(Context context, List<User> data, MainActivity mainActivity) {
        this.context = context;
        this.data = data;
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_select_layout, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        User user = data.get(position);

        if (user != null) {
            holder.position = position;
            holder.tvName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
            holder.tvEmail.setText(user.getEmail());

            // Fix to prevent invoking the onCheckedChanged while computing this view
            holder.onBind = true;
            holder.cbSelect.setChecked(user.isSelected());
            holder.onBind = false;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
