package com.mobilecomputing.flee.flee.data;

/**
 * Created by siddh on 4/27/2016.
 */
public class EventBean {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {

        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCountOfPeople() {
        return countOfPeople;
    }

    public void setCountOfPeople(int countOfPeople) {
        this.countOfPeople = countOfPeople;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(String colorIndex) {
        this.colorIndex = colorIndex;
    }

    public long id;

    private String name;

    private String topic;

    private double latitude;

    private double longitude;

    private int countOfPeople;

    private String description;

    private long time;

    private String address;

    private String url;

    private int categoryId;

    private String distance;

    private String colorIndex;

}
