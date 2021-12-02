package com.example.salesbinder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Authentication extends AppCompatActivity {
    private EditText usernameEditText,passwordEditText;
    private Button loginButton;
    private TextView textView;
    private int counter = 3;
    private Button signupButton;
    DatabaseReference db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);



        usernameEditText = (EditText) findViewById(R.id.usernameId);
        passwordEditText = (EditText) findViewById(R.id.passwordId);
        loginButton = (Button) findViewById(R.id.loginButtonId);
        textView = (TextView) findViewById(R.id.textViewId);
        textView.setText("Number of attempts remaining : 3");
        signupButton = (Button) findViewById(R.id.signupButtonId);
        signupButton.setPaintFlags(signupButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.equals("admin") && password.equals("1234"))
                {
                    Intent intent = new Intent(Authentication.this,MainActivity.class);
                    startActivity(intent);
                }
                else {
                        counter--;
                        textView.setText("Number of attempts remaining : " + counter);
                        if(counter==0){
                            loginButton.setEnabled(false);
                        }

                }
                /*
                    Username will be number

                 */

                //  validating the current user
//                db = FirebaseDatabase.getInstance().getReference("Users");
//                db.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.hasChild(username)){
//                            UserModel user = snapshot.child(username).getValue(UserModel.class);
//                            if(user.getPASSWORD().equals(password)){
//
//                                // put the info for setting profile in mainActivity
//
//                                Intent intent = new Intent(Authentication.this, MainActivity.class);
//                                Bundle extras = new Bundle();
//                                extras.putString("EXTRA_USERNAME", user.getNAME());
//                                extras.putString("EXTRA_PASSWORD", user.getPASSWORD());
//                                extras.putString("EXTRA_NUMBER", user.getNUMBER());
//                                intent.putExtras(extras);
//                                startActivity(intent);
//                                Toast.makeText(getApplicationContext(),"Welcome!", Toast.LENGTH_LONG).show();
//
//
//                            }
//                        }
//                        else{
//                            Toast.makeText(getApplicationContext(),"You are not registered!", Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
//
//
//                    }
//                });

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(Authentication.this,Signup.class);
                    startActivity(intent);
                }
        });
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Authentication.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });




    }
}
