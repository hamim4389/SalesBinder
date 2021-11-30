package com.example.salesbinder;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;


public class AddProductActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView textViewExpiryDate, textViewItemCode;
    EditText editTextItemName, editTextItemDesc, editTextMinQty, editTextQty, editTextPrice, editTextExpireDate, editTextCategory, editTextDaysToExpire;
    Button btnExpiryDate, btnQrCode, btnAddItem;
    RadioGroup radioGroup;
    RadioButton rbYes, rbNo;
    boolean remainder = false;

    // items details
    private String NAME, DESC, CATEGORY, EXPIRY_DATE, BAR_CODE;
    private int Qty, daysToExpireAlert, price, minQtyAlert;
    boolean setAlert;

    String[] sampleCategorySuggestions = {"Rice", "Coke", "Noodles", "Tomato", "Termaric", "Racket"  };
    String date;
    AutoCompleteTextView atvItemCategory;
    IntentResult intentResult;
    FirebaseDatabase database;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        atvItemCategory = findViewById(R.id.edit_txt_product_category);

        textViewExpiryDate = findViewById(R.id.txt_view_expiry_calender);
        textViewItemCode = findViewById(R.id.txt_view_qr_code);

        editTextItemName = findViewById(R.id.edit_txt_product_name);
        editTextItemDesc = findViewById(R.id.edit_txt_product_desc);
        editTextMinQty = findViewById(R.id.edit_txt_min_qty);
        editTextQty = findViewById(R.id.edit_txt_qty);
        editTextPrice = findViewById(R.id.edit_txt_price);
        editTextCategory = findViewById(R.id.edit_txt_product_category);
        // expiry date er koto din age alert dite chay
        editTextDaysToExpire = findViewById(R.id.edit_txt_days_before_expire_date);

        radioGroup = findViewById(R.id.radio_group);
        rbYes = findViewById(R.id.radio_expiry_calender_yes);
        rbNo = findViewById(R.id.radio_expiry_calender_no);

        btnAddItem = findViewById(R.id.btn_add_item);
        btnExpiryDate = findViewById(R.id.btn_expiry_calender);
        btnQrCode = findViewById(R.id.btn_qr_code);
        // implementing
          // drop down suggestions for category
        atvItemCategory.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, sampleCategorySuggestions));

        // Calender listener
        btnExpiryDate.setOnClickListener(view -> showDatePickerDialog());


       // bar code listener
        btnQrCode.setOnClickListener(view -> {
            IntentIntegrator intentIntegrator = new IntentIntegrator(
                    AddProductActivity.this
            );
            intentIntegrator.setPrompt("For flash use volume up key");
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.setCaptureActivity(Capture.class);
            intentIntegrator.initiateScan();

        });

        // firebase
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();

        // storing item details in firebase
        btnAddItem.setOnClickListener(view -> {

            NAME = editTextItemName.getText().toString().trim();
            DESC = editTextItemDesc.getText().toString().trim();
            CATEGORY = editTextCategory.getText().toString().trim();
            EXPIRY_DATE = date;
           // BAR_CODE = intentResult.getContents();
            BAR_CODE = "";
            Qty = Integer.parseInt(editTextQty.getText().toString().trim());
            //daysToExpireAlert = Integer.parseInt(editTextDaysToExpire.getText().toString().trim());
            daysToExpireAlert = 10;
            price = Integer.parseInt(editTextPrice.getText().toString().trim());
            minQtyAlert = Integer.parseInt(editTextMinQty.getText().toString().trim());
            setAlert = remainder;

            ItemModel item = new ItemModel(NAME, DESC, CATEGORY, EXPIRY_DATE, BAR_CODE, Qty, daysToExpireAlert,
                     price, minQtyAlert, setAlert);

            dbRef.child("Items").child(CATEGORY).child(NAME).setValue(item).addOnCompleteListener(
                    task -> Toast.makeText(getApplicationContext(), "Items Added", Toast.LENGTH_LONG).show());

            //  set every field to blank

                //  editTextExpireDate.setText("");
            editTextCategory.setText("");
            editTextItemName.setText("");
            editTextItemDesc.setText("");
            editTextMinQty.setText("");
            editTextQty.setText("");
            editTextPrice.setText("");
            editTextDaysToExpire.setText("");

            textViewItemCode.setText("");
            textViewExpiryDate.setText("");


            // TODO intent



        });

    }


// scanning barcode

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );

        if(intentResult.getContents() != null)
        {
            textViewItemCode.setText(intentResult.getContents());
        }
        else{
            Toast.makeText(getApplicationContext(), "Noting Scanned!!", Toast.LENGTH_LONG).show();
        }

    }

    // radio button

//    public void onClicked(View view){
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.radio_expiry_calender_yes:
//                    editTextExpireDate.setVisibility(View.VISIBLE);
//                    remainder = true;
//                    break;
//            case R.id.radio_expiry_calender_no:
//                    editTextExpireDate.setVisibility(View.INVISIBLE);
//                    remainder = false;
//                    break;
//        }
//    }

    // calender

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

                );

        datePickerDialog.show(); // triggers onDateset
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
             date = "day/month/year : " + dayOfMonth + "/ " + month + "/ " + year;
             textViewExpiryDate.setText(date);

    }
}