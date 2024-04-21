/*****************************************
 * MainActivity is the driver for the app *
 ******************************************/
// app package
package com.cs360.mterry_inventory;

// Imported Android Libraries
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.DialogTitle;
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

/********************************************************************************
 *                           InventoryHomePage                                  *
 * This page is the main page of the app once the user successfully logins. On  *
 * the page, the user can click on the hamburger button and will be given the   *
 * option to add items, modify or delete items, and sign out. After items have  *
 * been added, the user can select one of the listed categories to open a new   *
 * page and display all items in alphabetical order based on that category.     *
 ********************************************************************************/

public class InventoryHomePage extends AppCompatActivity implements View.OnClickListener {
    // OnCreate method that runs when the app page is loaded
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_home_page);

        // Declare toolbar object and set it to view
        Toolbar toolbar = findViewById(R.id.homePageToolbar);
        setSupportActionBar(toolbar);
        // Remove Title form toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // declare buttons and set on click listeners
        Button typeBtn = findViewById(R.id.typeButton);
        typeBtn.setOnClickListener(this);

        Button makeBtn = findViewById(R.id.makeButton);
        makeBtn.setOnClickListener(this);

        Button modelBtn = findViewById(R.id.modelButton);
        modelBtn.setOnClickListener(this);

        Button locationBtn = findViewById(R.id.locationButton);
        locationBtn.setOnClickListener(this);

        Button departmentBtn = findViewById(R.id.departmentButton);
        departmentBtn.setOnClickListener(this);

        Button userBtn = findViewById(R.id.userButton);
        userBtn.setOnClickListener(this);

        // declare checkbox object
        CheckBox textNotify = findViewById(R.id.textNotification);

        // listener to check when checkbox is changed. calls textDialog function
        //noinspection Convert2Lambda
        textNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    textDialog(textNotify);
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
        // if add item is selected, open add item page
        if (itemId == R.id.menuAddItem) {
            Intent addItemPage = new Intent(InventoryHomePage.this, AddItem.class);
            startActivity(addItemPage);
        }
        //if modify/delete is selected, open update item page
        if (itemId == R.id.menuUpdateItem) {
            Intent modifyItemPage = new Intent(InventoryHomePage.this, UpdateItem.class);
            startActivity(modifyItemPage);
        }
        // if sign out is selected, call logoutDialog function
        if (itemId == R.id.menuSignOut) {
            //AlertDialog.Builder signOutMessage = new AlertDialog.Builder(InventoryHomePage.this);
            logoutDialog();
        }

        return super.onOptionsItemSelected(item);
    }
    // show alert box to confirm user sign out. If yes, app returns to login page.
    // if not, app stays on home page
    public void logoutDialog() {
        new AlertDialog.Builder(this).setTitle("Confirm Sign Out")
                .setMessage("Do you wish to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent signOut = new Intent(InventoryHomePage.this, MainActivity.class);
                        startActivity(signOut);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }
    // show alert box to confirm is user wants notifications
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
    // on click function. When users selects a category, that value is sent to the
    // InventoryDisplay page
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