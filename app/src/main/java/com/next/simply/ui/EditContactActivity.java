package com.next.simply.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.next.simply.R;
import com.next.simply.model.Phonebook;
import com.next.simply.utils.SimplyConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditContactActivity extends AppCompatActivity {

    @Bind(R.id.nameContactEditText) EditText mNameContact;
    @Bind(R.id.numberNameContactEditText) EditText mNumberContact;

    private String mName;
    private String mNumber;
    private Phonebook mPhonebook;

    private android.support.v7.app.ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        ButterKnife.bind(this);

        mActionBar = getSupportActionBar();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mName = extras.getString(SimplyConstants.KEY_CONTACT_NAME);
            mNumber = extras.getString(SimplyConstants.KEY_CONTACT_NUMBER);
            mActionBar.setTitle(Html.fromHtml("<b>" + mName + "</b>"));
            mNameContact.setText(mName);
            mNumberContact.setText(mNumber);
        }
        enableModify(false);

        mPhonebook = new Phonebook();
    }

    private void enableModify(boolean modify) {
        mNameContact.setFocusable(modify);
        mNameContact.setClickable(modify);
        mNumberContact.setFocusable(modify);
        mNumberContact.setClickable(modify);

        mNameContact.setFocusableInTouchMode(modify);
        mNumberContact.setFocusableInTouchMode(modify);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            enableModify(true);
            mNameContact.requestFocus();
        }
        else if (id == R.id.action_delete) {
            mPhonebook.deleteContact(this, mNumber, mName);

            Toast.makeText(this, "Contact removed", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, ContactsActivity.class);
            startActivity(intent);
        }
        else if (id == android.R.id.home) {
            mPhonebook.deleteContact(this, mNumber, mName);
            mPhonebook.createNewContact(mNameContact.getText().toString(), mNumberContact.getText().toString(), this);
        }
        return super.onOptionsItemSelected(item);
    }

}


