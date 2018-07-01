package edu.uc.jonesbr.plantplaces.dao;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import edu.uc.jonesbr.plantplaces.dto.PlantDTO;

/**
 *  Represent the data access operations for a plant.
 *  Created by ucint on 5/17/2018.
 */

public interface IPlantDAO {

    /**
     * Given the search term, find appropriate matching plants.
     * @param searchTerm A text representation of the user's search term.
     * @return a list of matching plants.
     */
    public List<PlantDTO> search(String searchTerm) throws Exception;
}
