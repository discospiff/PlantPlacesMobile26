package edu.uc.jonesbr.plantplaces.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.uc.jonesbr.plantplaces.dto.PlantDTO;

/**
 * Created by ucint on 5/21/2018.
 */

public class PlantDAOStub implements IPlantDAO {
    /**
     * Given the search term, find appropriate matching plants.
     *
     * @param searchTerm A text representation of the user's search term.
     * @return a list of matching plants.
     */
    @Override
    public List<PlantDTO> search(String searchTerm) throws IOException {
        List<PlantDTO> allPlants = new ArrayList<PlantDTO>();

        PlantDTO whiteOak = new PlantDTO();
        whiteOak.setGenus("Quercus");
        whiteOak.setSpecies("alba");
        whiteOak.setCommon("White Oak");
        allPlants.add(whiteOak);

        PlantDTO englishOak = new PlantDTO();
        englishOak.setGenus("Quercus");
        englishOak.setSpecies("robur");
        englishOak.setCommon("English Oak");
        allPlants.add(englishOak);

        PlantDTO redbud = new PlantDTO();
        redbud.setGenus("Cercis");
        redbud.setSpecies("canadensis");
        redbud.setCommon("Eastern Redbud");
        allPlants.add(redbud);

        // only add to the matching plants where the plant contains the search term in one of its attributes.
        List<PlantDTO> matchingPlants = new ArrayList<PlantDTO>();

        // find the matches
        for (PlantDTO plant: allPlants) {
            if (plant.toString().contains(searchTerm)) {
                // if any of the attributes of this plant match the user's search term, add it to our collection of plants.
                matchingPlants.add(plant);
            }

        }

        return matchingPlants;
    }
}
