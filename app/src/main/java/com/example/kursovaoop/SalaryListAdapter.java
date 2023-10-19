package com.example.kursovaoop;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SalaryListAdapter extends RecyclerView.Adapter<SalaryListAdapter.SalaryViewHolder> {

    private List<Salary> salaryList;
    private int lastItemPosition = -1;

    public SalaryListAdapter(List<Salary> salaryList) {
        this.salaryList = salaryList;
    }

    public void setLastItemPosition(int lastItemPosition) {
        this.lastItemPosition = lastItemPosition;
    }

    @NonNull
    @Override
    public SalaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_salary, parent, false);
        return new SalaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalaryViewHolder holder, int position) {
        Salary salaryObject = salaryList.get(position);
        String salaryAmount = salaryObject.getAmount();
        String name = salaryObject.getFirstName() + " " + salaryObject.getLastName();
        holder.bind(salaryAmount, name);

        if (position == lastItemPosition) {
            // Change background for the last item
            holder.cardSalary.setBackgroundResource(R.drawable.card_color_background);
        } else {
            // Reset background for other items
            holder.cardSalary.setBackgroundResource(R.drawable.gradient_edit_text);
        }
    }

    @Override
    public int getItemCount() {
        return salaryList.size();
    }

    public class SalaryViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout cardSalary;
        private TextView salaryTextView;
        private TextView nameCustomerTextView;

        public SalaryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardSalary = itemView.findViewById(R.id.cardSalary);
            salaryTextView = itemView.findViewById(R.id.salaryTextView);
            nameCustomerTextView = itemView.findViewById(R.id.nameCustomerTextView);
        }

        public void bind(String salary, String name) {
            salaryTextView.setText(salary);
            nameCustomerTextView.setText(name);
        }
    }
}


