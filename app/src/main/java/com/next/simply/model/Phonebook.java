package com.next.simply.model;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by manfredi on 07/08/15.
 */
public class Phonebook {

    private ArrayList<String> mListNumbers;

    public ArrayList<String> getListNumbers() {
        return mListNumbers;
    }

    public void setListNumbers(ArrayList<String> listNumbers) {
        mListNumbers = listNumbers;
    }

    public void sortArrayList(ArrayList<String> arrayList) {
        Collections.sort(arrayList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
    }

    public ArrayList<String> getAllMobileNumbers(final Context context) {
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
