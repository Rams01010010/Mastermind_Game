package com.ramsolaiappan.mastermind.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.media.Image;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ramsolaiappan.mastermind.R;

public class HowToPlayActivity extends AppCompatActivity {

    private Button prev,next;
    private int currentPage = 0;
    private int[] layoutVisibility = {View.VISIBLE,View.INVISIBLE,View.INVISIBLE,View.INVISIBLE};
    private String[] nextBtnText = {"next","next","next","done"};
    private int[] prevBtnVisibility = {View.INVISIBLE,View.VISIBLE,View.VISIBLE,View.VISIBLE};
    private int[] nextBtnVisibility = {View.VISIBLE,View.VISIBLE,View.VISIBLE,View.VISIBLE};
    private int[] imageOrder = {0,R.drawable.p1,R.drawable.p2,R.drawable.p3};
    private int[] imageVisibility = {View.INVISIBLE,View.VISIBLE,View.VISIBLE,View.VISIBLE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prev = findViewById(R.id.howToPreviousBtn);
        next = findViewById(R.id.howToNextBtn);

        updateView();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                if(currentPage < 4){
                    updateView();
                }
                else
                    finish();
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPage < 4)
                    currentPage--;
                updateView();
            }
        });
    }

    private void updateView()
    {
        ((LinearLayout) findViewById(R.id.linearLayout)).setVisibility(layoutVisibility[currentPage]);
        ((ImageView) findViewById(R.id.imageView2)).setVisibility(imageVisibility[currentPage]);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(imageOrder[currentPage]);
        prev.setVisibility(prevBtnVisibility[currentPage]);
        next.setVisibility(nextBtnVisibility[currentPage]);
        next.setText(nextBtnText[currentPage]);
    }
}