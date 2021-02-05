package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
public class RestAPIController {
    @Autowired
    private VehicleDao vehicleDao;

    /**
     * Add a vehicle through POST.
     * <p>
     * Writes (appends) the newVehicle to the database
     *
     * @param newVehicle the instance of the new Vehicle to add.
     */
    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    public Vehicle addVehicle(@RequestBody Vehicle newVehicle) {
        vehicleDao.create(newVehicle);
        return newVehicle;
    }


    /**
     * Given an ID, gets the vehicle matching the ID.
     * <p>
     * Iterates the local file, line by line. Checking if the ID matches, and if so, returns
     * a vehicle object.
     *
     * @param id The ID used to search for a matching vehicle instance.
     */
    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) {
        return vehicleDao.getById(id);
    }

    /**
     * Updates a currently stored vehicle. (PUT)
     * <p>
     * Iterates the local file line by line.
     * Checks if the current line's vehicle ID matches the ID passed through
     * If there is a match, updates the current line with the passed through data.
     */
    @RequestMapping(value = "/updateVehicle", method = RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle newVehicle) {
        if (vehicleDao.getById(newVehicle.getId()) == null) {
            return null;
        }
        return vehicleDao.update(newVehicle);
    }


    /**
     * Deletes a vehicle currently stored (DELETE)
     * <p>
     * Takes the given ID abd deletes the vehicle from the local file.
     * Iterates through the local file, line-by-line to check if the given ID exists.,
     * Then performs a deletion.
     */
    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") int id) {
        if (vehicleDao.getById(id) == null) {
            return new ResponseEntity<>("Error Deleting: " + id, HttpStatus.BAD_REQUEST);
        }
        vehicleDao.delete(id);
        if (vehicleDao.getById(id) == null) {
            return new ResponseEntity<>("Successfully Deleted:  " + id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error Deleting: " + id, HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Gets the 10 most recent vehicles added to the inventory
     *
     * @return a list of the most recent vehicles.
     */
    @RequestMapping(value = "/getLatestVehicles", method = RequestMethod.GET)
    public List<Vehicle> getLatestVehicles() {
        List<Vehicle> vehicles = vehicleDao.getLatest();
        return vehicles;
    }
}
