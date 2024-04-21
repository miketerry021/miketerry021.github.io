/*****************************************
* MainActivity is the driver for the app *
******************************************/
// app package
package com.cs360.mterry_inventory;
// Imported Android Libraries
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/****************************************************************************
 *                          NewUserAccount Page                             *
 * This page will allow a user to create a new account. It contains text    *
 * fields for first name, last name, email address and password.            *
*****************************************************************************/
public class NewUserAccount extends AppCompatActivity {
    //Global variables. Accessible throughout page runtime
    EditText userFirstName, userLastName, userEmail, userPassword;
    Button newUserAdd;

    // OnCreate method that runs when the app page is loaded
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_account);

        // Create toolbar object and set in the pages view
        Toolbar toolbar = findViewById(R.id.newUserToolbar);
        setSupportActionBar(toolbar);
        // disable the default toolbar and display the created one
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // set global variable to text fields
        userFirstName = findViewById(R.id.firstName);
        userLastName = findViewById(R.id.lastName);
        userEmail = findViewById(R.id.username);
        userPassword = findViewById(R.id.password);
        newUserAdd = findViewById(R.id.createAccountButton);
        // add text listeners
        userFirstName.addTextChangedListener(textWatcher);
        userLastName.addTextChangedListener(textWatcher);
        userEmail.addTextChangedListener(textWatcher);
        userPassword.addTextChangedListener(textWatcher);
        // disable Create Account button
        newUserAdd.setEnabled(false);

        // On-clink function for the Create Account button
        //noinspection Convert2Lambda
        newUserAdd.setOnClickListener(new View.OnClickListener() {
            // When pressed, user input data will be sent to the database as a query to create account
            // Text fields are then cleared and user is returned to login page
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

                Intent homePage = new Intent(NewUserAccount.this, MainActivity.class);
                startActivity(homePage);
            }
        });
    }
    // textwatcher object functions. will monitor text changes to assigned text fields
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // when text all text fields changes, create button is visible
            String firstName = userFirstName.getText().toString().trim();
            String lastName = userLastName.getText().toString().trim();
            String email = userEmail.getText().toString().trim();
            String password = userPassword.getText().toString().trim();

            newUserAdd.setEnabled(!firstName.isEmpty() && !lastName.isEmpty() &&
                    !email.isEmpty() && !password.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}