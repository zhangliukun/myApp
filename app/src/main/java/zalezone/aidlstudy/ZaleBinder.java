package zalezone.aidlstudy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * Created by zale on 16/10/26.
 */

public abstract class ZaleBinder extends Binder implements IZaleInterface{


    public ZaleBinder() {
        this.attachInterface(this,DESCRIPTOR);
    }

    public static IZaleInterface asInterface(IBinder obj){
        if (obj == null){
            return null;
        }

        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);

        if((iin != null)&&(iin instanceof IZaleInterface)){
            return (IZaleInterface) iin;
        }

        return new Proxy(obj);

    }


    @Override
    public void sayHello(String word) throws RemoteException {

    }

    @Override
    public String introduceMe(Zale info) throws RemoteException {
        return null;
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    private static class Proxy extends ZaleBinder{
        private IBinder mRemote;

        public Proxy(IBinder mRemote) {
            this.mRemote = mRemote;
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }

        public String getInterfaceDescription(){
            return DESCRIPTOR;
        }

        @Override
        public void sayHello(String word) throws RemoteException {
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(ZaleBinder.TRANSACTION_sayHello,_data,_reply,0);
                _reply.readException();
            }finally {
                _reply.recycle();
                _data.recycle();
            }
        }

        @Override
        public String introduceMe(Zale info) throws RemoteException {
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
            try{
                _data.writeInterfaceToken(DESCRIPTOR);
                if (info!=null){
                    _data.writeString(info.getName());
                    _data.writeInt(info.getAge());
                }
                mRemote.transact(ZaleBinder.TRANSACTION_introduceMe,_data,_reply,0);
                _reply.readException();
            }finally {
                _reply.recycle();
                _data.recycle();
            }
            return "ok";
        }
    }
}
