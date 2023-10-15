package com.example.kursovaoop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class CalculateSalariesFragment extends Fragment {

    private EditText editTextHoursWorked;
    private EditText editTextHourlyRate;
    private EditText editTextTaxes;
    private EditText editTextBonuses;
    private SalaryViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate_salaries, container, false);

        Bundle args = getArguments();
        if (args != null) {
            String fragmentTitle = args.getString("fragmentTitle");
            TextView fragmentTitleTextView = view.findViewById(R.id.fragmentTitleTextView);
            fragmentTitleTextView.setText(fragmentTitle);
        }

        editTextHoursWorked = view.findViewById(R.id.editTextHoursWorked);
        editTextHourlyRate = view.findViewById(R.id.editTextHourlyRate);
        editTextTaxes = view.findViewById(R.id.editTextTaxes);
        editTextBonuses = view.findViewById(R.id.editTextBonuses);

        viewModel = new ViewModelProvider(requireActivity()).get(SalaryViewModel.class);

        Button calculateButton = view.findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSalary();
            }
        });

        // Отримуємо переданий текст з аргументів
        String fragmentTitle = getArguments().getString("fragmentTitle");

        // Встановлюємо отриманий текст як заголовок фрагмента
        TextView fragmentTitleTextView = view.findViewById(R.id.fragmentTitleTextView);
        fragmentTitleTextView.setText(fragmentTitle);

        return view;
    }

    private void calculateSalary() {
        String hoursWorkedStr = editTextHoursWorked.getText().toString();
        String hourlyRateStr = editTextHourlyRate.getText().toString();
        String taxesStr = editTextTaxes.getText().toString();
        String bonusesStr = editTextBonuses.getText().toString();

        // Перевіряємо, чи введена кома, і конвертуємо її в крапку
        hoursWorkedStr = hoursWorkedStr.replace(",", ".");
        hourlyRateStr = hourlyRateStr.replace(",", ".");
        taxesStr = taxesStr.replace(",", ".");
        bonusesStr = bonusesStr.replace(",", ".");

        if (!hoursWorkedStr.isEmpty() && !hourlyRateStr.isEmpty() && !taxesStr.isEmpty() && !bonusesStr.isEmpty()) {
            try {
                double hoursWorked = Double.parseDouble(hoursWorkedStr);
                double hourlyRate = Double.parseDouble(hourlyRateStr);
                double taxes = Double.parseDouble(taxesStr);
                double bonuses = Double.parseDouble(bonusesStr);

                double salaryBeforeTax = hoursWorked * hourlyRate + bonuses;
                double taxedSalary = salaryBeforeTax * (1 - taxes / 100);

                // Форматуємо зарплату, щоб вивести лише дві цифри після коми
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String result = decimalFormat.format(taxedSalary);

                viewModel.addSalary(result); // Додаємо зарплату в список

                // Отображаємо повний розрахунок в TextView
                TextView resultTextView = requireActivity().findViewById(R.id.resultTextView);
                resultTextView.setText(result + " ₴грн");
            } catch (NumberFormatException e) {
                // Якщо введені некоректні дані, показуємо повідомлення про помилку
                TextView resultTextView = requireActivity().findViewById(R.id.resultTextView);
                resultTextView.setText("Введено некоректні дані.");
            }
        } else {
            // Якщо введені невірні дані, показуємо повідомлення про помилку
            TextView resultTextView = requireActivity().findViewById(R.id.resultTextView);
            resultTextView.setText("Введено некоректні дані.");
        }
    }



}



