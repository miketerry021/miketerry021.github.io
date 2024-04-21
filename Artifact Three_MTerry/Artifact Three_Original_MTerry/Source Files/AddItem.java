package com.cs360.mterry_inventory;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItem extends AppCompatActivity {

    EditText newItemType, newItemMake, newItemModel, newItemLocation, newItemDepartment,
            newItemUser;
    Button newItemAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //
        Toolbar toolbar = findViewById(R.id.addItemToolbar);
        setSupportActionBar(toolbar);
        //
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newItemType = findViewById(R.id.addItemType);
        newItemMake = findViewById(R.id.addItemMake);
        newItemModel = findViewById(R.id.addItemModel);
        newItemLocation = findViewById(R.id.addItemLocation);
        newItemDepartment = findViewById(R.id.addItemDepartment);
        newItemUser = findViewById(R.id.addItemUser);
        newItemAdd = findViewById(R.id.addButton);

        //noinspection Convert2Lambda
        newItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //noinspection resource
                AppDatabaseHelper db = new AppDatabaseHelper(AddItem.this);
                db.addItem(newItemType.getText().toString().trim(),
                        newItemMake.getText().toString().trim(),
                        newItemModel.getText().toString().trim(),
                        newItemLocation.getText().toString().trim(),
                        newItemDepartment.getText().toString().trim(),
                        newItemUser.getText().toString().trim());

                newItemType.getText().clear();
                newItemMake.getText().clear();
                newItemModel.getText().clear();
                newItemLocation.getText().clear();
                newItemDepartment.getText().clear();
                newItemUser.getText().clear();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.inventory_menu, menu);
        return true;
    }

}