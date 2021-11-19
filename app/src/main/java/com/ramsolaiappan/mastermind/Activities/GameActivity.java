package com.ramsolaiappan.mastermind.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.assist.AssistStructure;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ramsolaiappan.mastermind.Adapters.RecyclerViewAdapter;
import com.ramsolaiappan.mastermind.Classes.ColorCode;
import com.ramsolaiappan.mastermind.Dialogs.WinnerDialog;
import com.ramsolaiappan.mastermind.GlobalVariables;
import com.ramsolaiappan.mastermind.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;

import de.hdodenhof.circleimageview.CircleImageView;

public class GameActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private CircleImageView[] paletteColorsIV = new CircleImageView[6];
    private CircleImageView[] hiddenCodeColorsIV = new CircleImageView[4];
    private ArrayList<Integer> hiddenColorCode = new ArrayList<>();
    private LinearLayout hiddenCodeLayout;
    private ArrayList<ColorCode> roundList = new ArrayList<>();
    private TextView checkBtn, findTV;
    boolean codeBreaked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Fullscreen Mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialize Properties
        init();

        //Create ColorCode
        createCode();
    }

    private void init()
    {
        ColorCode.idCount = 0;

        checkBtn = findViewById(R.id.checkBtn);
        findTV = findViewById(R.id.findTV);
        hiddenCodeLayout = findViewById(R.id.qcolorLayout);
        for(int i = 0; i < paletteColorsIV.length; i++)
        {
            paletteColorsIV[i] = findViewById(getResources().getIdentifier("pcolor"+String.valueOf(i+1),"id",getPackageName()));
        }
        for(int i = 0; i < hiddenCodeColorsIV.length; i++)
        {
            hiddenCodeColorsIV[i] = findViewById(getResources().getIdentifier("qcolor"+String.valueOf(i+1),"id",getPackageName()));
            hiddenColorCode.add(-1);
        }

        hiddenCodeLayout.setVisibility(View.INVISIBLE);
        findTV.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,true));

        adapter = new RecyclerViewAdapter(this,R.layout.layout_row,roundList);
        adapter.setOnColorClickListener(new RecyclerViewAdapter.OnColorClickListener() {
            @Override
            public void OnColorClick(int position, int colorIndex, int colorOfView) {
                if(!codeBreaked)
                {
                    if(!GlobalVariables.allowDuplicatePegs)
                    {
                        for(CircleImageView c : paletteColorsIV)
                        {
                            if(c.getTag().equals(String.valueOf(colorOfView)))
                            {
                                c.setImageResource(GlobalVariables.colorCodes[colorOfView]);
                                c.setEnabled(true);
                            }
                        }
                    }
                    roundList.get(position).setColorCode(colorIndex,0);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    updateCheckBtnStatus();
                }
            }
        });

        recyclerView.setAdapter(adapter);
        createRound();

        ((ImageView) findViewById(R.id.imageView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createCode()
    {
        Random randomColorChooser = new Random();
        boolean duplicates = GlobalVariables.allowDuplicatePegs;
        boolean empties = GlobalVariables.allowEmptyPegs;
        int colorRange = (empties) ? 7 : 6;
        int i = 0;
        while(i < 4)
        {
            int randomColor = randomColorChooser.nextInt(colorRange) - (colorRange - 7);
            if(!duplicates)
            {
                if(!hiddenColorCode.contains(randomColor))
                {
                    hiddenColorCode.set(i,randomColor);
                    hiddenCodeColorsIV[i].setImageResource(GlobalVariables.colorCodes[randomColor]);
                    i++;
                }
            }
            else
            {
                hiddenColorCode.set(i,randomColor);
                hiddenCodeColorsIV[i].setImageResource(GlobalVariables.colorCodes[randomColor]);
                i++;
            }
        }
        Log.i("Code",String.valueOf(hiddenColorCode.get(0)) + String.valueOf(hiddenColorCode.get(1)) + String.valueOf(hiddenColorCode.get(2)) + String.valueOf(hiddenColorCode.get(3)));
    }

    public void checkCode(View view) {
        int hintFillIndex = 0;
        ArrayList<Integer> acolorList = roundList.get(roundList.size() - 1).getColorArrayList();
        ArrayList<Integer> amismatchList = new ArrayList<>();
        ArrayList<Integer> hmismatchList = new ArrayList<>();

        //check code to place redPegs
        for (int i = 0; i < 4; i++) {
            int acolor = acolorList.get(i);
            int hcolor = hiddenColorCode.get(i);
            if (acolor == hcolor) {
                roundList.get(roundList.size() - 1).setHintCode(hintFillIndex,1);
                hintFillIndex++;
            }
            else
            {
                amismatchList.add(acolor);
                hmismatchList.add(hcolor);
            }
        }

        //check code to place whitePegs
        for (int i = 0; i < amismatchList.size(); i++)
        {
            int hcolorIndex = hmismatchList.indexOf(amismatchList.get(i));
            if(hcolorIndex != -1)
            {
                amismatchList.set(i,-1);
                hmismatchList.set(hcolorIndex,-1);
                roundList.get(roundList.size()-1).setHintCode(hintFillIndex,2);
                hintFillIndex++;
            }
        }
        recyclerView.getAdapter().notifyDataSetChanged();

        int[] h = roundList.get(roundList.size()-1).getHintCode();
        if(h[0] == 1 && h[1] == 1 && h[2] == 1 && h[3] == 1)
        {
            hiddenCodeLayout.setVisibility(View.VISIBLE);
            findTV.setVisibility(View.INVISIBLE);
            checkBtn.setEnabled(false);
            codeBreaked = true;
            WinnerDialog dialog = new WinnerDialog();
            Bundle bundle = new Bundle();
            bundle.putBoolean("gameState",codeBreaked);
            dialog.setArguments(bundle);
            dialog.setOnClickListener(new WinnerDialog.OnClickListener() {
                @Override
                public void OnClick(boolean play) {
                    if(play){
                        dialog.dismiss();
                        GameActivity.this.recreate();
                    }
                    else
                        finish();
                }
            });
            dialog.show(getSupportFragmentManager(),"WinnerDialog");
        }
        else {
            createRound();
        }

    }

    public void placeColor(View view)
    {
        if(!codeBreaked) {
            for (int i = 0; i < 4; i++) {
                if (roundList.get(roundList.size() - 1).getColorCode()[i] == 0) {
                    roundList.get(roundList.size() - 1).setColorCode(i, Integer.parseInt(view.getTag().toString()));
                    recyclerView.getAdapter().notifyDataSetChanged();
                    updateCheckBtnStatus();
                    if (!GlobalVariables.allowDuplicatePegs) {
                        view.setEnabled(false);
                        ((CircleImageView) view).setImageResource(R.color.noPeg);
                    }
                    return;
                }
            }
        }
    }

    private void createRound()
    {
        if(roundList.size() < 50) {
            roundList.add(new ColorCode());
            recyclerView.scrollToPosition(roundList.size()-1);
            recyclerView.getAdapter().notifyDataSetChanged();
            for(int i = 0; i < paletteColorsIV.length;i++)
            {
                paletteColorsIV[i].setImageResource(GlobalVariables.colorCodes[i+1]);
                paletteColorsIV[i].setEnabled(true);
            }
        }
        else {

            hiddenCodeLayout.setVisibility(View.VISIBLE);
            findTV.setVisibility(View.INVISIBLE);

            WinnerDialog dialog = new WinnerDialog();
            Bundle bundle = new Bundle();
            bundle.putBoolean("gameState",codeBreaked);
            dialog.setArguments(bundle);
            dialog.setOnClickListener(new WinnerDialog.OnClickListener() {
                @Override
                public void OnClick(boolean play) {
                    if(play){
                        dialog.dismiss();
                        GameActivity.this.recreate();
                    }
                    else
                        dialog.dismiss();
                }
            });
            dialog.show(getSupportFragmentManager(),"WinnerDialog");
        }
        updateCheckBtnStatus();
    }

    private void updateCheckBtnStatus()
    {
        checkBtn.setEnabled(true);
        if(!GlobalVariables.allowEmptyPegs) {
            int flag = 0;
            for (int i = 0; i < 4; i++) {
                if (roundList.get(roundList.size() - 1).getColorCode()[i] != 0) {
                    flag += 1;
                }
            }
            if (flag != 4) {
                checkBtn.setEnabled(false);
            }
        }
    }
}