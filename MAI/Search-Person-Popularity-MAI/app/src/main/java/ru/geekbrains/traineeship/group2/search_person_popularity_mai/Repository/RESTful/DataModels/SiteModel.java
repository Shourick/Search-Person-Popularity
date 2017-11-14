package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataModels;

/**
 * Created by skubatko on 28/10/17.
 */

public class SiteModel {

    private int id;

    private String name;

    public SiteModel() {
    }

    public SiteModel( String name ) {
        this.name = name;
    }

    public SiteModel( int id, String name ) {
        this.id = id;
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

    @Override
    public String toString() {
        return name;
    }

}
