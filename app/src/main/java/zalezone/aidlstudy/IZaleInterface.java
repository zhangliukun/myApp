package zalezone.aidlstudy;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

/**
 * Created by zale on 16/10/26.
 */

public interface IZaleInterface extends IInterface{

    static String DESCRIPTOR = "zalezone.aidlstudy.IZaleInterface";

    static final int TRANSACTION_sayHello = IBinder.FIRST_CALL_TRANSACTION + 0;
    static final int TRANSACTION_introduceMe = IBinder.FIRST_CALL_TRANSACTION + 1;

    public void sayHello(String word) throws RemoteException;

    public String introduceMe(Zale info) throws RemoteException;

}
