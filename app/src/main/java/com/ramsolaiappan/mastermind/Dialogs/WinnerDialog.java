package com.ramsolaiappan.mastermind.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ramsolaiappan.mastermind.Activities.GameActivity;
import com.ramsolaiappan.mastermind.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class WinnerDialog extends DialogFragment {

    private CircleImageView c1,c2,c3;
    private TextView t1;
    private Button no,play;
    private OnClickListener onClickListener;
    private String msgToDisplay = "";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if(getArguments().getBoolean("gameState"))
            msgToDisplay = "Congratulations! Finally you broke the Code!.\nPlay again ?";
        else
            msgToDisplay = "Sorry, You reached the maximum round limit\nPlay a new game?";
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_winner_dialog,null);
        no = v.findViewById(R.id.noBtn);
        play = v.findViewById(R.id.playBtn);
        ((TextView) v.findViewById(R.id.guideTV)).setText(msgToDisplay);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener != null)
                {
                    onClickListener.OnClick(false);
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener != null)
                {
                    onClickListener.OnClick(true);
                }
            }
        });
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setView(v)
                .setBackground(getContext().getDrawable(R.drawable.round_dialog));
        return builder.create();
    }

    public interface OnClickListener
    {
        void OnClick(boolean play);
    }

    public void setOnClickListener(OnClickListener listener)
    {
        this.onClickListener = listener;
    }
}
