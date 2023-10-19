package com.example.kursovaoop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReportsAnalyticsFragment extends Fragment {

    private RecyclerView recyclerView;
    private SalaryViewModel viewModel;
    private SalaryListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports_analytics, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        viewModel = new ViewModelProvider(requireActivity()).get(SalaryViewModel.class);
        viewModel.loadSalaries(new DatabaseHelper(requireContext()));

        viewModel.getSalaryList().observe(getViewLifecycleOwner(), new Observer<List<Salary>>() {
            @Override
            public void onChanged(List<Salary> salaryList) {
                int lastItemPosition = salaryList.size() - 1;

                adapter = new SalaryListAdapter(salaryList);
                adapter.setLastItemPosition(lastItemPosition);
                recyclerView.setAdapter(adapter);
            }
        });





        return view;
    }
}




