package edu.uc.jonesbr.plantplaces.dao;

import android.net.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.uc.jonesbr.plantplaces.dto.PlantDTO;

/**
 * Created by ucint on 7/1/2018.
 */

public class PlantJSONDAO implements IPlantDAO {

    NetworkDAO networkDAO;

    public PlantJSONDAO() {
        networkDAO = new NetworkDAO();
    }

    /**
     * Given the search term, find appropriate matching plants.
     *
     * @param searchTerm A text representation of the user's search term.
     * @return a list of matching plants.
     */
    @Override
    public List<PlantDTO> search(String searchTerm) throws IOException, JSONException {
        List<PlantDTO> results = new ArrayList<PlantDTO>();
        String url = "http://www.plantplaces.com/perl/mobile/viewplantsjson.pl?Combined_Name=" + searchTerm;

        // access the network.
        String rawJSON = networkDAO.request(url);

        // let's make a Huge JSON object out of this entire string.
        JSONObject root = new JSONObject(rawJSON);

        // get the collection of plants from this object.
        JSONArray plants = root.getJSONArray("plants");

        for (int i = 0; i < plants.length(); i++) {
            // get the current plant object
            JSONObject jsonPlant = plants.getJSONObject(i);
            int id = jsonPlant.getInt("id");
            String genus = jsonPlant.getString("genus");
            String species= jsonPlant.getString("species");
            String cultivar = jsonPlant.getString("cultivar");
            String common = jsonPlant.getString("common");

            // create a new plant object.
            PlantDTO plant = new PlantDTO();
            plant.setGuid(id);
            plant.setGenus(genus);
            plant.setSpecies(species);
            plant.setCommon(common);
            plant.setCultivar(cultivar);

            // add this plant to our results.
            results.add(plant);

        }


        return results;
    }
}
