package edu.uc.jonesbr.plantplaces.dto;

/**
 * Created by ucint on 7/6/2018.
 */

public class SpecimenDTO {
    private int specimenId;
    private int plantId;
    private String plantName;
    private String latitude;
    private String longitude;
    private String location;
    private String description;
    private String key;
    private String imageUrl;

    public int getSpecimenId() {
        return specimenId;
    }

    public void setSpecimenId(int specimenId) {
        this.specimenId = specimenId;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return plantName + " " + description + " " + location + " Lat: " + latitude + " Lng: " + longitude;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
