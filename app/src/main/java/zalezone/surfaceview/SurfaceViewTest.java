package zalezone.surfaceview;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import zalezone.aidlstudy.ICallback;
import zalezone.aidlstudy.ITaskAidiInterface;
import zalezone.aidlstudy.MyService;

/**
 * Created by zale on 16/7/11.
 */
public class SurfaceViewTest extends Activity {

    ITaskAidiInterface mStub;

    private ICallback.Stub mCallback = new ICallback.Stub() {
        @Override
        public void showResult(int result) throws RemoteException {
            Log.i("zale","the result is" + result);
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mStub = ITaskAidiInterface.Stub.asInterface(service);
            try {
                mStub.registerCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (mStub == null){
                Log.i("zale","the mStub is null");
            }else {
                try {
                    mStub.add(6,5);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mStub = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new FireworkView(this));

        Intent intent = new Intent();
        intent.setAction("com.zalezone.service.REMOTE_SERVICE");
        intent.setPackage("zalezone.aidlstudy.MyService");
        startService(MyService.getIntent(getApplicationContext()));
        getApplicationContext().bindService(MyService.getIntent(getApplicationContext()),serviceConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (mStub!=null){
            try {
                mStub.unregisterCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
