/*****************************************
 * MainActivity is the driver for the app *
 ******************************************/
// app package
package com.cs360.mterry_inventory;

// Imported Android Libraries
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

/************************************************************************
 *                           UpdateItem Page                            *
 * This is the update and delete items page. This page will load when   *
 * the user selects Modify/Delete Item from the hamburger menu. On this *
 * page,the user will enter the item information and then select Update *
 * or Delete to based on their desire. The app returns to the home page *
 * after execution                                                      *
 ************************************************************************/
public class UpdateItem extends AppCompatActivity {
    // Global variables. Accessible throughout page runtime
    EditText typeDisplay, makeDisplay, modelDisplay, locationDisplay, departmentDisplay,
            userDisplay;
    Button updateItem, deleteItem;
    String id, type, make, model, location, department, user;

    // OnCreate method that runs when the app page is loaded
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        // Declare toolbar object and set it to view
        Toolbar toolbar = findViewById(R.id.updateToolbar);
        setSupportActionBar(toolbar);
        // Remove Title form toolbar and enable up button
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // initialize text field objects
        typeDisplay = findViewById(R.id.updateItemType);
        makeDisplay = findViewById(R.id.updateItemMake);
        modelDisplay = findViewById(R.id.updateItemModel);
        locationDisplay = findViewById(R.id.updateItemLocation);
        departmentDisplay = findViewById(R.id.updateItemDepartment);
        userDisplay = findViewById(R.id.updateItemUser);
        updateItem = findViewById(R.id.updateButton);
        deleteItem = findViewById(R.id.deleteButton);

        // function call
        getItemData();

        // on click function that will query user input to update or delete item on database
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
    // create menu option on toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.inventory_menu, menu);
        return true;
    }
    // when menu items are selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuAddItem) {
            Intent addItemPage = new Intent(UpdateItem.this, AddItem.class);
            startActivity(addItemPage);
        }
        return super.onOptionsItemSelected(item);
    }
    // function will query the database and return the item
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
    // function for dialog bx to confirm user wants to delete item
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