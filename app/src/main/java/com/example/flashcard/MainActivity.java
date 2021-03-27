package com.example.flashcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

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
    Animation leftOutAnim;
    Animation rightInAnim;


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
        leftOutAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_out);
        rightInAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_in);

        leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                flashcard_question.startAnimation(leftOutAnim);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                increaseCardIndex();
                flashcard_question.startAnimation(rightInAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (!allFlashcards.isEmpty()) {
            Flashcard flashcard = allFlashcards.get(currentCardIndex);
            flashcard_question.setText(flashcard.getQuestion());
            flashcard_answer.setText(flashcard.getAnswer());
            if (allFlashcards.size() > 1) {
                nextCardBtn.setVisibility(View.VISIBLE);
            } else {
                nextCardBtn.setVisibility(View.INVISIBLE);
            }
        }else{
            nextCardBtn.setVisibility(View.INVISIBLE);
        }

        handleQuestionClicked();
        handleAnswerClicked();
        addNewCardClicked();
        nextCardBtnClicked();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CARD_REQUEST_CODE && resultCode == RESULT_OK) {
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

    void handleQuestionClicked(){
        flashcard_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the center for the clipping circle
                int cx = flashcard_answer.getWidth() / 2;
                int cy = flashcard_answer.getHeight() / 2;
                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);
                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(flashcard_answer, cx, cy, 0f, finalRadius);
                // hide the question and show the answer to prepare for playing the animation!
                flashcard_question.setVisibility(View.INVISIBLE);
                flashcard_answer.setVisibility(View.VISIBLE);

                anim.setDuration(2000);
                anim.start();
            }
        });

    }
    void handleAnswerClicked(){
        flashcard_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the center for the clipping circle
                int cx = flashcard_question.getWidth() / 2;
                int cy = flashcard_question.getHeight() / 2;
                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);
                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(flashcard_question, cx, cy, 0f, finalRadius);
                // hide the question and show the answer to prepare for playing the animation!
                flashcard_question.setVisibility(View.VISIBLE);
                flashcard_answer.setVisibility(View.INVISIBLE);

                anim.setDuration(2000);
                anim.start();
            }
        });

    }
    void addNewCardClicked(){
        addCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, ADD_CARD_REQUEST_CODE);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }
    void nextCardBtnClicked(){ nextCardBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            flashcard_question.startAnimation(leftOutAnim);
        }
    });}
    void increaseCardIndex(){
        if (currentCardIndex < allFlashcards.size() - 1) {
            currentCardIndex += 1;
        } else {
            currentCardIndex = 0;
        }
        Flashcard flashcard = allFlashcards.get(currentCardIndex);
        flashcard_question.setText(flashcard.getQuestion());
        flashcard_answer.setText(flashcard.getAnswer());
    }
}