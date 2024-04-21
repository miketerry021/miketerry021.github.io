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
import android.widget.EditText;
import android.widget.Toast;

public class UpdateItem extends AppCompatActivity {
    EditText typeDisplay, makeDisplay, modelDisplay, locationDisplay, departmentDisplay,
            userDisplay;
    Button updateItem, deleteItem;
    String id, type, make, model, location, department, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        //
        Toolbar toolbar = findViewById(R.id.updateToolbar);
        setSupportActionBar(toolbar);
        //
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        typeDisplay = findViewById(R.id.updateItemType);
        makeDisplay = findViewById(R.id.updateItemMake);
        modelDisplay = findViewById(R.id.updateItemModel);
        locationDisplay = findViewById(R.id.updateItemLocation);
        departmentDisplay = findViewById(R.id.updateItemDepartment);
        userDisplay = findViewById(R.id.updateItemUser);
        updateItem = findViewById(R.id.updateButton);
        deleteItem = findViewById(R.id.deleteButton);

        getItemData();
        updateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //noinspection resource
                AppDatabaseHelper db = new AppDatabaseHelper(UpdateItem.this);

                type = typeDisplay.getText().toString();
                make = makeDisplay.getText().toString();
                model = modelDisplay.getText().toString();
                location = locationDisplay.getText().toString();
                department = departmentDisplay.getText().toString();
                user = userDisplay.getText().toString();

                db.updateData(id, type, make, model, location, department, user);

                typeDisplay.getText().clear();
                makeDisplay.getText().clear();
                modelDisplay.getText().clear();
                locationDisplay.getText().clear();
                departmentDisplay.getText().clear();
                userDisplay.getText().clear();
            }
        });

        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();

                typeDisplay.getText().clear();
                makeDisplay.getText().clear();
                modelDisplay.getText().clear();
                locationDisplay.getText().clear();
                departmentDisplay.getText().clear();
                userDisplay.getText().clear();
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
            Intent addItemPage = new Intent(UpdateItem.this, AddItem.class);
            startActivity(addItemPage);
        }
        return super.onOptionsItemSelected(item);
    }

   void getItemData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("type") &&
                getIntent().hasExtra("make") && getIntent().hasExtra("model") &&
                getIntent().hasExtra("location") && getIntent().hasExtra("department")
                && getIntent().hasExtra("userAssigned")) {
            // get intent data

            id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("type");
            make = getIntent().getStringExtra("make");
            model = getIntent().getStringExtra("model");
            location = getIntent().getStringExtra("location");
            department = getIntent().getStringExtra("department");
            user = getIntent().getStringExtra("userAssigned");

            //set intent
            this.typeDisplay.setText(type);
            this.makeDisplay.setText(make);
            this.modelDisplay.setText(model);
            this.locationDisplay.setText(location);
            this.departmentDisplay.setText(department);
            this.userDisplay.setText(user);
        }
        else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Delete Item")
            .setMessage("Are you sure you want to delete item?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                id = getIntent().getStringExtra("id");
                AppDatabaseHelper db = new AppDatabaseHelper(UpdateItem.this);
                db.deleteData(id);
            }

        }).show();
    }
}