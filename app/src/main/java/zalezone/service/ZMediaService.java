package zalezone.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import zalezone.activity.MainActivity;
import zalezone.aidlstudy.ZPlayerInterface;
import zalezone.player.ZPlayerManager;
import zalezone.surfaceview.R;

/**
 * Created by zale on 16/9/8.
 */
public class ZMediaService extends Service {

    private static final String ACTION_PLAY = "cn.zalezone.action.PLAY";

    private ZPlayerManager zPlayerManager;

    String music ="/sdcard/Android/data/lastus.mp3";


    private ZPlayerInterface.Stub mStub = new ZPlayerInterface.Stub() {
        @Override
        public void play() throws RemoteException {
            Log.i("ZMediaService","start");
            createNotification();
            zPlayerManager.setDataSource(music);
            Log.i("ZMediaService","end");

        }

        @Override
        public void pause() throws RemoteException {
            zPlayerManager.pause();
        }

        @Override
        public void stop() throws RemoteException {
            //zPlayerManager.
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mStub;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void createNotification(){
        String songName="";
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0,
                new Intent(getApplicationContext(), MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
//        Notification notification = new Notification();
//        notification.tickerText = songName;
//        notification.icon = R.drawable.icon_wechat;
//        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        Notification notification = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.icon_wechat)
                .setContentTitle(songName+"hello")
                .setContentText("description")
                .setContentIntent(pi)
                .build();
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(1,notification);
        startForeground(1, notification);
    }

    public void closeNotification(){
        stopForeground(true);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        zPlayerManager = ZPlayerManager.getInstance(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        zPlayerManager.onDestory();
    }

    public static final Intent getIntent(Context ctx) {
        Intent intent = new Intent(ctx, ZMediaService.class);
        return intent;
    }
}
