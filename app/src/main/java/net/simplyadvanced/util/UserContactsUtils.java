/**
 * Created by Danial on 3/9/2015.
 */
package net.simplyadvanced.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

/** Static helper methods related to user's contacts. */
public class UserContactsUtils {

    /** No need to instantiate this class. */
    private UserContactsUtils() {}


    /** Return the first phone number for the contact or null if not available. */
    public static String getPhoneNumber(Context context, int contactId) {
        String phoneNumber = null;
        Cursor cursor = context.getContentResolver()
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                int phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                phoneNumber = cursor.getString(phoneNumberIndex);
            }
            cursor.close();
        }

        return phoneNumber;
    }

}
