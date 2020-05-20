package entity;


import utils.dao.interfaces.Identified;

public class Wagon implements Identified {
    private String serviceClass;
    private int price;
    private int numberOfSeats;
    private int id;
    private int trainId;


    public Wagon() {
    }

    public Wagon(String serviceClass, int price, int numberOfSeats, int id, int trainId) {
        this.serviceClass = serviceClass;
        this.price = price;
        this.numberOfSeats = numberOfSeats;
        this.id = id;
        this.trainId = trainId;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public String getServiceClass() {
        return serviceClass;
    }
    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void decreaseSeatsByValue(int value) {
        if (numberOfSeats - value >= 0)
            numberOfSeats -= value;
    }

}
