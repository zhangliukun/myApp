package zalezone.player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by zale on 16/9/8.
 */
public class ZPlayerManager {

    MediaPlayer mMediaPlayer = null;
    AudioManager mAudioManager = null;

    private volatile int mState;

    private static ZPlayerManager zPlayerManager;
    private Context mContext;

    public ZPlayerManager(Context mContext) {
        this.mContext = mContext;
        initMediaPlayer();
        getAudioFocus();
    }

    public static ZPlayerManager getInstance(Context context) {
        if (zPlayerManager == null) {
            synchronized (ZPlayerManager.class) {
                if (zPlayerManager == null) {
                    zPlayerManager = new ZPlayerManager(context);
                }
            }
        }
        return zPlayerManager;
    }

    public void setDataSource(String path) {

        try {
            mMediaPlayer.reset();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            if (path.startsWith("content://")) {
                mMediaPlayer.setOnPreparedListener(null);
                mMediaPlayer.setDataSource(mContext, Uri.parse(path));
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } else {
                mMediaPlayer.setDataSource(path);
                mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
                mMediaPlayer.prepareAsync();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        mMediaPlayer.setOnErrorListener(mOnErrorListener);
        mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
    }

    public void pause() {
        mMediaPlayer.pause();
        mState = ZPlayerState.STATE_PAUSED;
    }

    public void stop(){
        mMediaPlayer.stop();
        mState = ZPlayerState.STATE_STOPPED;
    }

    private void initMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mState = ZPlayerState.STATE_IDLE;
        }
        //mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
    }

    private void getAudioFocus() {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        }
        int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //could not get audio focus
        }
    }

    private MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            mp.reset();
            return true;
        }
    };

    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.start();
        }
    };

    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {

        }
    };

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mp.stop();
        }
    };

    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            // Do something based on focus change...
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    //resume playback
                    if (mMediaPlayer == null) {
                        initMediaPlayer();
                    } else if (!mMediaPlayer.isPlaying()) {
                        mMediaPlayer.start();
                    }
                    mMediaPlayer.setVolume(1.0f, 1.0f);
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    // Lost focus for an unbounded amount of time: stop playback and release media player
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.stop();
                    }
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    // Lost focus for a short time, but we have to stop
                    // playback. We don't release the media player because playback
                    // is likely to resume
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                    }
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    // Lost focus for a short time, but it's ok to keep playing
                    // at an attenuated level
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.setVolume(0.1f, 0.1f);
                    }
                    break;
            }
        }
    };

    public void onDestory() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (mAudioManager != null) {
            mAudioManager = null;
        }
    }

}
