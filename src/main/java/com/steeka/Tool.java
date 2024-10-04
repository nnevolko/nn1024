package com.steeka;

public class Tool {

    private String code;
    private String type;
    private String brand;
    private String name;

    private double cost;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Tool{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
