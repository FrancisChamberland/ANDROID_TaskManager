package com.chamberland.kickmyb.adapters;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chamberland.kickmyb.R;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.chamberland.kickmyb.activities.ConsultActivity;
import com.chamberland.kickmyb.http.RetrofitUtil;
import com.chamberland.kickmyb.http.Service;
import com.chamberland.kickmyb.transfer.Task;
import com.chamberland.kickmyb.utils.DateFormatter;
import com.google.gson.Gson;

import org.kickmyb.transfer.TaskDetailResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> localDataSet;
    private Service service;
    private TaskDetailResponse taskDetailResponse;

    public TaskAdapter() {
        service = RetrofitUtil.get();
        localDataSet = new ArrayList<>();
    }

    public void add(Task task) {
        localDataSet.add(task);
        notifyItemChanged(localDataSet.size() - 1);
    }

    public void set(List<Task> tasks){
        localDataSet = tasks;
        notifyDataSetChanged();
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

        viewHolder.name.setText(task.name);
        viewHolder.elapsedTime.setText(String.format("%s%%", task.percentageTimeSpent));
        viewHolder.dueDate.setText(DateFormatter.getFormatted(task.deadline, "yyyy-MM-dd"));
        viewHolder.progressPercentage.setText(String.format("%s%%", task.percentageDone));

        viewHolder.itemView.setOnClickListener(view -> {
            requestTaskDetail(task.id);
            if (taskDetailResponse == null) return;
            Gson gson = new Gson();
            Intent i = new Intent(view.getContext(), ConsultActivity.class);
            i.putExtra("task", gson.toJson(taskDetailResponse));
            view.getContext().startActivity(i);
        });
    }

    private void requestTaskDetail(long id){
        service.detail(id).enqueue(new Callback<TaskDetailResponse>() {
            @Override
            public void onResponse(Call<TaskDetailResponse> call, Response<TaskDetailResponse> response) {
                if (response.isSuccessful()){
                    Log.i("DETAIL", "Response is successful");
                    taskDetailResponse = response.body();
                } else {
                    Log.i("DETAIL", "Response is not successful");
                }
            }
            @Override
            public void onFailure(Call<TaskDetailResponse> call, Throwable t) {
                Log.i("DETAIL", "Resquest failed");
            }
        });
    }
}
