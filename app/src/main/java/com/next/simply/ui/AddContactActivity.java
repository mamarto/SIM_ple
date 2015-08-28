package com.next.simply.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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


    @Bind(R.id.nameEditText) EditText mNameEditText;
    @Bind(R.id.telephoneEditText) EditText mTelephoneEditText;
    @Bind(R.id.addContactImageView) ImageView mAddContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);

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


                if (phonebook.createNewContact(mName, mKeys, mTelephone, mContext)) {
                    Toast.makeText(mContext, "Contact Added.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, ContactsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
