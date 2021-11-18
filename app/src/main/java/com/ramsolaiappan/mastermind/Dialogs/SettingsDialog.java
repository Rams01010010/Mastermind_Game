package com.ramsolaiappan.mastermind.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ramsolaiappan.mastermind.GlobalVariables;
import com.ramsolaiappan.mastermind.R;

public class SettingsDialog extends DialogFragment {

    private OnClickListener onclicklistener = null;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(getContext());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_settings_dialog,null);
        Switch dupSwitch = v.findViewById(R.id.settingsDuplicatesSwitch);
        Switch emptySwitch = v.findViewById(R.id.settingsEmptySwitch);
        Button doneButton = v.findViewById(R.id.settingsDoneBtn);
        Button cancelButton = v.findViewById(R.id.settingsCancelBtn);
        dupSwitch.setChecked(GlobalVariables.allowDuplicatePegs);
        emptySwitch.setChecked(GlobalVariables.allowEmptyPegs);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclicklistener != null)
                {
                    GlobalVariables.allowDuplicatePegs = dupSwitch.isChecked();
                    GlobalVariables.allowEmptyPegs = emptySwitch.isChecked();
                    onclicklistener.OnClick(true);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclicklistener != null)
                {
                    onclicklistener.OnClick(false);
                }
            }
        });
        dialogBuilder.setView(v)
                     .setBackground(getContext().getDrawable(R.drawable.round_dialog));
        return dialogBuilder.create();
    }

    public void setOnClickListener(OnClickListener listener)
    {
        this.onclicklistener = listener;
    }

    public interface OnClickListener
    {
        void OnClick(boolean save);
    }
}
