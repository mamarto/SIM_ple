package com.next.simply.model;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by manfredi on 07/08/15.
 */
public class Phonebook {

    public Map<String, String> getAllMobileNumbers(final Context context) {
        Map<String, String> listNumbers = new TreeMap<String, String>();
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
