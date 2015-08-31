package com.next.simply.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.next.simply.R;
import com.next.simply.model.Phonebook;
import com.next.simply.utils.SimplyConstants;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImportExportContactActivity extends AppCompatActivity {
    private android.support.v7.app.ActionBar mActionBar;


    @Bind(R.id.importFromSim) TextView mImportFromSim;
    @Bind(R.id.exportToSim) TextView mExportToSim;

    Map<String, String> mContacts;


    private String[] mKeys;
    private String[] mValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_export_contact);
        ButterKnife.bind(this);

        mActionBar = getSupportActionBar();
        mActionBar.setTitle(Html.fromHtml("<b>Import/Export contacts</b>"));

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mKeys = extras.getStringArray(SimplyConstants.KEY_KEYS_CONTACT);
            mValues = extras.getStringArray(SimplyConstants.KEY_VALUES_CONTACT);
        }

        final Phonebook phonebook = new Phonebook();

        mImportFromSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonebook.importSimContact(mKeys, v.getContext());
            }
        });

        mExportToSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContacts = phonebook.getAllMobileNumbersByName(v.getContext());

                mKeys = mContacts.keySet().toArray(new String[mContacts.size()]);
                mValues = mContacts.values().toArray(new String[mContacts.size()]);

                phonebook.addAllContactsToSim(v.getContext(), mKeys, mValues);
            }
        });
    }


}
