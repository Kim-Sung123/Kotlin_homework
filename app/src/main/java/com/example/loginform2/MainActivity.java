package com.example.loginform2;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginform2.databinding.ActivityMainBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.rootLayout.setOnClickListener(v -> {
            hideKeyboard();
        });

        Glide.with(this)
                .load(R.drawable.logo)
                .circleCrop()
                .into(binding.logoimage);

        binding.btnlogin.setOnClickListener(view -> {
            hideKeyboard();
            var message = "";

            message += "Email: " + binding.emailInput.getText().toString();
            message += "\nPassword: " + binding.passwordInput.getText().toString();

            Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", v -> {

                    }).show();

        });

        addTextInputListener();
    }


    private void validateButtonSubmit(){
        if (binding.emailInput.getText().toString().isEmpty() || binding.passwordInput.getText().toString().isEmpty()){
            binding.btnlogin.setEnabled(false);
        }else {
            binding.btnlogin.setEnabled(true);
        }
    }

    private void addTextInputListener() {
        TextWatcher handleTextChange = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateButtonSubmit();
            }
        };

        binding.emailInput.addTextChangedListener(handleTextChange);

        binding.passwordInput.addTextChangedListener(handleTextChange);
    }

    private void hideKeyboard() {
        // Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();

        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}