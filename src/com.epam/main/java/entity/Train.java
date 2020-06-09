package entity;

import utils.dao.interfaces.Identified;

import java.util.*;
import java.util.stream.Collectors;

public class Train implements Identified {
    private String from;
    private String to;
    private String date;
    private String startTime;
    private String finishTime;
    private int id;
    private Set<String> serviceTypes = new HashSet<>();
    private Map<String, Integer> serviceToSeats = new HashMap<>();
    private Map<String, Integer> serviceToPrice = new HashMap<>();
    private List<Wagon> wagons;

    public Train(){

    }

    public Train(String from, String to, String date, String startTime, String finishTime){
        this.from = from;
        this.to = to;
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public Train(String from, String to, String date, String startTime, String finishTime, int id, List<Wagon> wagons) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.id = id;
        this.wagons = wagons;

    }


    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }
    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Set<String> getServiceTypes() {
        return serviceTypes;
    }
    public void setServiceTypes(Set<String> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public Map<String, Integer> getServiceToSeats() {
        return serviceToSeats;
    }
    public void setServiceToSeats(Map<String, Integer> serviceToSeats) {
        this.serviceToSeats = serviceToSeats;
    }

    public List<Wagon> getWagons() {
        return wagons;
    }
    public void setWagons(List<Wagon> wagons) {
        this.wagons = wagons;
        makeMapsOfData();
    }

    public Map<String, Integer> getServiceToPrice() {
        return serviceToPrice;
    }
    public void setServiceToPrice(Map<String, Integer> serviceToPrice) {
        this.serviceToPrice = serviceToPrice;
    }

    public int getNumberOfSeats() {
        return wagons.stream()
                .mapToInt(Wagon::getNumberOfSeats).sum();

    }

    public int getPrice(int wagonID){
        if (wagons != null)
            return Objects.requireNonNull(wagons.stream()
                    .filter(wagon -> wagon.getId() == wagonID)
                    .findAny()
                    .orElse(null))
                    .getId();
        return -1;
    }

    public List<Wagon> getWagonsByService(String service){
        return wagons.stream()
                .filter(wagon -> wagon.getServiceClass().equals(service))
                .collect(Collectors.toList());
    }

    private void makeMapsOfData(){
        for(Wagon wagon : wagons) {
            this.serviceTypes.add(wagon.getServiceClass());
        }
        for(String serviceType: serviceTypes){
            int sumOfSeats = wagons.stream()
                    .filter(wagon -> wagon.getServiceClass().equals(serviceType))
                    .mapToInt(Wagon::getNumberOfSeats).sum();
            int minPrice = wagons.stream()
                    .filter(wagon -> wagon.getServiceClass().equals(serviceType))
                    .min(Comparator.comparing(Wagon::getPrice))
                    .get().getPrice();
            this.serviceToSeats.put(serviceType,sumOfSeats);
            this.serviceToPrice.put(serviceType,minPrice);
        }
    }
}
