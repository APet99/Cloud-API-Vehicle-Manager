package com.example.demo;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "vehicles")
public class Vehicle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String makeModel;
    private int year;
    private double retailPrice;


    //default constructor
    public Vehicle() {
        this.id = 0;
        this.makeModel = "Unknown";
        this.year = 0000;
        this.retailPrice = 0.0001;
    }

    public Vehicle(String makeModel, int year, double retailPrice) {
        this.makeModel = makeModel;
        this.year = year;
        this.retailPrice = retailPrice;
    }

    public Vehicle(int id, String makeModel, int year, double retailPrice) {
        this.id = id;
        this.makeModel = makeModel;
        this.year = year;
        this.retailPrice = retailPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMakeModel() {
        return makeModel;
    }

    public void setMakeModel(String makeModel) {
        this.makeModel = makeModel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String toString(){
        return String.format(this.getId() + ", " + this.getMakeModel() + ", Year: " + this.getYear() +
                ", Price: " + "%.2f",this.getRetailPrice());
    }
}
