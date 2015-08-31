package com.next.simply.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.next.simply.R;
import com.next.simply.model.Phonebook;
import com.next.simply.utils.SimplyConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowContactsActivity extends AppCompatActivity {

    private android.support.v7.app.ActionBar mActionBar;

    @Bind(R.id.simRadioButton) RadioButton mSimRadioButton;
    @Bind(R.id.phoneRadioButton) RadioButton mPhoneRadioButton;
    @Bind(R.id.bothRadioButton) RadioButton mBothRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contacts);
        ButterKnife.bind(this);

        mActionBar = getSupportActionBar();
        mActionBar.setTitle(Html.fromHtml("<b>Contacts to display</b>"));

        SharedPreferences mPrefs = getSharedPreferences(SimplyConstants.KEY_FILE, MODE_PRIVATE);
        boolean isShownBySim = mPrefs.getBoolean(SimplyConstants.KEY_SHOW_SIM, false);
        boolean showBoth = mPrefs.getBoolean(SimplyConstants.KEY_SHOW_BOTH, false);
        boolean showPhone = mPrefs.getBoolean(SimplyConstants.KEY_SHOW_PHONE, false);

        if (isShownBySim) {
            mSimRadioButton.setChecked(true);
        }
        else if (showBoth) {
            mBothRadioButton.setChecked(true);
        }
        else if (showPhone) {
            mPhoneRadioButton.setChecked(true);
        }

        final Phonebook phonebook = new Phonebook();

        mPhoneRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowContactsActivity.this, ContactsActivity.class);

                SharedPreferences.Editor mEditor = getSharedPreferences(SimplyConstants.KEY_FILE, MODE_PRIVATE).edit();
                mEditor.putBoolean(SimplyConstants.KEY_SHOW_SIM, false);
                mEditor.putBoolean(SimplyConstants.KEY_SHOW_BOTH, false);
                mEditor.putBoolean(SimplyConstants.KEY_SHOW_PHONE, true);

                mEditor.apply();
                startActivity(intent);
            }
        });

        mSimRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phonebook.isSimAvailable(v.getContext())) {
                    Intent intent = new Intent(ShowContactsActivity.this, ContactsActivity.class);

                    SharedPreferences.Editor mEditor = getSharedPreferences(SimplyConstants.KEY_FILE, MODE_PRIVATE).edit();
                    mEditor.putBoolean(SimplyConstants.KEY_SHOW_SIM, true);
                    mEditor.putBoolean(SimplyConstants.KEY_SHOW_BOTH, false);
                    mEditor.putBoolean(SimplyConstants.KEY_SHOW_PHONE, false);

                    mEditor.apply();

                    startActivity(intent);
                }
                else {
                    Toast.makeText(v.getContext(), R.string.sim_not_available, Toast.LENGTH_LONG).show();
                }
            }
        });

        mBothRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phonebook.isSimAvailable(v.getContext())) {
                    if (phonebook.getSimContacts(ShowContactsActivity.this).size() == 0) {
                        Toast.makeText(v.getContext(), "There are no contacts in the SIM", Toast.LENGTH_LONG).show();
                    }

                    Intent intent = new Intent(v.getContext(), ContactsActivity.class);

                    SharedPreferences.Editor mEditor = getSharedPreferences(SimplyConstants.KEY_FILE, MODE_PRIVATE).edit();
                    mEditor.putBoolean(SimplyConstants.KEY_SHOW_BOTH, true);
                    mEditor.putBoolean(SimplyConstants.KEY_SHOW_SIM, false);
                    mEditor.putBoolean(SimplyConstants.KEY_SHOW_PHONE, false);
                    mEditor.apply();

                    startActivity(intent);
                }
                else {
                    Toast.makeText(v.getContext(), R.string.sim_not_available, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
