package net.simplyadvanced.util;

import android.content.Context;
import android.media.AudioManager;

/** Static methods related to device audio. */
public class AudioUtils {

    /** No need to instantiate this class. */
    private AudioUtils() {}
    
    /** Returns true if device ringer is silent or vibrate, otherwise false. */
    public static boolean isRingerSilent(Context context) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int ringerMode = am.getRingerMode();
        return (ringerMode == AudioManager.RINGER_MODE_SILENT) || (ringerMode == AudioManager.RINGER_MODE_VIBRATE);
    }
    
}
