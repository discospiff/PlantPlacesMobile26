package edu.uc.jonesbr.plantplaces;

import org.junit.Test;

import java.util.List;

import edu.uc.jonesbr.plantplaces.dao.IPlantDAO;
import edu.uc.jonesbr.plantplaces.dao.PlantDAOStub;
import edu.uc.jonesbr.plantplaces.dto.PlantDTO;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by ucint on 5/21/2018.
 */

public class PlantDAOTest {

    private IPlantDAO plantDAO;
    private List<PlantDTO> plants;

    @Test
    public void search_whenSearchForRedbudReceiveOneCercisCanadensis() {
        givenPlantDataAreAvailable();
        whenSearchForRedbud();
        thenReceiveResultWithCercisCanadensis();
    }

    @Test
    public void search_whenSearchForQuercusReceiveTwoOaks() {
        givenPlantDataAreAvailable();
        whenSearchForQuercus();
        thenReceiveTwoOaks();
    }

    private void whenSearchForQuercus() {
        plants = plantDAO.search("Quercus");
    }

    private void thenReceiveTwoOaks() {
        boolean quercusAlba = false;
        boolean quercusRobur = false;
        // iterate over results
        for (PlantDTO plant : plants) {
            if (plant.getGenus().contains("Quercus") && plant.getSpecies().contains("alba")) {
                quercusAlba = true;
            } else if (plant.getGenus().contains("Quercus") && plant.getSpecies().contains("robur")) {
                quercusRobur = true;
            }
        }
        assertTrue(quercusAlba);
        assertTrue(quercusRobur);
    }

    @Test
    public void search_whenSearchForGarbageReceiveNothing() {
        givenPlantDataAreAvailable();
        whenSearchForGarbage();
        thenReceiveNothing();
    }

    private void whenSearchForGarbage() {
        plants = plantDAO.search("sklujapouetllkjsda;u");
    }

    private void thenReceiveNothing() {
        assertEquals(0, plants.size());
    }

    private void givenPlantDataAreAvailable() {
        // instantiate our DAO class.
        plantDAO = new PlantDAOStub();
    }

    private void whenSearchForRedbud() {
        plants = plantDAO.search("Redbud");
    }

    

    private void thenReceiveResultWithCercisCanadensis() {
        // assume that we don't have a plant that meets the given criteria.
        boolean cercisCanadensisFound = false;


        // iterate over results
        for (PlantDTO plant : plants) {
            // does this plant qualify?
            if (plant.getGenus().contains("Cercis") && plant.getSpecies().contains("canadensis")) {
                cercisCanadensisFound = true;
                break;
            }
        }

        assertTrue(cercisCanadensisFound);

    }
}
