package com.example.bottomnavigation.Medicine;

public class MedicineModel {

    String food, medName, tablets, timesDaily, picUrl;

    MedicineModel(){

    }

    public MedicineModel(String food, String medName, String tablets, String timesDaily, String picUrl) {
        this.food = food;
        this.medName = medName;
        this.tablets = tablets;
        this.timesDaily = timesDaily;
        this.picUrl = picUrl;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getTablets() {
        return tablets;
    }

    public void setTablets(String tablets) {
        this.tablets = tablets;
    }

    public String getTimesDaily() {
        return timesDaily;
    }

    public void setTimesDaily(String timesDaily) {
        this.timesDaily = timesDaily;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
