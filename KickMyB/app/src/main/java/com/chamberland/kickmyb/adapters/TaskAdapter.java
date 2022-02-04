package com.chamberland.kickmyb.adapters;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chamberland.kickmyb.R;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.chamberland.kickmyb.activities.ConsultActivity;
import com.chamberland.kickmyb.activities.CreateActivity;
import com.chamberland.kickmyb.activities.HomeActivity;
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
        private final TextView name;
        private final TextView elapsedTime;
        private final TextView dueDate;
        private final ProgressBar progress;
        private final TextView progressPercentage;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            name = (TextView) view.findViewById(R.id.taskName);
            elapsedTime = (TextView) view.findViewById(R.id.taskElapsedTime);
            dueDate = (TextView) view.findViewById(R.id.taskDueDate);
            progress = (ProgressBar) view.findViewById(R.id.taskProgressBar);
            progressPercentage = (TextView) view.findViewById(R.id.taskProgressPercentage);
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
        Task task = localDataSet.get(position);

        viewHolder.name.setText(task.getName());
        viewHolder.elapsedTime.setText(task.getFormattedTimeElapsed());
        viewHolder.dueDate.setText(task.getEndDateTime());
        viewHolder.progress.setProgress(task.getProgressPercentage());
        viewHolder.progressPercentage.setText(String.format("%s%%", task.getProgressPercentage()));

        viewHolder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), ConsultActivity.class);
            view.getContext().startActivity(i);
        });
    }
}
