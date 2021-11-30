package com.example.salesbinder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Authentication extends AppCompatActivity {
    private EditText usernameEditText,passwordEditText;
    private Button loginButton;
    private TextView textView;
    private int counter = 3;
    private Button signupButton;
    private Context mContext;

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
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

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
