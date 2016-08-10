// ITaskAidiInterface.aidl
package zalezone.aidlstudy;

import zalezone.model.Product;
import zalezone.aidlstudy.ICallback;

interface ITaskAidiInterface {

    int add(int arg1,int arg2);

    String getName();

    Product getProduct();

    void registerCallback(ICallback cb);

    void unregisterCallback(ICallback cb);
}
