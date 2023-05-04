package id.sindika.gastromobile.Models.Request;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MultipleFoodsDTO implements Parcelable {

    private List<String> foodIds;

    public MultipleFoodsDTO(List<String> foodIds)
    {
        this.foodIds = foodIds;
    }

    protected MultipleFoodsDTO(Parcel in) {
        foodIds = in.createStringArrayList();
    }

    public List<String> getFoodIds() {
        return foodIds;
    }

    public void setFoodIds(List<String> foodIds) {
        this.foodIds = foodIds;
    }

    public static final Creator<MultipleFoodsDTO> CREATOR = new Creator<MultipleFoodsDTO>() {
        @Override
        public MultipleFoodsDTO createFromParcel(Parcel in) {
            return new MultipleFoodsDTO(in);
        }

        @Override
        public MultipleFoodsDTO[] newArray(int size) {
            return new MultipleFoodsDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(foodIds);
    }
}
