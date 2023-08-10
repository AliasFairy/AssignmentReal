package com.example.assignmentreal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText amountEdt, peopleEdt;
    private TextView amtTV;
    private Button resetBtn, amtBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing variables with ids.
        amountEdt = findViewById(R.id.idEdtAMount);
        peopleEdt = findViewById(R.id.idEdtPeople);
        amtBtn = findViewById(R.id.idBtnGetAmount);
        resetBtn = findViewById(R.id.idBtnReset);
        amtTV = findViewById(R.id.idTVIndividualAmount);

        // adding click listener for amount button.
        amtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if edit text is not empty
                if (!TextUtils.isEmpty(amountEdt.getText()) && !TextUtils.isEmpty(peopleEdt.getText())) {
                    // Get the total amount and number of people
                    double totalAmount = Double.parseDouble(amountEdt.getText().toString());
                    int numberOfPeople = Integer.parseInt(peopleEdt.getText().toString());

                    // Ensure number of people is an integer
                    if (numberOfPeople > 0) {
                        double individualAmount = totalAmount / numberOfPeople;

                        // setting amount to text view with two decimal places
                        DecimalFormat df = new DecimalFormat("#.00");
                        String individualAmountText = getResources().getString(R.string.individual_amount, df.format(individualAmount));

                        // Limiting total bill to two decimal places
                        String totalBillText = getResources().getString(R.string.total_bill, df.format(totalAmount));
                        amtTV.setVisibility(View.VISIBLE);
                        amtTV.setText(individualAmountText+"\n"+totalBillText);
                    } else {
                        // Display an error message for invalid number of people
                        Toast.makeText(MainActivity.this, "Number of people must be greater than 0", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // adding click listener for reset button.
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting empty text for edit text.
                amountEdt.setText("");
                peopleEdt.setText("");
                amtTV.setText("");
            }
        });
        Button shareButton = findViewById(R.id.idBtnShare3);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareResult();
            }
        });
    }
    private void shareResult() {
        String resultText = amtTV.getText().toString();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Bill Breakdown");
        shareIntent.putExtra(Intent.EXTRA_TEXT, resultText);

        startActivity(Intent.createChooser(shareIntent, "Share Result"));
    }
}