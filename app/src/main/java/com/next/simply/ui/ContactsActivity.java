package com.next.simply.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.next.simply.R;
import com.next.simply.adapters.ContactAdapter;
import com.next.simply.model.Phonebook;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ContactsActivity extends AppCompatActivity {
    private static final String TAG = ContactsActivity.class.getSimpleName();

    private Map<String, String> mContacts;
    private android.support.v7.app.ActionBar mActionBar;

    private boolean mOrderByName;

    @Bind(R.id.listView) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);

        mActionBar = getSupportActionBar();
        mActionBar.setTitle(Html.fromHtml("<b>SIM PLY</b>"));

        Phonebook phonebook = new Phonebook();

        SharedPreferences mPrefs = getSharedPreferences("MY_PREFS_FILE", MODE_PRIVATE);
        mOrderByName = mPrefs.getBoolean("NAME_SURNAME", true);

        if (mOrderByName) {
            mContacts = phonebook.getAllMobileNumbersByName(this);
        }
        else {
            mContacts = phonebook.getAllMobileNumbersBySurname(this);
        }


        final ContactAdapter adapter = new ContactAdapter(this, mContacts);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), EditContactActivity.class);
                String item = adapter.getKey(position);
                String number = adapter.getItem(position).toString();
                intent.putExtra("CONTACT_NAME", item);
                intent.putExtra("CONTACT_NUMBER", number);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_show_contacts:
                Intent showContactIntent = new Intent(this, ShowContactsActivity.class);
                startActivity(showContactIntent);
            case R.id.action_search:
                onSearchRequested();
                break;
            case R.id.action_sort_by:
                Intent intent = new Intent(this, SortByActivity.class);
                startActivity(intent);
                break;
            case R.id.action_import_export:
                Intent importExportIntent = new Intent(this, ImportExportContactActivity.class);
                startActivity(importExportIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.addContact) public void startAddActivity() {
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }

}
