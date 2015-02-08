/**
 * Created by Danial on 2/7/2015.
 */
package net.simplyadvanced.simplytonegenerator;

/** Static helper methods related to configuring how the app should run for developers. */
public class AppConfig {

    /** No need to instantiate this class. */
    private AppConfig() {}

    /** Returns true if debug logs should show, otherwise false and all the debug logcat will be
     * hidden. */
    @SuppressWarnings("SameReturnValue")
    public static boolean isShowDebugLogcat() {
        return false;
    }

}
