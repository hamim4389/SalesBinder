package com.example.salesbinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {

    String username_string, password_string, number_string;



    public FirstFragment(String username_string, String password_string, String number_string) {
        this.username_string = username_string;
        this.password_string = password_string;
        this.number_string = number_string;
    }


    private TextView backHome;
    private TextView name;
    private  TextView number;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle  savedInstanceState){
        //backHome= (Button) root.findViewById(R.id.btn_home);

        View root = (View) inflater.inflate(R.layout.activity_profile,container,false);
        backHome= (TextView) root.findViewById(R.id.btn_home);
        name = (TextView) root.findViewById(R.id.acc_username);
        number = (TextView) root.findViewById(R.id.acc_number);

//        MainActivity myActivity = (MainActivity) getActivity();
//        String data = myActivity.myData();



               name.setText(username_string);
               number.setText(number_string);
//        name.setText(username_string);
//        number.setText(password_string);

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        return root;


    }





}
