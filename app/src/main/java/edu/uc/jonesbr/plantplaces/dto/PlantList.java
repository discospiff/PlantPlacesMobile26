package edu.uc.jonesbr.plantplaces.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ucint on 6/28/2018.
 */

public class PlantList {

    @SerializedName("plants")
    private List<PlantDTO> plants;

    public List<PlantDTO> getPlants() {
        return plants;
    }

    public void setPlants(List<PlantDTO> plants) {
        this.plants = plants;
    }
}
