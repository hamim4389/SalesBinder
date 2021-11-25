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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;


public class AddProductActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView textViewExpiryDate, textViewItemCode;
    EditText editTextItemName, editTextItemDesc, editTextMinQty, editTextQty, editTextPrice;
    Button btnExpiryDate, btnQrCode, btnAddItem;
    RadioGroup radioGroup;
    RadioButton rbYes, rbNo;
    boolean remainder = false;

    String[] sampleCategorySuggestions = {"Rice", "Coke", "Noodles", "Tomato", "Termaric", "Racket"  };
    AutoCompleteTextView atvItemCategory;

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

        radioGroup = findViewById(R.id.radio_group);
        rbYes = findViewById(R.id.radio_expiry_calender_yes);
        rbNo = findViewById(R.id.radio_expiry_calender_no);

        btnAddItem = findViewById(R.id.btn_add_item);
        btnExpiryDate = findViewById(R.id.btn_expiry_calender);
        btnQrCode = findViewById(R.id.btn_qr_code);
        // implementing
          // drop down suggetions for category
        atvItemCategory.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, sampleCategorySuggestions));

        btnExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });


        btnQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        AddProductActivity.this
                );
                intentIntegrator.setPrompt("For flash use volume up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
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

    public void onClicked(View view){
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_expiry_calender_yes:
                if (checked)
                    remainder = true;
                    break;
            case R.id.radio_expiry_calender_no:
                if (checked)
                    remainder = false;
                    break;
        }
    }

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
             String date = "day / month / year : " + dayOfMonth + "/ " + month + "/ " + year;
             textViewExpiryDate.setText(date);

    }
}