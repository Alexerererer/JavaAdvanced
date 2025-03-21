package com.JavaLab.AdvJava.models;

import jakarta.persistence.*;

//A Jakarta class representing an object stored within our DB
@Entity
public class RztkGood {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private int price_uah;

    @Column(nullable = false)
    private int price_usd;

//Limit the title to 255 symbols for DB support
    public void setTitle(String title) {
        if (title.length() > 255)
        {
            title = title.substring(0, 252) + "...";
        }

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice_uah() {
        return price_uah;
    }

    public void setPrice_uah(int price_uah) {
        this.price_uah = price_uah;
    }

    public int getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(int price_usd) {
        this.price_usd = price_usd;
    }
}

