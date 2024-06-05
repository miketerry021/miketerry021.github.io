package com.cs360.mterry_inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class InventoryHomePage extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_home_page);

        // Declare toolbar object and set it to view
        Toolbar toolbar = findViewById(R.id.homePageToolbar);
        setSupportActionBar(toolbar);
        // Remove Title form toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //
        Button typeBtn = findViewById(R.id.typeButton);
        typeBtn.setOnClickListener(this);
        //
        Button makeBtn = findViewById(R.id.makeButton);
        makeBtn.setOnClickListener(this);
        //
        Button modelBtn = findViewById(R.id.modelButton);
        modelBtn.setOnClickListener(this);
        //
        Button locationBtn = findViewById(R.id.locationButton);
        locationBtn.setOnClickListener(this);
        //
        Button departmentBtn = findViewById(R.id.departmentButton);
        departmentBtn.setOnClickListener(this);
        //
        Button userBtn = findViewById(R.id.userButton);
        userBtn.setOnClickListener(this);

        // declare checkbox object
        CheckBox textNotify = findViewById(R.id.textNotification);

        //noinspection Convert2Lambda
        textNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    textDialog(textNotify);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.inventory_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuAddItem) {
            Intent addItemPage = new Intent(InventoryHomePage.this, AddItem.class);
            startActivity(addItemPage);
        }

        return super.onOptionsItemSelected(item);
    }

    public void textDialog(CheckBox textNotify) {
        //noinspection Convert2Lambda
        new AlertDialog.Builder(this)
                .setTitle("Enable Text Notification")
                .setMessage("Checking the box will enable text notifications when inventory " +
                        "is below 1. Do you wish to continue?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textNotify.setChecked(true);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textNotify.setChecked(false);
                    }
                }).show();
    }

    @Override
    public void onClick(View v) {
        int btnID = v.getId();

        if (btnID == R.id.typeButton) {
            Intent itemDisplayPage = new Intent(InventoryHomePage.this, InventoryDisplay.class);
            itemDisplayPage.putExtra("KEY_SENDER", "type");
            startActivity(itemDisplayPage);
        }
        else if (btnID == R.id.makeButton) {
            Intent itemDisplayPage = new Intent(InventoryHomePage.this, InventoryDisplay.class);
            itemDisplayPage.putExtra("KEY_SENDER", "make");
            startActivity(itemDisplayPage);
        }
        else if (btnID == R.id.modelButton) {
            Intent itemDisplayPage = new Intent(InventoryHomePage.this, InventoryDisplay.class);
            itemDisplayPage.putExtra("KEY_SENDER", "model");
            startActivity(itemDisplayPage);
        }
        else if (btnID == R.id.locationButton) {
            Intent itemDisplayPage = new Intent(InventoryHomePage.this, InventoryDisplay.class);
            itemDisplayPage.putExtra("KEY_SENDER", "location");
            startActivity(itemDisplayPage);
        }
        else if (btnID == R.id.departmentButton) {
            Intent itemDisplayPage = new Intent(InventoryHomePage.this, InventoryDisplay.class);
            itemDisplayPage.putExtra("KEY_SENDER", "department");
            startActivity(itemDisplayPage);
        }
        else if (btnID == R.id.userButton) {
            Intent itemDisplayPage = new Intent(InventoryHomePage.this, InventoryDisplay.class);
            itemDisplayPage.putExtra("KEY_SENDER", "user_assigned");
            startActivity(itemDisplayPage);
        }
    }
}