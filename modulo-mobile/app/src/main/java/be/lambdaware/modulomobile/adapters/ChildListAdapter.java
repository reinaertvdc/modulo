package be.lambdaware.modulomobile.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import be.lambdaware.modulomobile.R;
import be.lambdaware.modulomobile.models.Task;
import be.lambdaware.modulomobile.models.User;

/**
 * Created by Hendrik on 13/05/2016.
 */
public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public View parent;

        public TextView tvName;
        public TextView tvEmail;
        public CheckBox cbSelect;

        public ViewHolder(View parent) {
            super(parent);

            this.parent = parent;
            this.tvName = (TextView) parent.findViewById(R.id.tv_name);
            this.tvEmail = (TextView) parent.findViewById(R.id.tv_email);
            this.cbSelect = (CheckBox) parent.findViewById(R.id.cb_select);
        }

    }

    private Context context;
    private List<User> data;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ChildListAdapter(Context context, List<User> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_select_layout, parent, false);
        // set view layouting here.

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = data.get(position);

        if (user != null) {
            holder.tvName.setText(user.getFirstName() + " " + user.getLastName());
            holder.tvEmail.setText(user.getEmail());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
