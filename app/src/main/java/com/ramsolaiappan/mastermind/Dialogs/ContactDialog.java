package com.ramsolaiappan.mastermind.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ramsolaiappan.mastermind.R;

public class ContactDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_contact_dialog,null);
        v.findViewById(R.id.contactOkBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactDialog.this.dismiss();
            }
        });
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setView(v)
                .setBackground(getContext().getDrawable(R.drawable.round_dialog));
        return builder.create();
    }
}
