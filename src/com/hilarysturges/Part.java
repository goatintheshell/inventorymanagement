/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hilarysturges;

/**
 *
 * @author Hilary
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    @Override
    public boolean equals (Object o) {
        if (!(o instanceof Part))
            return false;
        else {
        Part other = (Part) o;
        return this == other;
    } }
    
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    public void setId(int id) {
    };
    public void setName(String name) {  
    };
    public void setPrice(double price){  
    };
    public void setStock(int stock){  
    };
    public void setMin(int min){  
    };
    public void setMax(int max){  
    };
    public void setPrice(int max){  
    };
    public int getId() {
        return id;
    };
    public String getName() {
        return name;
    };
    public double getPrice() {
        return price;
    };
    public int getStock() {
        return stock;
    };
    public int getMin() {
        return min;
    };
    public int getMax() {
        return max;
    };
}
