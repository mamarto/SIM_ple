package com.next.simply.model;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

/**
 * Created by manfredi on 07/08/15.
 */
public class Phonebook {

    public void sortArrayList(ArrayList<String> arrayList) {
        Collections.sort(arrayList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
    }

    public LinkedHashMap<String, String> getAllMobileNumbers(final Context context) {
        LinkedHashMap<String, String> listNumbers = new LinkedHashMap<String, String>();
        final Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            final int phone_id = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            final String contactName = cursor.getString(phone_id);

            final int phone_number = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            final String contactNumber = cursor.getString(phone_number);

            listNumbers.put(contactName, contactNumber);
        }

        return listNumbers;
    }
}
