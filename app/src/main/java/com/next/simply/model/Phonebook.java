package com.next.simply.model;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by manfredi on 07/08/15.
 */
public class Phonebook {

    public Map<String, String> getAllMobileNumbersByName(final Context context) {
        Map<String, String> contacts = new TreeMap<String, String>();
        final Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            final int phone_id = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            final String contactName = cursor.getString(phone_id);

            final int phone_number = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            final String contactNumber = cursor.getString(phone_number);

            contacts.put(contactName, contactNumber);
        }
        return contacts;
    }

    public Map<String, String> getAllMobileNumbersBySurname(final Context context) {
        Map<String, String> contacts = new TreeMap<String, String>();
        final Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            final int phone_id = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            final String contactName = cursor.getString(phone_id);

            final int phone_number = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            final String contactNumber = cursor.getString(phone_number);

            contacts.put(sortByLastName(contactName), contactNumber);
        }
        return contacts;
    }

    private String sortByLastName(String toSort) {
        String sorted = "";
        String[] split = toSort.split(" ");

        for (int i = 0; split.length > i; i++) {
            sorted = sorted + " " + split[split.length - i -1];
        }
        return sorted;
    }

    public Map<String, String> sortedByLastName(Map<String, String> list) {
        Map<String, String> contacts = new TreeMap<String, String>();

        String[] mKeys = list.keySet().toArray(new String[list.size()]);

        for(int i = 0; i < list.size(); i++) {
            contacts.put(sortByLastName(mKeys[i]), list.get(sortByLastName(mKeys[i])));
        }

        return contacts;
    }

    public boolean createNewContact(String name, String[] keys, String telephone, Context context) {

        if (areDifferent(name, keys)) {

            ArrayList<ContentProviderOperation> cntProOper = new ArrayList<ContentProviderOperation>();
            int contactIndex = cntProOper.size();//ContactSize

            //Newly Inserted contact
            // A raw contact will be inserted ContactsContract.RawContacts table in contacts database.
            cntProOper.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)//Step1
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

            //Display name will be inserted in ContactsContract.Data table
            cntProOper.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)//Step2
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, contactIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name) // Name of the contact
                    .build());
            //Mobile number will be inserted in ContactsContract.Data table
            cntProOper.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)//Step 3
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, contactIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, telephone) // Number to be added
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build()); //Type like HOME, MOBILE etc
            try {
                // We will do batch operation to insert all above data
                //Contains the output of the app of a ContentProviderOperation.
                //It is sure to have exactly one of uri or count set
                ContentProviderResult[] contentProresult = null;
                contentProresult = context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, cntProOper); //apply above data insertion into contacts list
                return true;
            } catch (RemoteException exp) {
                //logs;
            } catch (OperationApplicationException exp) {
                //logs
            }
        }
        else {
            Toast.makeText(context, "Contact already exists", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    public void importSimContact(String[] keys, ProgressBar progressBar, Context context) {

        progressBar.setVisibility(View.VISIBLE);

        int index = 0;

        Uri simUri = Uri.parse("content://icc/adn");

        Cursor cursorSim = context.getContentResolver().query(simUri, null, null, null, null);

        while (cursorSim.moveToNext()) {
            final String name = cursorSim.getString(cursorSim.getColumnIndex("name"));
            final String number = cursorSim.getString(cursorSim.getColumnIndex("number"));

            if (areDifferent(name, keys)) {
                 createNewContact(name, keys, number, context);
                 index++;
            }

        }

        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(context, index + " contacts added.", Toast.LENGTH_LONG).show();
    }

    public Map<String, String> viewSimContact(Context context) {
        Map<String, String> contacts = new TreeMap<String, String>();

        Uri simUri = Uri.parse("content://icc/adn");

        Cursor cursorSim = context.getContentResolver().query(simUri, null, null, null, null);

        while (cursorSim.moveToNext()) {
            final String name = cursorSim.getString(cursorSim.getColumnIndex("name"));
            final String number = cursorSim.getString(cursorSim.getColumnIndex("number"));

            contacts.put(name, number);
        }

        return contacts;
    }

    private boolean areDifferent(String name, String[] contacts) {

        for (String firstName : contacts) {
            if (name.equals(firstName)) {
                return false;
            }
        }
        return true;
    }

    public void insertSIMContact(Context context) {
        Uri simUri = Uri.parse("content://icc/adn");

        ContentValues values = new ContentValues();
        values.put("tag","Franco Schicchiz");
        values.put("number", "123456789");

        context.getContentResolver().insert(simUri, values);
        context.getContentResolver().notifyChange(simUri, null);
    }

}
