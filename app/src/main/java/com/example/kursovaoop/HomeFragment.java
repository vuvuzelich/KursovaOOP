package com.example.kursovaoop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Ініціалізація елементів інтерфейсу, включаючи fragmentTitleTextView
        TextView fragmentTitleTextView = view.findViewById(R.id.fragmentTitleTextView);
        TextView fullNameTextView = view.findViewById(R.id.fullNameTextView);
        TextView positionTextView = view.findViewById(R.id.positionTextView);
        TextView gradeTextView = view.findViewById(R.id.gradeTextView);
        LinearLayout manageEmployeesLayout = view.findViewById(R.id.manageEmployeesLayout);
        LinearLayout calculateSalariesLayout = view.findViewById(R.id.calculateSalarieLayout);
        LinearLayout reportsAnalyticsLayout = view.findViewById(R.id.reportsAnalyticsLayout);
        LinearLayout notificationsLayout = view.findViewById(R.id.notificationsLayout);
        LinearLayout adminFunctionsLayout = view.findViewById(R.id.adminFunctionsLayout);
        LinearLayout integrationLayout = view.findViewById(R.id.integrationLayout);


        Bundle args = getArguments();
        if (args != null) {
            String fullName = args.getString("fullName");
            String position = args.getString("position");
            String grade = args.getString("grade");

            fullNameTextView.setText(fullName);
            positionTextView.setText(position);
            gradeTextView.setText("Grade: " + grade);
        }


        manageEmployeesLayout.setOnClickListener(v -> {
            ManageEmployeesFragment manageEmployeesFragment = new ManageEmployeesFragment();
            openFragment(manageEmployeesFragment);
        });

        calculateSalariesLayout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("fragmentTitle", "Розрахунок Зарплати");
            CalculateSalariesFragment calculateSalariesFragment = new CalculateSalariesFragment();
            calculateSalariesFragment.setArguments(bundle);
            openFragment(calculateSalariesFragment);
        });

        reportsAnalyticsLayout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("fragmentTitle", "Звіти та Аналітика");
            ReportsAnalyticsFragment reportsAnalyticsFragment = new ReportsAnalyticsFragment();
            reportsAnalyticsFragment.setArguments(bundle);
            openFragment(reportsAnalyticsFragment);
        });

        notificationsLayout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("fragmentTitle", "Повідомлення та Нагадування");
            NotificationsFragment notificationsFragment = new NotificationsFragment();
            notificationsFragment.setArguments(bundle);
            openFragment(notificationsFragment);
        });

        adminFunctionsLayout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("fragmentTitle", "Додаткові Функції для Адміністраторів");
            AdminFunctionsFragment adminFunctionsFragment = new AdminFunctionsFragment();
            adminFunctionsFragment.setArguments(bundle);
            openFragment(adminFunctionsFragment);
        });

        integrationLayout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("fragmentTitle", "Інтеграція з Іншими Системами");
            IntegrationFragment integrationFragment = new IntegrationFragment();
            integrationFragment.setArguments(bundle);
            openFragment(integrationFragment);
        });




        return view;
    }



    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }





}
