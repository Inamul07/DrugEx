package com.application.posts;

public class Post {

    String desc, date, traffickingType, transportMethod, city, address, gender, age, appearance, otherInfo, numOfPersons;

    public Post() { }

    public Post(String desc, String date, String traffickingType, String transportMethod, String city, String address, String gender, String age, String appearance, String otherInfo, String numOfPersons) {
        this.desc = desc;
        this.date = date;
        this.traffickingType = traffickingType;
        this.transportMethod = transportMethod;
        this.city = city;
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.appearance = appearance;
        this.otherInfo = otherInfo;
        this.numOfPersons = numOfPersons;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }

    public String getTraffickingType() {
        return traffickingType;
    }

    public String getTransportMethod() {
        return transportMethod;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getAppearance() {
        return appearance;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public String getNumOfPersons() {
        return numOfPersons;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTraffickingType(String traffickingType) {
        this.traffickingType = traffickingType;
    }

    public void setTransportMethod(String transportMethod) {
        this.transportMethod = transportMethod;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public void setNumOfPersons(String numOfPersons) {
        this.numOfPersons = numOfPersons;
    }
}
