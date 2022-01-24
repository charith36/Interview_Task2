package com.sliit.interviewapp1_t2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText name, email, contact,searchName;
    private Button buttonInsert, buttonView;
    private DBHelper DB;
    // define Regular expression to validate a email
    private String emailPattern = "^(.+)@(.+)$";
    AlertDialog.Builder alert1 = new AlertDialog.Builder(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiate required resources
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        searchName = findViewById(R.id.searchName);

        DB = new DBHelper(this);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if all fields are filled
                if (!isEmpty(name) && !isEmpty(email) && !isEmpty(contact)) {
                    // validate contact number
                    if (contactValidate(email)) {
                        // validate email
                        if (emailValidate(email)) {
                            // retrieve text from the text fields and assign to the following variables
                            String nameText = name.getText().toString();
                            String contactText = contact.getText().toString();
                            String emailText = email.getText().toString();

                            Boolean checkInsertData = DB.insertUserData(nameText, contactText, emailText);
                            // check if data is inserted correctly
                            if (checkInsertData) {
                                // provide a Toast message to indicate the user is added successfully
                                Toast.makeText(getApplicationContext(),"Added user successfully",Toast.LENGTH_SHORT).show();
                            }
                            // add Toast messages to indicate each validation or process failed.
                            else {
                                //Database insertion failed
                                Toast.makeText(getApplicationContext(),"Database insertion failed",Toast.LENGTH_SHORT).show();
                            }

                        } else {
                           //Email validation failed
                            Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //contact number validation failed
                        Toast.makeText(getApplicationContext(),"Invalid contact number",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //All fields are not filled
                    Toast.makeText(getApplicationContext(),"Please fill all fields",Toast.LENGTH_SHORT).show();
                }

            }
        });



        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if name to be search is filled
                if (!searchName.getText().toString().isEmpty()) {

                    Cursor res = DB.getUserData(searchName.getText().toString().trim());
                    //check if the entered user exists
                    if (res.getCount() == 0) {
                       // Toast message to indicate user does not exists
                        Toast.makeText(getApplicationContext(),"User does not exists",Toast.LENGTH_SHORT).show();
                        return;

                    } else {
                        StringBuffer buffer = new StringBuffer();
                        // iterate trough returned Cursor's fields append to the String buffer
                        while (res.moveToNext()) {
                            buffer.append("Name :" +res.getString(res.getColumnIndexOrThrow("name")));
                            buffer.append("\n");
                            buffer.append("Contact :" +res.getString(res.getColumnIndexOrThrow("contact")));
                            buffer.append("\n");
                            buffer.append("Email :" +res.getString(res.getColumnIndexOrThrow("email")));
                        }
                        // Implement a cancellable Alert Dialog box indicating details of the user
                        // Title as "User Details"
                        // Message  - content in the String buffer
                        alert1.setTitle("User Details");
                        alert1.setMessage(buffer);
                        alert1.setCancelable(true);
                    }
                }
                // clear text
                name.setText("");
                email.setText("");
                contact.setText("");
            }
        });
    }

    private boolean emailValidate(EditText email) {
        // check if a provided email is valid
        return Pattern.compile(emailPattern).matcher(email.getText().toString()).matches();
    }

    private boolean contactValidate(EditText contact) {
        // check if a number has 10 digits and starts with 0
        String phonePattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        return Pattern.compile(phonePattern).matcher(contact.getText().toString()).matches();
    }

    private boolean isEmpty(EditText text) {
        // implement method to check if a given field is empty
        return name.getText().toString().isEmpty();
    }
}