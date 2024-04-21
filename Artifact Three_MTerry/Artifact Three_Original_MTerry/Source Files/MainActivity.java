package com.cs360.mterry_inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView newAccount = findViewById(R.id.createAccount);
        newAccount.setOnClickListener(this);

        Button loginBtn = findViewById(R.id.submitButton);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int itemID = v.getId();
        AppDatabaseHelper userLoginTrue = null;
        boolean acceptedLogin;

        if (itemID == R.id.createAccount)
            openNewAccount();
        else if (itemID == R.id.submitButton) {
            openHomePage();
        }

    }

    public void openNewAccount() {
        Intent newUserPage = new Intent(MainActivity.this, NewUserAccount.class);
        startActivity(newUserPage);
    }
    public void openHomePage() {
        Intent homePage = new Intent(MainActivity.this, InventoryHomePage.class);
        startActivity(homePage);
    }


}