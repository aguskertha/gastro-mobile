package id.sindika.gastromobile.Models.Request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PredictDTO implements Parcelable {

    private String base64;

    public PredictDTO(String base64) {
        this.base64 = base64;
    }

    protected PredictDTO(Parcel in) {
        base64 = in.readString();
    }

    public static final Creator<PredictDTO> CREATOR = new Creator<PredictDTO>() {
        @Override
        public PredictDTO createFromParcel(Parcel in) {
            return new PredictDTO(in);
        }

        @Override
        public PredictDTO[] newArray(int size) {
            return new PredictDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(base64);
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
