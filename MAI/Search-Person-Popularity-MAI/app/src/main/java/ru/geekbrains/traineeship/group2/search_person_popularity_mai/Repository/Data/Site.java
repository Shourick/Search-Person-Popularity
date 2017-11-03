package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data;

/**
 * Created by skubatko on 28/10/17.
 */

public class Site {
    private int id;
    private String name;

    public Site() {
    }

    public Site( String name ) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }
}
