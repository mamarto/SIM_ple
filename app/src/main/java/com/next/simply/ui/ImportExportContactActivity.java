package com.next.simply.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.next.simply.R;
import com.next.simply.model.Phonebook;
import com.next.simply.utils.SimplyConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImportExportContactActivity extends AppCompatActivity {


    @Bind(R.id.importFromSim) TextView mImportFromSim;
    @Bind(R.id.exportToSim) TextView mExportToSim;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_export_contact);
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.INVISIBLE);

        SharedPreferences mPrefs = getSharedPreferences(SimplyConstants.KEY_FILE, MODE_PRIVATE);
        String names = mPrefs.getString(SimplyConstants.KEY_CONTACTS_FILE, "hello, world");
        final String[] keys = names.split(",");

        final Phonebook phonebook = new Phonebook();

        mImportFromSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);

                phonebook.importSimContact(keys, mProgressBar, v.getContext());
            }
        });
    }
}
