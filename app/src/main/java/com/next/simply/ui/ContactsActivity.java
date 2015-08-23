package com.next.simply.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.next.simply.R;
import com.next.simply.adapters.ContactAdapter;
import com.next.simply.model.Phonebook;
import com.next.simply.utils.SimplyConstants;

import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ContactsActivity extends AppCompatActivity {
    private static final String TAG = ContactsActivity.class.getSimpleName();

    private ContactAdapter adapter;

    private Map<String, String> mContacts;
    private Map<String, String> mFilteredMap = new TreeMap<String, String>();
    private android.support.v7.app.ActionBar mActionBar;
    private String[] mKeys;
    private String[] mValues;

    private boolean mOrderByName;

    @Bind(R.id.empty) TextView mEmpty;
    @Bind(R.id.listView) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);

        mActionBar = getSupportActionBar();
        mActionBar.setTitle(Html.fromHtml("<b>SIM PLY</b>"));

        Phonebook phonebook = new Phonebook();

        SharedPreferences mPrefs = getSharedPreferences(SimplyConstants.KEY_FILE, MODE_PRIVATE);
        mOrderByName = mPrefs.getBoolean(SimplyConstants.KEY_NAME_SURNAME, true);

        Bundle extras = getIntent().getExtras();

        if (extras != null && extras.getBoolean(SimplyConstants.IS_ORDERED_BY_SIM) && extras.getBoolean(SimplyConstants.KEY_SHOW_BOTH)) {
            Map<String, String> sim = phonebook.getSimContacts(this);
            Map<String, String> phone = phonebook.getAllMobileNumbersByName(this);
            mContacts.putAll(sim);
            mContacts.putAll(phone);
        }
        else if (extras != null && extras.getBoolean(SimplyConstants.IS_ORDERED_BY_SIM)) {
            mContacts = phonebook.getSimContacts(this);
            if (mContacts.isEmpty()) {
                Toast.makeText(this, "There are no contacts in the SIM", Toast.LENGTH_LONG).show();
            }
        }
        else {
            mContacts = phonebook.getAllMobileNumbersByName(this);
        }

        if (!mOrderByName) {
            mContacts = phonebook.sortedByLastName(mContacts);
        }

        mValues = mContacts.values().toArray(new String[mContacts.size()]);
        mKeys = mContacts.keySet().toArray(new String[mContacts.size()]);

        createContactAdapter(mContacts);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), EditContactActivity.class);
                String item = adapter.getKey(position);

                if (mValues[position] == null) {
                    intent.putExtra(SimplyConstants.KEY_CONTACT_NUMBER, "No number found");
                } else {
                    String number = adapter.getItem(position).toString();
                    intent.putExtra(SimplyConstants.KEY_CONTACT_NUMBER, number);
                }

                intent.putExtra(SimplyConstants.KEY_CONTACT_NAME, item);
                startActivity(intent);
            }
        });
    }

    private void createContactAdapter(Map<String, String> contacts) {
        adapter = new ContactAdapter(this, contacts);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmpty);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();

        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                for (String name : mKeys) {
                    String number = mContacts.get(name);
                    name = name.trim();
                    if (name.toLowerCase().startsWith(newText.toLowerCase()) && newText.length() > 0) {
                        mFilteredMap.put(name, number);

                        if (mFilteredMap.size() > 0) {
                            createContactAdapter(mFilteredMap);
                        }
                    }

                    if (!name.toLowerCase().startsWith(newText.toLowerCase()) && newText.length() > 0) {
                        mFilteredMap.remove(name);

                        if (mFilteredMap.size() > 0) {
                            createContactAdapter(mFilteredMap);
                        }
                    }

                    if (newText.length() == 0) {
                        mFilteredMap.clear();
                        createContactAdapter(mContacts);
                    }
                }
                return false;
            }
        });

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
                break;
            case R.id.action_sort_by:
                Intent intent = new Intent(this, SortByActivity.class);
                startActivity(intent);
                break;
            case R.id.action_import_export:
                Intent importExportIntent = new Intent(this, ImportExportContactActivity.class);
                importExportIntent.putExtra(SimplyConstants.KEY_KEYS_CONTACT, mKeys);
                importExportIntent.putExtra(SimplyConstants.KEY_VALUES_CONTACT, mValues);
                startActivity(importExportIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.addContact)
    public void startAddActivity() {
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }
}



























