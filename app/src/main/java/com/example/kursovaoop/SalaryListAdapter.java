package com.example.kursovaoop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SalaryListAdapter extends RecyclerView.Adapter<SalaryListAdapter.SalaryViewHolder> {

    private List<String> salaryList;

    public SalaryListAdapter(List<String> salaryList) {
        this.salaryList = salaryList;
    }

    @NonNull
    @Override
    public SalaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_salary, parent, false);
        return new SalaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalaryViewHolder holder, int position) {
        String salary = salaryList.get(position);
        holder.salaryTextView.setText("Заработная плата: " + salary + " грн");
    }

    @Override
    public int getItemCount() {
        return salaryList.size();
    }

    public static class SalaryViewHolder extends RecyclerView.ViewHolder {
        TextView salaryTextView;

        public SalaryViewHolder(@NonNull View itemView) {
            super(itemView);
            salaryTextView = itemView.findViewById(R.id.salaryTextView);
        }
    }
}
