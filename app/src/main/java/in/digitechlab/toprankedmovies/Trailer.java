package in.digitechlab.toprankedmovies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 9/24/2017.
 */

public class Trailer implements Parcelable{

    @SerializedName("id")
    private String trailerId;
    private String key;
    private String name;

    public Trailer() {}

    protected Trailer(Parcel in) {
        trailerId = in.readString();
        key = in.readString();
        name = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) { return new Trailer(in); }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };


    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {  return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(trailerId);
        parcel.writeString(key);
        parcel.writeString(name);
    }

    public static class TrailerResult {
        private List<Trailer> results;

        public List<Trailer> getResults() {
            return results;
        }
    }
}
