package edu.uc.jonesbr.plantplaces.dao;

import edu.uc.jonesbr.plantplaces.dto.PlantList;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ucint on 6/28/2018.
 */

public interface GetPlantService {

    @GET("/perl/mobile/viewplantsjson.pl?Combined_Name=Oak")
    Call<PlantList> getAllPlants();
}
