package com.next.simply.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.next.simply.R;
import com.next.simply.model.Phonebook;
import com.next.simply.utils.SimplyConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowContactsActivity extends AppCompatActivity {

    @Bind(R.id.showContactRadioGroup) RadioGroup mRadioGroup;

    @Bind(R.id.simRadioButton) RadioButton mSimRadioButton;
    @Bind(R.id.phoneRadioButton) RadioButton mRadioButton;
    @Bind(R.id.bothRadioButton) RadioButton mBothRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contacts);
        ButterKnife.bind(this);

        final Phonebook phonebook = new Phonebook();

        mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowContactsActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });

        mSimRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonebook.viewSimContact(ShowContactsActivity.this);
                Intent intent2 = new Intent(ShowContactsActivity.this, ContactsActivity.class);
                intent2.putExtra(SimplyConstants.IS_ORDERED_BY_SIM, true);
                startActivity(intent2);
            }
        });
    }
}
