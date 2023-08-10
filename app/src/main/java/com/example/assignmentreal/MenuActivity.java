package com.example.assignmentreal;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button navigateToBillButton = findViewById(R.id.idBtnNavigateToBill);
        Button navigateToNewPageButton = findViewById(R.id.idBtnNavigateToNewPage);
        Button navigateToNewPage2Button = findViewById(R.id.idBtnNavigateToNewPage2);
        navigateToBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        navigateToNewPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, NewPageActivity.class);
                startActivity(intent);
            }
        });

        navigateToNewPage2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, NewPageActivity2.class);
                startActivity(intent);
            }
        });
    }
}