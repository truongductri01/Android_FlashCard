package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class AddCardActivity extends AppCompatActivity {
    ImageView cancelBtn;
    ImageView saveCardBtn;
    EditText questionET;
    EditText answerET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        cancelBtn = findViewById(R.id.cancelCardBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveCardBtn = findViewById(R.id.saveCardBtn);
        questionET = findViewById(R.id.questionEditText);
        answerET = findViewById(R.id.answerEditText);
        ArrayList<String> flashCard = new ArrayList<>();
        Intent intent = new Intent(this, MainActivity.class);
        saveCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashCard.add(questionET.getText().toString());
                flashCard.add(answerET.getText().toString());
                intent.putExtra("new_card", flashCard);
                startActivity(intent);
                finish();
            }
        });
    }
}