package com.cs360.mterry_inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.text.TextUtils;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    AppDatabaseHelper db;
    EditText userEmail, userPassword;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        userEmail = findViewById(R.id.userEmailAddress);
        userPassword = findViewById(R.id.userPassword);

        //
        TextView newAccount = findViewById(R.id.createAccount);
        newAccount.setOnClickListener(this);

        //
        loginBtn = findViewById(R.id.submitButton);
        loginBtn.setOnClickListener(this);

        //
        userEmail.addTextChangedListener(textWatcher);
        userPassword.addTextChangedListener(textWatcher);
    }

    @Override
    public void onClick(View v) {
        int itemID = v.getId();
        AppDatabaseHelper userLoginTrue = null;

        if (itemID == R.id.createAccount)
            openNewAccount();
        else if (itemID == R.id.submitButton) {
            //
            Cursor cursor = db.readUser(userEmail.getText().toString().trim(),
                    userPassword.getText().toString().trim());
            //
            userEmail.getText().clear();
            userPassword.getText().clear();

            if (cursor.getCount() == 1) {
                openHomePage();
            }
            else {
                Toast.makeText(this, "Invalid Username and/or Password",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //
            String email = userEmail.getText().toString().trim();
            String password = userPassword.getText().toString().trim();

            loginBtn.setEnabled(!email.isEmpty() && !password.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void openNewAccount() {
        Intent newUserPage = new Intent(MainActivity.this, NewUserAccount.class);
        startActivity(newUserPage);
    }
    public void openHomePage() {
        Intent homePage = new Intent(MainActivity.this, InventoryHomePage.class);
        startActivity(homePage);
    }


}