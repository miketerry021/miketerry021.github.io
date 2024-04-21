/*****************************************
 * MainActivity is the driver for the app *
 ******************************************/
// app package
package com.cs360.mterry_inventory;

// Imported Android Libraries
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/************************************************************************
*                           AddItem Page                                *
* This is the add items page. This page will load when the user         *
* selects Add Item from the hamburger menu. Once the user populates     *
* the text fields, the user input is queried into the database          *
*************************************************************************/
public class AddItem extends AppCompatActivity {
    // Global variables. Accessible throughout page runtime
    EditText newItemType, newItemMake, newItemModel, newItemLocation, newItemDepartment,
            newItemUser;
    Button newItemAdd;

    // OnCreate method that runs when the app page is loaded
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Declare toolbar object and set it to view
        Toolbar toolbar = findViewById(R.id.addItemToolbar);
        setSupportActionBar(toolbar);
        // Remove Title form toolbar and enable up button
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // initialize text field objects
        newItemType = findViewById(R.id.addItemType);
        newItemMake = findViewById(R.id.addItemMake);
        newItemModel = findViewById(R.id.addItemModel);
        newItemLocation = findViewById(R.id.addItemLocation);
        newItemDepartment = findViewById(R.id.addItemDepartment);
        newItemUser = findViewById(R.id.addItemUser);
        newItemAdd = findViewById(R.id.addButton);

        // on click function that will query user input to add to database
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
    // create menu option on toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.inventory_menu, menu);
        return true;
    }

}