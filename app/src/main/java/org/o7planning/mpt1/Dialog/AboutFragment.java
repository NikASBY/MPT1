package org.o7planning.mpt1.Dialog;

import static android.provider.ContactsContract.*;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.o7planning.mpt1.BuildConfig;
import org.o7planning.mpt1.R;

import java.util.Date;

public class AboutFragment  extends DialogFragment {

    private TextView textViewApp;
    private TextView textViewDate;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_about,null);
        textViewApp = (TextView) view.findViewById(R.id.dialog_text_about);
        textViewApp.setText(getResources().getString(R.string.app_name).toString() + " " + BuildConfig.VERSION_NAME);
        textViewDate = (TextView) view.findViewById(R.id.dialog_text_date);
        textViewDate.setText("21.10.2022");

        return new AlertDialog.Builder(getActivity()).setTitle("About").setView(view).setPositiveButton("Ok",null).create();
    }
}
