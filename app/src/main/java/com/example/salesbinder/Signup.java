package com.example.salesbinder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Signup extends AppCompatActivity {
    Button btnRegister;

    EditText editTextName, editTextNumber, editTextPassword;
    private String USER_NAME, USER_NUMBER, USER_PASSWORD;

    FirebaseDatabase database;
    DatabaseReference dbRef;
    UserModel user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // initialize the features
        btnRegister = findViewById(R.id.register_button);
        editTextName = findViewById(R.id.register_name);
        editTextNumber = findViewById(R.id.register_number);
        editTextPassword = findViewById(R.id.register_password);





        // Db initialize
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();

        // button clicking
        btnRegister.setOnClickListener( view -> {
            // Getting Data from the user
                USER_NAME = editTextName.getText().toString().trim();
                USER_PASSWORD = editTextPassword.getText().toString().trim();
                USER_NUMBER = editTextNumber.getText().toString().trim();

            // Checking validity of UserData
                if(TextUtils.isEmpty(USER_NAME)){
                    editTextName.setError("User Name cann't be Blank");
                    return;
                }
                if(TextUtils.isEmpty(USER_NUMBER)){
                    editTextNumber.setError("Number is required");
                    return;

                }
                if (USER_NUMBER.length() != 11){
                    editTextNumber.setError("Your Number is wrong pls re-enter");
                    return;
                }
                if (TextUtils.isEmpty(USER_PASSWORD)){
                    editTextPassword.setError("Password cann't be empty");
                    return;
                }
                if(USER_PASSWORD.length() < 6){
                    editTextPassword.setError("Password cann't be less than 6 Characters");
                    return;
                }

            // creating a User
             user = new UserModel(USER_NAME, USER_NUMBER, USER_PASSWORD);


            //registering new user to db

                dbRef.child("Users").child(USER_NUMBER).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Registration Successful" , Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Authentication.class));
                        Toast.makeText(getApplicationContext(),"Please Log in now", Toast.LENGTH_SHORT).show();
                    }
                });



                // progress bar


        });
    }
}