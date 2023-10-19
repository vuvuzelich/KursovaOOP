package com.example.kursovaoop;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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

    private EditText editTextDate;

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextCardNumber;
    private EditText editTextBankAccount;
    private EditText editTextInsuranceContributions;
    private EditText editTextSocialBenefits;
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
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        editTextCardNumber = view.findViewById(R.id.editTextCardNumber);
        editTextBankAccount = view.findViewById(R.id.editTextBankAccount);
        editTextInsuranceContributions = view.findViewById(R.id.editTextInsuranceContributions);
        editTextSocialBenefits = view.findViewById(R.id.editTextSocialBenefits);

        // Додавання обробників подій для вибору дати
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

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
        // Отримання значень з полів введення
        String hoursWorkedStr = editTextHoursWorked.getText().toString();
        String hourlyRateStr = editTextHourlyRate.getText().toString();
        String taxesStr = editTextTaxes.getText().toString();
        String bonusesStr = editTextBonuses.getText().toString();
        String insuranceContributionsStr = editTextInsuranceContributions.getText().toString();
        String socialBenefitsStr = editTextSocialBenefits.getText().toString();

        // Перевірка, чи всі необхідні дані введені
        if (!hoursWorkedStr.isEmpty() && !hourlyRateStr.isEmpty() && !taxesStr.isEmpty() && !bonusesStr.isEmpty()
                && !insuranceContributionsStr.isEmpty() && !socialBenefitsStr.isEmpty()) {
            try {
                // Парсинг введених значень в числові типи
                double hoursWorked = Double.parseDouble(hoursWorkedStr.replace(",", "."));
                double hourlyRate = Double.parseDouble(hourlyRateStr.replace(",", "."));
                double taxes = Double.parseDouble(taxesStr.replace(",", "."));
                double bonuses = Double.parseDouble(bonusesStr.replace(",", "."));
                double insuranceContributions = Double.parseDouble(insuranceContributionsStr.replace(",", "."));
                double socialBenefits = Double.parseDouble(socialBenefitsStr.replace(",", "."));

                // Розрахунок зарплати до оподаткування та оподаткованої зарплати
                double salaryBeforeTax = hoursWorked * hourlyRate + bonuses;
                double taxedSalary = salaryBeforeTax * (1 - taxes / 100);

                // Розрахунок загальних витрат та загального прибутку
                double totalEarnings = taxedSalary + insuranceContributions + socialBenefits;
                double totalDeductions = insuranceContributions + socialBenefits;

                // Форматування результату та виведення його у текстове поле
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String result = decimalFormat.format(totalEarnings - totalDeductions) + " ₴грн";

                // Отримання значень інших полів, які можуть бути використані в майбутньому
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String cardNumber = editTextCardNumber.getText().toString();
                String bankAccount = editTextBankAccount.getText().toString();

                // Збереження зарплати в базі даних
                DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
                viewModel.addSalary(result, databaseHelper, firstName, lastName, cardNumber, bankAccount, insuranceContributions, socialBenefits);

                // Виведення результату у текстове поле
                TextView resultTextView = requireActivity().findViewById(R.id.resultTextView);
                resultTextView.setText(result);
            } catch (NumberFormatException e) {
                // Обробка винятку, якщо введені дані некоректні
                TextView resultTextView = requireActivity().findViewById(R.id.resultTextView);
                resultTextView.setText("Введено некоректні дані.");
            }
        } else {
            // Виведення повідомлення про помилку, якщо не всі дані введені
            TextView resultTextView = requireActivity().findViewById(R.id.resultTextView);
            resultTextView.setText("Будь ласка, заповніть всі поля.");
        }
    }



    private void showDatePickerDialog() {
        // Отримання поточної дати
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Створення та показ діалогу вибору дати
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Обробка вибраної дати
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editTextDate.setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }


}



