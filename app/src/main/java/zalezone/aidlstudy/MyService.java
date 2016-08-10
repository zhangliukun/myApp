package zalezone.aidlstudy;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import zalezone.model.Product;


/**
 * Created by zale on 16/7/25.
 */
public class MyService extends Service{


    private RemoteCallbackList<ICallback> mRemoteCallbackList = new RemoteCallbackList<>();


    private ITaskAidiInterface.Stub mBinder = new ITaskAidiInterface.Stub() {
        @Override
        public int add(int arg1, int arg2) throws RemoteException {
            return 0;
        }

        @Override
        public String getName() throws RemoteException {
            return null;
        }

        @Override
        public Product getProduct() throws RemoteException {

            return null;
        }

        @Override
        public void registerCallback(ICallback cb) throws RemoteException {
            if (cb!=null){
                mRemoteCallbackList.register(cb);
            }
        }

        @Override
        public void unregisterCallback(ICallback cb) throws RemoteException {
            if (cb!=null){
                mRemoteCallbackList.unregister(cb);
            }
        }
    };

    @Override
    public void onCreate() {
        Log.i("zale","create service"+ Thread.currentThread().getId());
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        mRemoteCallbackList.kill();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("zale","onStartCommand"+ Thread.currentThread().getId());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public static final Intent getIntent(Context ctx) {
        Intent intent = new Intent(ctx, MyService.class);
        return intent;
    }


}
