/************************************************************************************
 * Programmer   :   Michael Terry                                                   *
 * Project      :   MTerry_Inventory                                                *
 * Created Date :   September 2023 - Original (Version 1.0)                         *
 * Description  :   This app is designed to show proficiency with CRUD database     *
 *                  operations in the form of an inventory app. Upon logging in,    *
 *                  a user will be able to add, modify, and delete items. On the    *
 *                  app home page, the user will be able to sort the database       *
 *                  items by clicking on any of the listed categories. This         *
 *                  version of the app does not contain a user database to allow    *
 *                  for user account creation and authentication.                   *
 *                                                                                  *
 * Revisions    :   Version 1.1  - 4/10/2024                                        *
 *              :   This revision will add a user database to allow for user        *
 *              :   account creation and authentication. The toolbar was also       *
 *              :   updated to list the menu options. A sign out feature was added  *
 *              :   to confirm a users desire to logout of their account            *
 ************************************************************************************/
// app package
package com.cs360.mterry_inventory;

// Imported Android Libraries
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.database.Cursor;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*******************************************************************************
 *                             MainActivity Page                               *
 * This is the first page that opens when the app is started. It is the login  *
 * page and will display the app logo, text fields for email address and       *
 * password, and an option to create a new user account.
 *******************************************************************************/
public class MainActivity extends AppCompatActivity implements OnClickListener {
    // Global variables and object. Accessible throughout page runtime
    AppDatabaseHelper db = new AppDatabaseHelper(MainActivity.this);
    EditText email, password;
    Button loginBtn;

    // OnCreate method that runs when the app page is loaded
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // "Create New User" button. Set on-click function
        TextView newAccount = findViewById(R.id.createAccount);
        newAccount.setOnClickListener(this);

        // "Login" button. Set on-click function and disabled
        loginBtn = findViewById(R.id.submitButton);
        loginBtn.setOnClickListener(this);
        loginBtn.setEnabled(false);

        // set email and password text fields. Set text change function
        email = findViewById(R.id.userEmailAddress);
        email.addTextChangedListener(textWatcher);
        password = findViewById(R.id.userPassword);
        password.addTextChangedListener(textWatcher);
    }
    // On-click function which will detail operations when objects are pressed
    @Override
    public void onClick(View v) {
        // object to receive clicked input
        int itemID = v.getId();
        //if "Create Account" is pressed, call function
        if (itemID == R.id.createAccount)
            openNewAccount();
        // If "Login" is pressed
        else if (itemID == R.id.submitButton) {
            // user email and password are queried for authentication.
            // Query results are stored on cursor
            Cursor cursor = db.readUser(email.getText().toString().trim(),
                    password.getText().toString().trim() );
            // Text fields are cleared
            email.getText().clear();
            password.getText().clear();
            // If cursor has 1 row, query returned 1 and access will be granted
            if (cursor.getCount() == 1) {

                Toast.makeText(this, "Access Granted", Toast.LENGTH_SHORT).show();
                openHomePage();
            }
            // If cursor is not 1, access denied
            else {
                Toast.makeText(this, "User Not Found. Please Try Again",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    // textwatcher object functions. will monitor text changes to assigned text fields
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        // function will run when text in the text field changes
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // when text in the email and password text fields changes, login button is visible
            String email = MainActivity.this.email.getText().toString().trim();
            String password = MainActivity.this.password.getText().toString().trim();

            loginBtn.setEnabled(!email.isEmpty() && !password.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    // open new user account page
    public void openNewAccount() {
        Intent newUserPage = new Intent(MainActivity.this, NewUserAccount.class);
        startActivity(newUserPage);
    }
    // open app home page
    public void openHomePage() {
        Intent homePage = new Intent(MainActivity.this, InventoryHomePage.class);
        startActivity(homePage);
    }
}