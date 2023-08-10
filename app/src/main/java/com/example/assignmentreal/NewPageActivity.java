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
public class NewPageActivity extends AppCompatActivity {
    private TextInputEditText amountEdt, peopleEdt;
    private LinearLayout inputFieldsContainer;
    private TextView customBreakdownTextView;
    private Button resetBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);
        amountEdt = findViewById(R.id.idEdtTotalBill);
        peopleEdt = findViewById(R.id.idEdtNumberOfPeople);
        resetBtn = findViewById(R.id.idBtnReset);
        inputFieldsContainer = findViewById(R.id.idInputFieldsContainer);
        customBreakdownTextView = findViewById(R.id.idTVCustomBreakdown);

        Button generateInputFieldsButton = findViewById(R.id.idBtnGenerateInputFields);
        generateInputFieldsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateInputFields();
            }
        });

        Button calculateBreakdownButton = findViewById(R.id.idBtnCalculateCustomBreakdown);
        calculateBreakdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCustomBreakdown();
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
        Button shareButton = findViewById(R.id.idBtnShare2);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareResult();
            }
        });
    }

    private void generateInputFields() {
        inputFieldsContainer.removeAllViews();

        // Get the number of people entered by the user
        TextInputEditText numberOfPeopleEditText = findViewById(R.id.idEdtNumberOfPeople);
        int numberOfPeople = Integer.parseInt(numberOfPeopleEditText.getText().toString());

        // Create input fields for custom percentages and total bill
        TextInputLayout[] inputLayouts = new TextInputLayout[numberOfPeople];

        // Generate input fields for custom percentages based on the number of people
        for (int i = 0; i < numberOfPeople; i++) {
            TextInputLayout inputLayout = new TextInputLayout(this);
            TextInputEditText editText = new TextInputEditText(this);
            editText.setHint("Enter Percentage for Person " + (i + 1));
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            inputLayout.addView(editText);
            inputLayouts[i] = inputLayout;
        }


        // Add all input fields to the container
        for (TextInputLayout layout : inputLayouts) {
            inputFieldsContainer.addView(layout);
        }
    }
    private void calculateCustomBreakdown() {
        // Get the total bill entered by the user
        TextInputEditText totalBillEditText = findViewById(R.id.idEdtTotalBill);
        double totalBill = Double.parseDouble(totalBillEditText.getText().toString());

        // Get the number of people entered by the user
        TextInputEditText numberOfPeopleEditText = findViewById(R.id.idEdtNumberOfPeople);
        int numberOfPeople = Integer.parseInt(numberOfPeopleEditText.getText().toString());
        DecimalFormat df = new DecimalFormat("#.00");
        // Calculate and display the individual bills
        StringBuilder breakdownText = new StringBuilder("Custom Break-down:\n");
        for (int i = 0; i < numberOfPeople; i++) {
            TextInputLayout inputLayout = (TextInputLayout) inputFieldsContainer.getChildAt(i);
            TextInputEditText percentageEditText = (TextInputEditText) inputLayout.getEditText();
            if (percentageEditText != null) {
                double percentage = Double.parseDouble(percentageEditText.getText().toString());
                double individualBill = (percentage / 100) * totalBill;
                breakdownText.append("Person ").append(i + 1).append(": RM").append(df.format(individualBill)).append("\n");
            }
        }

        customBreakdownTextView.setText(breakdownText.toString());
    }
    private void shareResult() {
        String resultText = customBreakdownTextView.getText().toString();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Bill Breakdown");
        shareIntent.putExtra(Intent.EXTRA_TEXT, resultText);

        startActivity(Intent.createChooser(shareIntent, "Share Result"));
    }
}