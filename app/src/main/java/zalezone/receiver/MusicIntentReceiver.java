package zalezone.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

/**
 * Created by zale on 16/9/8.
 */
public class MusicIntentReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(AudioManager.ACTION_AUDIO_BECOMING_NOISY)){
            // signal your service to stop playback
            // (via an Intent, for instance)
        }
    }
}
