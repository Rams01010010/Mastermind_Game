package com.ramsolaiappan.mastermind.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ramsolaiappan.mastermind.Dialogs.ContactDialog;
import com.ramsolaiappan.mastermind.Dialogs.SettingsDialog;
import com.ramsolaiappan.mastermind.GlobalVariables;
import com.ramsolaiappan.mastermind.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GlobalVariables.allowDuplicatePegs = getSharedPreferences("settings", Context.MODE_PRIVATE).getBoolean("allowDuplicatePegs",false);
        GlobalVariables.allowEmptyPegs = getSharedPreferences("settings",Context.MODE_PRIVATE).getBoolean("allowEmptyPegs",false);
    }

    public void howto(View view)
    {
        startActivity(new Intent(MainActivity.this,HowToPlayActivity.class));
    }

    public void play(View view)
    {
        Intent intent = new Intent(MainActivity.this,GameActivity.class);
        startActivity(intent);
    }

    public void settings(View view)
    {
        SettingsDialog dialog = new SettingsDialog();
        dialog.setOnClickListener(new SettingsDialog.OnClickListener() {
            @Override
            public void OnClick(boolean save) {
                if(save)
                {
                    getSharedPreferences("settings",MODE_PRIVATE).edit().putBoolean("allowDuplicatePegs", GlobalVariables.allowDuplicatePegs).apply();
                    getSharedPreferences("settings",MODE_PRIVATE).edit().putBoolean("allowEmptyPegs",GlobalVariables.allowEmptyPegs).apply();
                    dialog.dismiss();
                }
                else
                {
                    dialog.dismiss();
                }
            }
        });
        dialog.show(getSupportFragmentManager(),"SettingsDialog");
    }

    public void contact(View view)
    {
        ContactDialog dialog = new ContactDialog();
        dialog.show(getSupportFragmentManager(),"ContactDialog");
    }

}