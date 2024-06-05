package com.cs360.mterry_inventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewUserAccount extends AppCompatActivity implements View.OnClickListener {
    EditText userFirstName, userLastName, userEmail, userPassword;
    Button newUserAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_account);

        //
        Toolbar toolbar = findViewById(R.id.newUserToolbar);
        setSupportActionBar(toolbar);
        //
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //
        newUserAdd.setOnClickListener(this);
        //
        userFirstName = findViewById(R.id.firstName);
        userLastName = findViewById(R.id.lastName);
        userEmail = findViewById(R.id.username);
        userPassword = findViewById(R.id.password);
        newUserAdd = findViewById(R.id.createAccountButton);
        //
        userFirstName.addTextChangedListener(textWatcher);
        userLastName.addTextChangedListener(textWatcher);
        userEmail.addTextChangedListener(textWatcher);
        userPassword.addTextChangedListener(textWatcher);
    }


    //noinspection Convert2Lambda
    @Override
    public void onClick(View v) {
        //noinspection resource
        AppDatabaseHelper db = new AppDatabaseHelper(NewUserAccount.this);
        db.addUser(userFirstName.getText().toString().trim(),
                userLastName.getText().toString().trim(),
                userEmail.getText().toString().trim(),
                userPassword.getText().toString().trim());

            userFirstName.getText().clear();
            userLastName.getText().clear();
            userEmail.getText().clear();
            userPassword.getText().clear();
        }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //
            String firstName = userFirstName.getText().toString().trim();
            String lastName = userLastName.getText().toString().trim();
            String email = userEmail.getText().toString().trim();
            String password = userPassword.getText().toString().trim();

            newUserAdd.setEnabled(!firstName.isEmpty() && !lastName.isEmpty() &&
                    !email.isEmpty() && !password.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}