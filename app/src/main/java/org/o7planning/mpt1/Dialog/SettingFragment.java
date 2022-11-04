package org.o7planning.mpt1.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.o7planning.mpt1.R;

public class SettingFragment extends DialogFragment {

    private TextView textViewApp;
    private TextView textViewDate;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_about,null);
        textViewApp = (TextView) view.findViewById(R.id.dialog_text_about);
        textViewApp.setText("Fragment not created");
        textViewDate = (TextView) view.findViewById(R.id.dialog_text_date);
        textViewDate.setText("Awaiting creation");

        return new AlertDialog.Builder(getActivity()).setTitle("Setting").setView(view).setPositiveButton("Ok",null).setNegativeButton("Cancel" , null).create();
    }
}
