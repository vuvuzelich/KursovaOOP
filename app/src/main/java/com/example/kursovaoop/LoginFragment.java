package com.example.kursovaoop;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextView, registerTextView2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);
        registerTextView = view.findViewById(R.id.registerTextView);
        registerTextView2 = view.findViewById(R.id.registerTextView2);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            // Получаем ссылку на Toolbar из активности
            Toolbar toolbar = activity.findViewById(R.id.customToolbar);
            // Устанавливаем Toolbar для фрагмента
            activity.setSupportActionBar(toolbar);
            // Скрываем заголовок в Toolbar
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                Log.d("LoginFragment", "Email: " + email + ", Password: " + password);

                // Перевірка користувача в базі даних
                DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

                // Перевірте, чи існує користувач з вказаним email
                if (dbHelper.userExists(email)) {
                    boolean isAuthenticated = dbHelper.authenticateUser(email, password);

                    if (isAuthenticated) {
                        // Отримуємо дані користувача з бази даних
                        User user = dbHelper.getUserByEmail(email);

                        // Передача даних користувача на HomeFragment
                        HomeFragment homeFragment = new HomeFragment();
                        Bundle args = new Bundle();
                        args.putString("fullName", user.getFullName());
                        args.putString("email", user.getEmail());
                        args.putString("grade", user.getGrade());
                        args.putString("position", user.getPosition());
                        homeFragment.setArguments(args);

                        // Перехід на HomeFragment
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, homeFragment)
                                .addToBackStack(null) // Додаємо транзакцію в back stack
                                .commit();


                    } else {
                        // Помилка автентифікації, показати сповіщення
                        Toast.makeText(requireContext(), "Невірний email або пароль", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Користувача не існує, показати сповіщення
                    Toast.makeText(requireContext(), "Користувача з таким email не існує", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Обробник натискання на текст "Register"
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Перехід на фрагмент реєстрації
                RegistrationFragment registrationFragment = new RegistrationFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, registrationFragment)
                        .addToBackStack(null) // Додаємо транзакцію в back stack
                        .commit();
            }
        });

        registerTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Перехід на фрагмент реєстрації
                RegistrationFragment registrationFragment = new RegistrationFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, registrationFragment)
                        .addToBackStack(null) // Додаємо транзакцію в back stack
                        .commit();
            }
        });

        return view;
    }



}

