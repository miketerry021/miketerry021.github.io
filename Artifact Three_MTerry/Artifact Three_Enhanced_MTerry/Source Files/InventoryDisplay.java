/*****************************************
 * MainActivity is the driver for the app *
 ******************************************/
// app package
package com.cs360.mterry_inventory;

// Imported Android Libraries
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;

/****************************************************************************
 *                          InventoryDisplay Page                           *
 * This page will display all the item in the database based on a specific  *
 * criteria determined by the user from the InventoryHomePage. The page     *
 * also utilize the CustomAdapter.java file to display the items.           *
*****************************************************************************/

/*@noinspection ALL*/
public class InventoryDisplay extends AppCompatActivity {
    // Global variables and object. Accessible throughout page runtime
    AppDatabaseHelper db = new AppDatabaseHelper(InventoryDisplay.this);
    RecyclerView rView;
    ArrayList<String> itemID, itemType, itemMake, itemModel, itemLocation, itemDepartment,
            itemUserAssigned;
    CustomAdapter customAdapter;

    // OnCreate method that runs when the app page is loaded
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_display);

        // Declare toolbar object and set it to view
        Toolbar toolbar = findViewById(R.id.itemsDisplayToolbar);
        setSupportActionBar(toolbar);

        // Remove Title form toolbar and enable up button
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // attached object to recycler object
        rView = findViewById(R.id.displayItems);

        // receive search category
        Intent receiveIntent = getIntent();
        String receiveCategory = receiveIntent.getStringExtra("KEY_SENDER");

        // initialize array objects
        itemID = new ArrayList<String>();
        itemType = new ArrayList<String>();
        itemMake = new ArrayList<String>();
        itemModel = new ArrayList<String>();
        itemLocation = new ArrayList<String>();
        itemDepartment = new ArrayList<String>();
        itemUserAssigned = new ArrayList<String>();

        // call displayDate function with selected category as argument
        displayData(receiveCategory);

        // initialize customAdapter with array objects and set to recycler object
        customAdapter = new CustomAdapter(InventoryDisplay.this,this, itemID, itemType, itemMake,
                itemModel, itemLocation, itemDepartment, itemUserAssigned);
        rView.setAdapter(customAdapter);
        rView.setLayoutManager(new LinearLayoutManager(InventoryDisplay.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0)
            recreate();
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
            Intent addItemPage = new Intent(InventoryDisplay.this, AddItem.class);
            startActivity(addItemPage);
        }
        return super.onOptionsItemSelected(item);
    }
    // displayData function will query the database for item data and store in cursor.
    // if cursor has data, it is displayed. If not, will display "no data" message
    public void displayData(String receiveCategory) {
        Cursor cursor = db.readData(receiveCategory);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                this.itemID.add(cursor.getString(0));
                this.itemType.add(cursor.getString(1));
                this.itemMake.add(cursor.getString(2));
                this.itemModel.add(cursor.getString(3));
                this.itemLocation.add(cursor.getString(4));
                this.itemDepartment.add(cursor.getString(5));
                this.itemUserAssigned.add(cursor.getString(6));

            }
        }
    }
}