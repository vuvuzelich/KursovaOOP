package com.example.kursovaoop;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class RegistrationFragment extends Fragment {

    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText;
    private Spinner gradeSpinner;

    private Spinner positionSpinner;
    private String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        // Находимо елементи інтерфейсу
        firstNameEditText = view.findViewById(R.id.firstNameEditText);
        lastNameEditText = view.findViewById(R.id.lastNameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        gradeSpinner = view.findViewById(R.id.gradeSpinner);
        positionSpinner = view.findViewById(R.id.positionSpinner);
        Button registerButton = view.findViewById(R.id.registerButton);


        // Наповнюємо Spinner для вибору грейда
        ArrayAdapter<CharSequence> gradeAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.grade_array, android.R.layout.simple_spinner_item);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(gradeAdapter);

        // Наполняем Spinner для выбора позиции
        ArrayAdapter<CharSequence> positionAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.position_array, android.R.layout.simple_spinner_item);
        positionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        positionSpinner.setAdapter(positionAdapter);



        // Обробник кнопки "Зареєструватися"
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String grade = gradeSpinner.getSelectedItem().toString();
                String position = positionSpinner.getSelectedItem().toString();
                password = passwordEditText.getText().toString();



                // Зберігаємо користувача в базу даних, передаючи пароль
                saveUserToDatabase(firstName, lastName, email, grade, password, position);
            }
        });



        return view;
    }

    private void saveUserToDatabase(String firstName, String lastName, String email, String grade, String password, String position) {
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_FIRST_NAME, firstName);
        values.put(DatabaseHelper.COLUMN_LAST_NAME, lastName);
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);
        values.put(DatabaseHelper.COLUMN_GRADE, grade);
        values.put(DatabaseHelper.COLUMN_POSITION, position);

        // Вставте дані в базу даних
        long newRowId = db.insert(DatabaseHelper.TABLE_USERS, null, values);

        // Перевірте, чи дані були успішно збережені в базі даних
        if (newRowId != -1) {
            // Дані успішно збережені, показуємо сповіщення
            Toast.makeText(requireContext(), "Реєстрація успішна!", Toast.LENGTH_SHORT).show();

            // Повертаємось на фрагмент Login
            requireFragmentManager().popBackStack();
        } else {
            // Помилка при збереженні даних
            Toast.makeText(requireContext(), "Помилка реєстрації, спробуйте знову", Toast.LENGTH_SHORT).show();
        }

        // Закрийте з'єднання з базою даних
        db.close();
    }
}
