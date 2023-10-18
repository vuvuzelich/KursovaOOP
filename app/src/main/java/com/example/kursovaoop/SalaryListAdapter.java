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
    private int lastItemTextColor;

    public SalaryListAdapter(List<String> salaryList, int lastItemTextColor) {
        this.salaryList = salaryList;
        this.lastItemTextColor = lastItemTextColor;
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
        holder.bind(salary);
        if (position == getItemCount() - 1) {
            holder.salaryTextView.setTextColor(lastItemTextColor);
        }
    }

    @Override
    public int getItemCount() {
        return salaryList.size();
    }

    public class SalaryViewHolder extends RecyclerView.ViewHolder {

        private TextView salaryTextView;

        public SalaryViewHolder(@NonNull View itemView) {
            super(itemView);
            salaryTextView = itemView.findViewById(R.id.salaryTextView);
        }

        public void bind(String salary) {
            salaryTextView.setText("Заробітна плата: " + salary);
        }
    }
}

