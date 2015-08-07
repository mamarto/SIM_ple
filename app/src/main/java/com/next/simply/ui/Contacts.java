package com.next.simply.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.next.simply.R;
import com.next.simply.adapters.ContactAdapter;
import com.next.simply.model.Phonebook;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class Contacts extends AppCompatActivity {
    private static final String TAG = Contacts.class.getSimpleName();

    private ArrayList<String> mListNumbers;


    @Bind(R.id.listView) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);

        Phonebook phonebook = new Phonebook();
        mListNumbers = phonebook.getAllMobileNumbers(this);

        phonebook.sortArrayList(mListNumbers);

        ContactAdapter adapter = new ContactAdapter(this, mListNumbers);
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
        if (id == R.id.action_show_contacts) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
