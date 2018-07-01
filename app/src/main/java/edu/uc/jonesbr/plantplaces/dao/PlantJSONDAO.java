package edu.uc.jonesbr.plantplaces.dao;

import android.net.Network;

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
    public List<PlantDTO> search(String searchTerm) throws IOException {
        List<PlantDTO> results = new ArrayList<PlantDTO>();
        String url = "http://www.plantplaces.com/perl/mobile/viewplantsjson.pl?Combined_Name=" + searchTerm;

        // access the network.
        String request = networkDAO.request(url);

        return results;
    }
}
