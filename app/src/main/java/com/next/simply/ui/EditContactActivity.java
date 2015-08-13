package com.next.simply.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.next.simply.R;
import com.next.simply.utils.SimplyConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditContactActivity extends AppCompatActivity {

    @Bind(R.id.nameContactEditText)
    EditText mNameContact;
    @Bind(R.id.numberNameContactEditText)
    EditText mNumberContact;

    private String mName;
    private String mNumber;

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
        } else if (id == R.id.action_delete) {
            deleteContact(this, mNumber, mName);

            Toast.makeText(this, "Contact removed", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, ContactsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public static boolean deleteContact(Context ctx, String phone, String name) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
        Cursor cur = ctx.getContentResolver().query(contactUri, null, null, null, null);
        try {
            if (cur.moveToFirst()) {
                do {
                    if (cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)).equalsIgnoreCase(name)) {
                        String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                        ctx.getContentResolver().delete(uri, null, null);
                        return true;
                    }

                }
                while (cur.moveToNext());
            }
        }
        catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return false;
        }

}


