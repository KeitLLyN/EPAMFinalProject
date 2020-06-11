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
    /**
     * Создание объекта с определенными значениями
     * @param from - Откуда отправление
     * @param to - Куда прибытие
     * @param date - Дата отправлния
     * @param startTime - Время старта
     * @param finishTime - Время прибытия
     * */
    public Train(String from, String to, String date, String startTime, String finishTime){
        this.from = from;
        this.to = to;
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    /**
     * Создание объекта с определенными значениями
     * @param from - Откуда отправление
     * @param to - Куда прибытие
     * @param date - Дата отправлния
     * @param startTime - Время старта
     * @param finishTime - Время прибытия
     * @param id - Номер поезда
     * @param wagons - Список вагонов
     * */
    public Train(String from, String to, String date, String startTime, String finishTime, int id, List<Wagon> wagons) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.id = id;
        this.wagons = wagons;

    }

    /**
     * Функция получения значения поля {@link Train#from}
     * @return возвращает название Место отправления
     */
    public String getFrom() {
        return from;
    }
    /**
     * Процедура определения Места отправления {@link Train#from}
     * @param from - Место отправления
     */
    public void setFrom(String from) {
        this.from = from;
    }
    /**
     * Функция получения значения поля {@link Train#id}
     * @return возвращает Номер поезда
     */
    @Override
    public int getId() {
        return id;
    }
    /**
     * Процедура определения Номера поезда {@link Train#id}
     * @param id - Номер поезда
     */
    public void setId(int id){
        this.id = id;
    }
    /**
     * Функция получения значения поля {@link Train#id}
     * @return возвращает Номер поезда
     */
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    /**
     * Функция получения значения поля {@link Train#date}
     * @return возвращает Дату отправления
     */
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    /**
     * Функция получения значения поля {@link Train#startTime}
     * @return возвращает Время старта
     */
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    /**
     * Функция получения значения поля {@link Train#finishTime}
     * @return возвращает Время прибытия
     */
    public String getFinishTime() {
        return finishTime;
    }
    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }
    /**
     * Функция получения значения поля {@link Train#serviceTypes}
     * @return возвращает Множество из всех типов обслуживания
     */
    public Set<String> getServiceTypes() {
        return serviceTypes;
    }
    public void setServiceTypes(Set<String> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }
    /**
     * Функция получения значения поля {@link Train#serviceToSeats}
     * @return возвращает Словарь классов обслуживания к количеству мест
     */
    public Map<String, Integer> getServiceToSeats() {
        return serviceToSeats;
    }
    public void setServiceToSeats(Map<String, Integer> serviceToSeats) {
        this.serviceToSeats = serviceToSeats;
    }
    /**
     * Функция получения значения поля {@link Train#wagons}
     * @return возвращает Список поездов
     */
    public List<Wagon> getWagons() {
        return wagons;
    }
    public void setWagons(List<Wagon> wagons) {
        this.wagons = wagons;
        makeMapsOfData();
    }
    /**
     * Функция получения значения поля {@link Train#serviceToPrice}
     * @return возвращает Словарь классов обслуживания к цене
     */
    public Map<String, Integer> getServiceToPrice() {
        return serviceToPrice;
    }
    public void setServiceToPrice(Map<String, Integer> serviceToPrice) {
        this.serviceToPrice = serviceToPrice;
    }
    /**
     * Функция получения общего количество мест
     * @return возвращает общее количество мест
     */
    public int getNumberOfSeats() {
        return wagons.stream()
                .mapToInt(Wagon::getNumberOfSeats).sum();
    }
    /**
     * Функция получения цены вагона по его номеру
     * @return возвращает стоимать одного из вагонов
     */
    public int getPrice(int wagonID){
        if (wagons != null)
            return Objects.requireNonNull(wagons.stream()
                    .filter(wagon -> wagon.getId() == wagonID)
                    .findAny()
                    .orElse(null))
                    .getId();
        return -1;
    }
    /**
     * Функция получения вагонов относительно класса обслуживания
     * @return возвращает Список вагонов
     */
    public List<Wagon> getWagonsByService(String service){
        return wagons.stream()
                .filter(wagon -> wagon.getServiceClass().equals(service))
                .collect(Collectors.toList());
    }
    /**
     * Функция преобразования данных в соотношения
     * serviceTypes, serviceToSeats, serviceToPrice
     */
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
