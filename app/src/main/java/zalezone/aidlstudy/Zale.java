package zalezone.aidlstudy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zale on 16/10/26.
 */

public class Zale implements Parcelable{

    private String name;
    private int age;

    public Zale() {
    }

    protected Zale(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
    }

    public static final Creator<Zale> CREATOR = new Creator<Zale>() {
        @Override
        public Zale createFromParcel(Parcel in) {
            return new Zale(in);
        }

        @Override
        public Zale[] newArray(int size) {
            return new Zale[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getName());
        dest.writeInt(this.getAge());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
