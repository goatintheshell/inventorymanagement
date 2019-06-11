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
    private int max;
    private int min;

    
    @Override
    public boolean equals (Object o) {
        if (!(o instanceof Part))
            return false;
        else {
        Part other = (Part) o;
        return this == other;
    } }
    
    public Part(int id, String name, double price, int stock, int max, int min) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.max = max;
        this.min = min;
    }
    public void setId(int id) {
        this.id=id;
    };
    public void setName(String name) {  
        this.name=name;
    };
    public void setPrice(double price){  
        this.price=price;
    };
    public void setStock(int stock){
        this.stock=stock;
    };
    public void setMin(int min){  
        this.min=min;
    };
    public void setMax(int max){  
        this.max=max;
    };
    public void setPrice(int max){ 
        this.price=max;
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
