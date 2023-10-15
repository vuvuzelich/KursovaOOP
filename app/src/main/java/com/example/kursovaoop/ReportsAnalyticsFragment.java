package com.example.kursovaoop;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.util.List;

public class ReportsAnalyticsFragment extends Fragment {

    private RecyclerView recyclerView;
    private SalaryViewModel viewModel;
    private SalaryListAdapter adapter;
    private TextView resultTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports_analytics, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel = new ViewModelProvider(requireActivity()).get(SalaryViewModel.class);

        // Наблюдаем за изменениями в LiveData внутри ViewModel
        viewModel.getSalaryList().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> salaryList) {
                // Обновляем адаптер списка с новыми данными
                adapter = new SalaryListAdapter(salaryList);
                recyclerView.setAdapter(adapter);
            }
        });

        resultTextView = view.findViewById(R.id.resultTextView);

        return view;
    }

    public void displayResult(String result) {
        // Отображаем результат в текстовом поле
        resultTextView.setText("Заробітна плата: " + result + " грн");
    }
}



