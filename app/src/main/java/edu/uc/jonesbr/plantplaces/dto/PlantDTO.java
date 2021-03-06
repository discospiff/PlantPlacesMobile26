package edu.uc.jonesbr.plantplaces.dto;

import com.google.gson.annotations.SerializedName;

/**
 * A plant has a genus, species, cultivar, common name.
 * Created by ucint on 5/17/2018.
 */
public class PlantDTO {

    @SerializedName("id")
    private int guid;
    private int cacheId;
    @SerializedName("genus")
    private String genus;
    @SerializedName("species")
    private String species;
    @SerializedName("cultivar")
    private String cultivar;
    @SerializedName("common")
    private String common;


    public int getGuid() {
        return guid;
    }

    public void setGuid(int guid) {
        this.guid = guid;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getCultivar() {
        return cultivar;
    }

    public void setCultivar(String cultivar) {
        this.cultivar = cultivar;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public int getCacheId() {
        return cacheId;
    }

    public void setCacheId(int cacheId) {
        this.cacheId = cacheId;
    }

    @Override
    public String toString() {
        return genus + " " + species + " " + cultivar + " "  + common;
    }
}
