package com.example.kursovaoop;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class InfoFragment extends Fragment {

    private SharedViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        // Инициализация элементов интерфейса с правильными идентификаторами
        TextView infoFullNameTextView = view.findViewById(R.id.fullNameTextView);
        TextView infoPositionTextView = view.findViewById(R.id.positionTextView);
        TextView infoGradeTextView = view.findViewById(R.id.gradeTextView);
        TextView infoEmailTextView = view.findViewById(R.id.emailTextView);

        // Подписка на изменения данных пользователя
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            // Обновление UI с новыми данными из объекта User
            infoFullNameTextView.setText(user.getFullName());
            infoPositionTextView.setText(user.getPosition());
            infoGradeTextView.setText("Grade: " + user.getGrade());
            infoEmailTextView.setText(user.getEmail());
        });

        return view;
    }
}



