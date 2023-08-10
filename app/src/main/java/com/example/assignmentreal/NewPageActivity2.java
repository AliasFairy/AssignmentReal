package com.example.assignmentreal;


import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.text.DecimalFormat;

public class NewPageActivity2 extends AppCompatActivity {
    private TextInputEditText amountEdt, peopleEdt;
    private LinearLayout inputFieldsContainer;
    private TextView amountBreakdownTextView;
    private Button resetBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page2);
        amountEdt = findViewById(R.id.idEdtTotalBill2);
        peopleEdt = findViewById(R.id.idEdtNumberOfPeople2);
        resetBtn = findViewById(R.id.idBtnReset2);
        inputFieldsContainer = findViewById(R.id.idInputFieldsContainer2);
        amountBreakdownTextView = findViewById(R.id.idTVAmount);

        Button generateInputFieldsButton = findViewById(R.id.idBtnGenerateInputFields2);
        generateInputFieldsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateInputFields2();
            }
        });

        Button calculateBreakdownButton = findViewById(R.id.idBtnCalculate);
        calculateBreakdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAmount();
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting empty text for edit text.
                amountEdt.setText("");
                peopleEdt.setText("");
            }
        });
        Button shareButton = findViewById(R.id.idBtnShare);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareResult();
            }
        });
    }
    private void generateInputFields2(){
        inputFieldsContainer.removeAllViews();

        // Get the number of people entered by the user
        TextInputEditText numberOfPeopleEditText = findViewById(R.id.idEdtNumberOfPeople2);
        int numberOfPeople = Integer.parseInt(numberOfPeopleEditText.getText().toString());

        // Create input fields for custom percentages and total bill
        TextInputLayout[] inputLayouts = new TextInputLayout[numberOfPeople];

        // Generate input fields for custom percentages based on the number of people
        for (int i = 0; i < numberOfPeople; i++) {
            TextInputLayout inputLayout = new TextInputLayout(this);
            TextInputEditText editText = new TextInputEditText(this);
            editText.setHint("Enter Amount for Person " + (i + 1));
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            inputLayout.addView(editText);
            inputLayouts[i] = inputLayout;
        }


        // Add all input fields to the container
        for (TextInputLayout layout : inputLayouts) {
            inputFieldsContainer.addView(layout);
        }
    }
    private void calculateAmount() {
        double totalBill = Double.parseDouble(amountEdt.getText().toString());
        int numberOfPeople = Integer.parseInt(peopleEdt.getText().toString());

        double[] amounts = new double[numberOfPeople];

        // Retrieve the entered amounts for each person
        for (int i = 0; i < numberOfPeople; i++) {
            TextInputLayout inputLayout = (TextInputLayout) inputFieldsContainer.getChildAt(i);
            TextInputEditText editText = (TextInputEditText) inputLayout.getEditText();
            if (editText != null) {
                amounts[i] = Double.parseDouble(editText.getText().toString());
            }
        }

        // Calculate individual amounts
        double totalAmount = 0.0;
        StringBuilder breakdownText = new StringBuilder("Amount Breakdown:\n");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        for (int i = 0; i < numberOfPeople; i++) {
            double individualAmount = (amounts[i] / totalBill) * totalBill;
            totalAmount += individualAmount;
            breakdownText.append("Person ").append(i + 1).append(": $")
                    .append(decimalFormat.format(individualAmount)).append("\n");
        }

        breakdownText.append("Total Amount: $").append(decimalFormat.format(totalAmount));

        // Verify the sum of individual amounts matches the total bill
        if (Math.abs(totalAmount - totalBill) <= 0.01) {
            breakdownText.append("\nAmounts are verified.");
        } else {
            breakdownText.append("\nAmounts do not match the total bill.");
        }

        // Display the calculated amount breakdown
        amountBreakdownTextView.setText(breakdownText.toString());
    }
    private void shareResult() {
        String resultText = amountBreakdownTextView.getText().toString();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Bill Breakdown");
        shareIntent.putExtra(Intent.EXTRA_TEXT, resultText);

        startActivity(Intent.createChooser(shareIntent, "Share Result"));
    }
}