package com.next.simply.ui;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.provider.ContactsContract;

import com.next.simply.R;
import com.next.simply.adapters.ContactAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class Contacts extends AppCompatActivity {
    private static final String TAG = Contacts.class.getSimpleName();

    ArrayList<String> mListNumbers;

    @Bind(R.id.listView) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);

        mListNumbers = this.getAllMobileNumbers(this);
        Log.d(TAG, mListNumbers.toString());

        ContactAdapter adapter = new ContactAdapter(this, mListNumbers);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListNumbers);

        mListView.setAdapter(adapter);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<String> getAllMobileNumbers(final Context context) {
        final ArrayList<String> listNumbers = new ArrayList<String>();
        final Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            final int phone_id = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            final String phone = cursor.getString(phone_id);
            listNumbers.add(phone);
        }

        return listNumbers;
    }
}
