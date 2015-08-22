package com.next.simply.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.next.simply.R;
import com.next.simply.model.Phonebook;
import com.next.simply.utils.SimplyConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImportExportContactActivity extends AppCompatActivity {


    @Bind(R.id.importFromSim) TextView mImportFromSim;
    @Bind(R.id.exportToSim) TextView mExportToSim;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;

    private String[] mKeys;
    private String[] mValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_export_contact);
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mKeys = extras.getStringArray(SimplyConstants.KEY_KEYS_CONTACT);
            mValues = extras.getStringArray(SimplyConstants.KEY_VALUES_CONTACT);
        }

        final Phonebook phonebook = new Phonebook();

        mImportFromSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);

                phonebook.importSimContact(mKeys, mProgressBar, v.getContext());

//                Intent intent = new Intent(v.getContext(), ContactsActivity.class);
//                startActivity(intent);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        mExportToSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAllContactsToSim(v, phonebook, mProgressBar);
            }
        });
    }

    private void addAllContactsToSim(View v, Phonebook phonebook, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);

        int index = 0;

        for (int i = 0; i < mKeys.length; i++) {
            if (phonebook.areDifferent(mKeys[i], phonebook.getSimNames(v.getContext()))) {
                phonebook.insertSIMContact(mKeys[i], mValues[i], v.getContext());
                index++;
            }
        }

        Toast.makeText(v.getContext(), index + " contacts added.", Toast.LENGTH_LONG).show();

        progressBar.setVisibility(View.INVISIBLE);
    }
}
