package com.gmail.psyh2409;

import java.util.Objects;

public class Flat {
    private int id;
    private String city;
    private String street;
    private int home;
    private int flat;
    private int rooms;
    private int area;
    private int price;

    public Flat() {
        super();
    }

    public Flat(String city, String street, int home, int flat, int rooms, int area, int price) {
        this.city = city;
        this.street = street;
        this.home = home;
        this.flat = flat;
        this.rooms = rooms;
        this.area = area;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flat flat1 = (Flat) o;
        return home == flat1.home &&
                flat == flat1.flat &&
                rooms == flat1.rooms &&
                area == flat1.area &&
                price == flat1.price &&
                Objects.equals(city, flat1.city) &&
                Objects.equals(street, flat1.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, home, flat, rooms, area, price);
    }

    @Override
    public String toString() {
        return "Flat{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", home=" + home +
                ", flat=" + flat +
                ", rooms=" + rooms +
                ", area=" + area +
                ", price=" + price +
                '}';
    }
}
