package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView flashcard_question;
    TextView flashcard_answer;
    TextView answer1;
    TextView answer2;
    TextView answer3;
    ImageView addCardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcard_question = findViewById(R.id.flashcard_question);
        flashcard_answer = findViewById(R.id.flashcard_answer);
        answer1 = findViewById(R.id.first_answer);
        answer2 = findViewById(R.id.second_answer);
        answer3 = findViewById(R.id.third_answer);
        Intent intent = new Intent(this, AddCardActivity.class);
        addCardBtn = findViewById(R.id.addCardBtn);
        flashcard_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcard_question.setVisibility(View.INVISIBLE);
                flashcard_answer.setVisibility(View.VISIBLE);
            }
        });

        flashcard_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcard_question.setVisibility(View.VISIBLE);
                flashcard_answer.setVisibility(View.INVISIBLE);
            }
        });

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            answer1.setBackgroundColor(getResources().getColor(R.color.lime_green));            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer1.setBackgroundColor(getResources().getColor(R.color.lime_green));
                answer2.setBackgroundColor(getResources().getColor(R.color.crimson));
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer1.setBackgroundColor(getResources().getColor(R.color.lime_green));
                answer3.setBackgroundColor(getResources().getColor(R.color.crimson));
            }
        });

        addCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        // receive new Card
        receiveNewCard(this.getIntent());
    }

    void receiveNewCard(Intent intent) {
        ArrayList<String> newCard;
        if (intent.hasExtra("new_card")) {
            newCard = intent.getStringArrayListExtra("new_card");
            flashcard_question.setText(newCard.get(0));
            flashcard_answer.setText(newCard.get(1));

            answer1.setVisibility(View.INVISIBLE);
            answer2.setVisibility(View.INVISIBLE);
            answer3.setVisibility(View.INVISIBLE);
        }
    }
}