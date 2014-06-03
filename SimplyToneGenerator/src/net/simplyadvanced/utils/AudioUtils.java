package net.simplyadvanced.utils;

import android.content.Context;
import android.media.AudioManager;

/** Static methods related to device audio. */
public class AudioUtils {
    private AudioUtils() {}
    
    /** Returns true if device ringer is silent or vibrate, otherwise false. */
    public static boolean isRingerSilent(Context context) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int ringerMode = am.getRingerMode();
        if ((ringerMode == AudioManager.RINGER_MODE_SILENT)
                || (ringerMode == AudioManager.RINGER_MODE_VIBRATE)) {
            return true;
        }
        return false;
    }
    
}
