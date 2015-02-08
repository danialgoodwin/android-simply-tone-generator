/**
 * Created by Danial on 10/11/2014.
 */
package net.simplyadvanced.simplytonegenerator;

/** Static helper methods for knowing the current version and build of this app. */
public class AppVersion {

    /** No need to instantiate this class. */
    private AppVersion() {}

    /** Returns true if this app should run in free mode, otherwise false. */
//    public static boolean isFree() {
//        return BuildConfig.FLAVOR.equalsIgnoreCase("free");
//    }

    /** Returns true if this app should run in pro mode, otherwise false.. */
//    public static boolean isPro() {
//        return BuildConfig.FLAVOR.equalsIgnoreCase("pro");
//    }

    /** Returns true if this app is running in debug mode, otherwise false. */
    public static boolean isDebug() {
        return BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug");
    }

    /** Returns the app version name, e.g. "3.7.2b". */
    public static String getName() {
        return BuildConfig.VERSION_NAME;
    }

    /** Returns the app package name, e.g. "net.simplyadvanced.simplytonegenerator.pro". */
    public static String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }

    /** Returns true if this app is in pro mode, otherwise false.. */
//    public static boolean isForGoogle() {
//        return true;
//    }

    /** Returns true if this app is in pro mode, otherwise false.. */
//    public static boolean isForAmazon() {
//        return false;
//    }

    /** Returns true if this app is in pro mode, otherwise false.. */
//    public static boolean isForBlackberry() {
//        return false;
//    }

}
