package com.example.demo;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class MyTasks {
    private RestTemplate restTemplate = new RestTemplate();
    private static int count;

    /**
     * Adds a vehicle (POST) at a periodic rate.
     * <p>
     * Creates vehicles with random values, and adds it to the vehicles database.
     * Vehicle ID: Start at 1 and increment.
     * makeModel: random generated String.
     * year should be a random int between 1986-2019.
     * retailPrice should be a random number between $15,000 and $45,000.
     */
    @Scheduled(fixedRate = 5000)
    public void addVehicle() {
        String url = "http://localhost:8080/addVehicle";
        Vehicle v = createRandomVehicle();
        Vehicle out = restTemplate.postForObject(url, v, Vehicle.class);
        count = out.getId();
        if (out != null) {
            System.out.println("Added Vehicle:  " + out.toString());
        }
    }

    /**
     * Deletes a vehicle at a periodic rate (DELETE).
     * <p>
     * Generate a random ID within a reasonable range (0-100)
     * DELETE request for the generated ID.
     */
    @Scheduled(fixedRate = 5000)
    public void deleteVehicle() {
        int idToDelete = RandomUtils.nextInt(0, count);
        String url = "http://localhost:8080/deleteVehicle/";

        try {
            restTemplate.delete(url + idToDelete);
            System.out.println("Deleted: " + idToDelete);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            //do nothing. Can not delete.
        }
    }

    /**
     * Updates a vehicle at a periodic rate (PUT)
     * <p>
     * Create a new vehicle object with random numbers between 0-100.
     * The ID should be likely to exist already.
     * makeModel, year, retail price can be hardcoded at some arbitrary values(EASY TO IDENTIFY)
     * <p>
     * After the update, make a GET request to verify the vehicle was actually updated.
     */
    @Scheduled(fixedRate = 6500)
    public void updateVehicle() {
        String putUrl = "http://localhost:8080/updateVehicle";
        String getUrl = "http://localhost:8080/getVehicle/";
        int randomID = RandomUtils.nextInt(0, count);

        Vehicle vehicle = new Vehicle(randomID, "_-_-_-_-_-_-_-_-", 2050, 33333.33);
        restTemplate.put(putUrl, vehicle);
        Vehicle pulledV = restTemplate.getForObject(getUrl + randomID, Vehicle.class);
        if (pulledV != null) {
            System.out.println("Updated: " + pulledV.getId() + "   to: " + pulledV.toString());
        }
    }


    /**
     * At the top of each hour, for every hour in the day, make a GET request
     * to /getLatestVehicles. The last 10 vehicles added to the inventory should be
     * printed to the console
     * <p>
     * See:
     * class java.util.LinkedHashMap cannot be cast to class com.example.demo.Vehicle
     */
    @Scheduled(cron = "0 0 * * * *")
    public void latestVehiclesReport() {
        String url = "http://localhost:8080/getLatestVehicles";
        Vehicle[] vehicles = restTemplate.getForObject(url, Vehicle[].class);

        if (vehicles == null) return;
        System.out.println(System.lineSeparator() + "10 Most Recent Vehicles:");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.toString());
        }
        System.out.println();
    }


    /**
     * Creates a random vehicle instance
     * <p>
     * id:          Non-duplicate incremental count.
     * makeModel:   Random String sequence of 10 characters.
     * year:        Random year from 1986-2019.
     * retailPrice: Random price from $15,000 to $45,000.
     *
     * @return a vehicle instance with random attributes.
     */
    private Vehicle createRandomVehicle() {
        String randomMakeModel = RandomStringUtils.randomAlphabetic(10);
        int randomYear = RandomUtils.nextInt(1986, 2020);
        double randomRetailPrice = RandomUtils.nextDouble(15000.00, 45000.01);
        randomRetailPrice = Double.parseDouble(String.format("%.2f", randomRetailPrice));
        return new Vehicle(randomMakeModel, randomYear, randomRetailPrice);
    }
}
