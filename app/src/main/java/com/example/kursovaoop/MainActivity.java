package com.example.kursovaoop;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageButton firstButton;

    private ImageButton secondButton;
    public static final int MENU_ITEM_1_ID = 1;
    public static final int MENU_ITEM_2_ID = 2;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Инициализация кастомного Toolbar
        Toolbar toolbar = findViewById(R.id.customToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Инициализация ImageButton
        firstButton = findViewById(R.id.firstButton);
        secondButton = findViewById(R.id.secondButton);


        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // Перевірте, чи поточний фрагмент - це LoginFragment
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (currentFragment instanceof HomeFragment) {
                    // Приховати Toolbar
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().hide();
                    }
                    // Сховати secondButton
                    secondButton.setVisibility(View.GONE);
                } else if (currentFragment instanceof LoginFragment) {
                    // Показати Toolbar
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().show();
                    }
                    secondButton.setVisibility(View.GONE);
                } else {
                    // Показати Toolbar для інших фрагментів
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().show();
                    }
                    secondButton.setVisibility(View.VISIBLE);
                }
            }
        });







        // Установка слушателя нажатия на ImageButton
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Показать вложенное меню при нажатии на ImageButton
                showPopupMenu(v);
            }
        });


        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Перевірте, чи є фрагменти в back stack
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    // Повернутися до попереднього фрагмента
                    getSupportFragmentManager().popBackStack();
                } else {
                    // Якщо немає фрагментів в back stack, можна виконати інші дії, наприклад, закрити додаток
                    // Додайте код для інших дій, якщо back stack порожній
                }
            }
        });





        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
        }
    }

    // Метод для отображения вложенного меню
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.submenu); // Используйте ваш файл ресурсов для вложенного меню

        // Настройка обработчика нажатия для элементов вложенного меню
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Обработка нажатий на элементы меню
                switch (item.getItemId()) {
                    case MENU_ITEM_1_ID:
                        // Действия для menu_item_1
                        return true;
                    case MENU_ITEM_2_ID:
                        // Действия для menu_item_2
                        return true;
                    // Добавьте обработку других элементов меню, если необходимо
                    default:
                        return false;
                }
            }
        });



        // Отобразить вложенное меню
        popupMenu.show();
    }


}
