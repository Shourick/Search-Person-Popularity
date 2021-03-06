package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_NAME;

/**
 * Created by skubatko on 28/10/17.
 */

public class Person {

    @SerializedName( "id" )
    @Expose
    private Integer id;

    @SerializedName( "name" )
    @Expose
    private String name;

    public Person() {
        this.id = EMPTY_ID;
        this.name = EMPTY_NAME;
    }

    public Person( String name ) {
        this.id = EMPTY_ID;
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

    @Override
    public boolean equals( Object o ) {
        if ( o == null ) return false;
        if ( !( o instanceof Person ) ) return false;

        Person other = (Person) o;
        if ( this.getId() != other.getId() ) return false;
        if ( !( this.getName().equals( other.getName() ) ) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id * name.hashCode();
    }
}
