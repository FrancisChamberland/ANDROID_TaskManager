package com.chamberland.kickmyb.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chamberland.kickmyb.R;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.chamberland.kickmyb.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> localDataSet;

    public TaskAdapter() {
        localDataSet = new ArrayList<>();
    }

    public void add(Task task) {
        localDataSet.add(task);
        notifyItemChanged(localDataSet.size() - 1);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

    }
}
