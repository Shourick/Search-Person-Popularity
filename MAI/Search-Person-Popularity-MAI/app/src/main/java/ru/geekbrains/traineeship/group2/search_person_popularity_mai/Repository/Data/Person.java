package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by skubatko on 28/10/17.
 */

public class Person {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    public Person() {
    }

    public Person( String name ) {
        this.id = 0;
        this.name = name;
    }

    public Person( int id, String name ) {
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
