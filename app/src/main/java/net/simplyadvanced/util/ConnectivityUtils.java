/**
 * Created by Danial on 2/7/2015.
 */
package net.simplyadvanced.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/** Static helper methods related to Internet connectivity. */
public class ConnectivityUtils {

    /** No need to instantiate this class. */
    private ConnectivityUtils() {}

    /** Returns true if user currently has access to Internet, otherwise false. */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
