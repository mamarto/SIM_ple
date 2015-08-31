package com.next.simply.ui;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.next.simply.R;
import com.next.simply.model.Phonebook;
import com.next.simply.utils.SimplyConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddContactActivity extends AppCompatActivity {
    private Context mContext;

    private android.support.v7.app.ActionBar mActionBar;

    private String mName;
    private String mTelephone;

    private String[] mKeys;
    private String[] mValues;
    private boolean mShowBoth;

    private boolean mIsShownBySim;


    @Bind(R.id.nameEditText) EditText mNameEditText;
    @Bind(R.id.telephoneEditText) EditText mTelephoneEditText;
    @Bind(R.id.addContactImageView) ImageView mAddContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);

        SharedPreferences mPrefs = getSharedPreferences(SimplyConstants.KEY_FILE, MODE_PRIVATE);
        mIsShownBySim = mPrefs.getBoolean(SimplyConstants.KEY_SHOW_SIM, false);
        mShowBoth = mPrefs.getBoolean(SimplyConstants.KEY_SHOW_BOTH, false);


        final Phonebook phonebook = new Phonebook();

        mActionBar = getSupportActionBar();
        mActionBar.setTitle(Html.fromHtml("<b>Add new contact</b>"));

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mKeys = extras.getStringArray(SimplyConstants.KEY_KEYS_CONTACT);
            mValues = extras.getStringArray(SimplyConstants.KEY_VALUES_CONTACT);
        }

        mContext = getApplicationContext();

        mAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = mNameEditText.getText().toString();
                mTelephone = mTelephoneEditText.getText().toString();

                new AlertDialog.Builder(v.getContext())
                        .setMessage("Where do you want to save this contact?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (addSim(phonebook)) {
                                    Intent intent = new Intent(mContext, ContactsActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("PHONE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (addPhone(phonebook)) {
                                    Intent intent = new Intent(mContext, ContactsActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNeutralButton("BOTH", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (addSim(phonebook) && addPhone(phonebook)) {
                                    Intent intent = new Intent(mContext, ContactsActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .show();
            }
        });
    }

    private boolean addPhone(Phonebook phonebook) {
        if (phonebook.createNewContact(mName, mKeys, mTelephone, mContext) && mKeys.length > 0) {
            Toast.makeText(mContext, "Contact added to the phone.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean addSim(Phonebook phonebook) {
        if (phonebook.insertSIMContact(mName, mTelephone, mContext)) {
            Toast.makeText(mContext, "Contact added to the SIM.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
