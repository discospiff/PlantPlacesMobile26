package edu.uc.jonesbr.plantplaces.dao;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Perform common data-agnostic network operations.
 * Created by ucint on 7/1/2018.
 */

public class NetworkDAO {

    /**
     * Return the data found at the given URL
     * @param uri the location for the data
     * @return the raw data found at this location.
     */
    public String request(String uri ) throws IOException {
        StringBuilder sb = new StringBuilder();

        // construct a URL in the correct format
        URL url = new URL(uri);

        // open a connection to this URL
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            // get an input stream to read the bits
            InputStream inputStream = urlConnection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            // get a reader to turn the bits into a String.
            InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // read one line at a time.
            String inputLine = bufferedReader.readLine();
            while (inputLine != null) {
                // add this to our running String Builder.
                sb.append(inputLine);

                // read the next line
                inputLine = bufferedReader.readLine();
            }
        } finally {
            urlConnection.disconnect();
        }

        return sb.toString();
    }
}
