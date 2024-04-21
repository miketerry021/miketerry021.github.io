package com.cs360.mterry_inventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewUserAccount extends AppCompatActivity {
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

        userFirstName = findViewById(R.id.firstName);
        userLastName = findViewById(R.id.lastName);
        userEmail = findViewById(R.id.username);
        userPassword = findViewById(R.id.password);
        newUserAdd = findViewById(R.id.createAccountButton);

        //noinspection Convert2Lambda
        newUserAdd.setOnClickListener(new View.OnClickListener() {
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
        });
    }
}