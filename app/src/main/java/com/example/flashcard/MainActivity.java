package com.example.flashcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView flashcard_question;
    TextView flashcard_answer;
    ImageView addCardBtn;
    ImageView nextCardBtn;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int ADD_CARD_REQUEST_CODE = 100;
    int currentCardIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcard_question = findViewById(R.id.flashcard_question);
        flashcard_answer = findViewById(R.id.flashcard_answer);
        addCardBtn = findViewById(R.id.addCardBtn);
        nextCardBtn = findViewById(R.id.nextCardBtn);
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (!allFlashcards.isEmpty()){
            Flashcard flashcard = allFlashcards.get(currentCardIndex);
            flashcard_question.setText(flashcard.getQuestion());
            flashcard_answer.setText(flashcard.getAnswer());

            if (allFlashcards.size() > 1){
                nextCardBtn.setVisibility(View.VISIBLE);
            }else{
                nextCardBtn.setVisibility(View.INVISIBLE);
            }
        }

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

        addCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, ADD_CARD_REQUEST_CODE);
            }
        });

        nextCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentCardIndex < allFlashcards.size() - 1){
                    currentCardIndex += 1;
                }
                else{
                    currentCardIndex = 0;
                }
                Flashcard flashcard = allFlashcards.get(currentCardIndex);
                flashcard_question.setText(flashcard.getQuestion());
                flashcard_answer.setText(flashcard.getAnswer());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CARD_REQUEST_CODE) {
            assert data != null;
            String question = data.getExtras().getString("question");
            String answer = data.getExtras().getString("answer");

            flashcard_question.setText(question);
            flashcard_answer.setText(answer);

            // Save to database
            flashcardDatabase.insertCard(new Flashcard(question, answer));
            allFlashcards = flashcardDatabase.getAllCards();  // update the list of Cards
        }
    }
}