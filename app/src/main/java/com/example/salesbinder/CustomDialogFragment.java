package com.example.salesbinder;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomDialogFragment{
    EditText editTextItemQty, editTextItemPrice;
    Button btnDialogSell, btnDialogCancel;
    ArrayAdapter<String> adapterName, adapterCategory;
    Spinner spinnerName, spinnerCategory;
    static int sales = 0;
    DatabaseReference dbRef;

    AutoCompleteTextView atvName, atvCategory;


    String[] nameArr = {"Sunsilk", "Dove", "Katari", "Basmoti"};
    String[] categoryArr = {"Rice", "Shampoo", "FaceWash"};

    public void showDialog(Activity activity, String msg){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_sell_product);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


//        spinnerName =(Spinner) dialog.findViewById(R.id.spinner_dialog_item_name);
//        spinnerCategory = (Spinner) dialog.findViewById(R.id.spinner_dialog_item_category);
        atvCategory = (AutoCompleteTextView) dialog.findViewById(R.id.tv_dialog_item_category);
        atvName = (AutoCompleteTextView) dialog.findViewById(R.id.tv_dialog_item_name);
        editTextItemPrice =(EditText) dialog.findViewById(R.id.edit_txt_dialog_price);
        editTextItemQty = (EditText) dialog.findViewById(R.id.edit_txt_dialog_qty);


        adapterName = new ArrayAdapter<String>(activity.getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, nameArr);
        // activity.getResources().getStringArray(R.array.Item_Name)
        adapterName.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        atvName.setAdapter(adapterName);
        atvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atvName.showDropDown();
            }
        });

        adapterCategory = new ArrayAdapter<>(activity.getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, categoryArr);
        adapterCategory.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        atvCategory.setAdapter(adapterCategory);
        atvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atvCategory.showDropDown();
            }
        });

//        spinnerName.setAdapter(adapterName);
//
//        adapterCategory = new ArrayAdapter<>(activity.getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, categoryArr);
//        adapterCategory.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        spinnerCategory.setAdapter(adapterCategory);






        // item name will be dropdown selection only



       btnDialogSell = dialog.findViewById(R.id.btn_dialog_sell);
       btnDialogCancel = dialog.findViewById(R.id.btn_dialog_Cancel);

        btnDialogCancel.setOnClickListener(v -> {
                 Toast.makeText(activity.getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });


        btnDialogSell.setOnClickListener(v -> {
             //sales += Integer.parseInt(editTextItemPrice.toString().trim());

             String name  = atvName.getText().toString().trim();
             String category = atvCategory.getText().toString().trim();
             int qty = Integer.parseInt(editTextItemQty.getText().toString().trim());
              double price = Double.parseDouble(editTextItemPrice.getText().toString().trim());

             dbRef = FirebaseDatabase.getInstance().getReference("Items").child(category).child(name);

             dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                     ItemModel item =  snapshot.getValue(ItemModel.class);
                     int newQty = item.getQty() - qty;

                     double perPrice= item.getPrice()/ item.getQty();

                     double newPrice = perPrice * newQty;

                     if(newQty < 0) {
                         Toast.makeText(activity.getApplicationContext(), "Don't have enough Qty!!", Toast.LENGTH_SHORT).show();
                         return;
                     }
                     dbRef.child("price").setValue(newPrice);
                     dbRef.child("qty").setValue(newQty);
                     Toast.makeText(activity.getApplicationContext(),"Updated the stock" ,Toast.LENGTH_SHORT).show();

                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {
                     Toast.makeText(activity.getApplicationContext(),error.getMessage() ,Toast.LENGTH_SHORT).show();
                 }
             });

                    Toast.makeText(activity.getApplicationContext(),"Added to Inventory" ,Toast.LENGTH_SHORT).show();
            dialog.cancel();
        });

        dialog.show();
    }

    int getSales(){
        return this.sales;
    }

}
