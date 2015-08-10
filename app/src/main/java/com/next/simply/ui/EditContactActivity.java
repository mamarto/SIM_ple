package com.next.simply.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.next.simply.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditContactActivity extends AppCompatActivity {

    @Bind (R.id.nameContactEditText) TextView mNameContact;
    @Bind(R.id.numberNameContactEditText) TextView mNumberContact;

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
            mName = extras.getString("CONTACT_NAME");
            mNumber = extras.getString("CONTACT_NUMBER");
            mActionBar.setTitle(Html.fromHtml("<b>" + mName + "</b>"));
            mNameContact.setText(mName);
            mNumberContact.setText(mNumber);
        }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

