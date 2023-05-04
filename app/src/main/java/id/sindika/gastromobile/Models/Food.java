package id.sindika.gastromobile.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Food implements Parcelable{

    @SerializedName("_id")
    private String id;
    private String foodCode;
    private String name;
    private List<String> picture;
    private List<String> base64;
    private String link;
    private String description;
    private String history;
    private String culture;
    private String lifeStyle;
    private List<Ingredient> ingredients;
    private List<String> howToMakes;
    private List<String> nutritions;
    private Date createdAt;
    private Date updatedAt;


    protected Food(Parcel in) {
        id = in.readString();
        foodCode = in.readString();
        name = in.readString();
        picture = in.createStringArrayList();
        base64 = in.createStringArrayList();
        link = in.readString();
        description = in.readString();
        history = in.readString();
        culture = in.readString();
        lifeStyle = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        howToMakes = in.createStringArrayList();
        nutritions = in.createStringArrayList();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(foodCode);
        parcel.writeString(name);
        parcel.writeStringList(picture);
        parcel.writeStringList(base64);
        parcel.writeString(link);
        parcel.writeString(description);
        parcel.writeString(history);
        parcel.writeString(culture);
        parcel.writeString(lifeStyle);
        parcel.writeTypedList(ingredients);
        parcel.writeStringList(howToMakes);
        parcel.writeStringList(nutritions);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoodCode() {
        return foodCode;
    }

    public void setFoodCode(String foodCode) {
        this.foodCode = foodCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getLifeStyle() {
        return lifeStyle;
    }

    public void setLifeStyle(String lifeStyle) {
        this.lifeStyle = lifeStyle;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getHowToMakes() {
        return howToMakes;
    }

    public void setHowToMakes(List<String> howToMakes) {
        this.howToMakes = howToMakes;
    }

    public List<String> getNutritions() {
        return nutritions;
    }

    public void setNutritions(List<String> nutritions) {
        this.nutritions = nutritions;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getBase64() {
        return base64;
    }

    public void setBase64(List<String> base64) {
        this.base64 = base64;
    }
}
