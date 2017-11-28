package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_NAME;

/**
 * Created by skubatko on 28/10/17.
 */

public class Keyword {

    @SerializedName( "id" )
    @Expose
    private int id;

    @SerializedName( "name" )
    @Expose
    private String name;

    @SerializedName( "person_id" )
    @Expose
    private int personId;

    public Keyword() {
        this.id = EMPTY_ID;
        this.name = EMPTY_NAME;
        this.personId = EMPTY_ID;
    }

    public Keyword( String name ) {
        this.id = EMPTY_ID;
        this.name = name;
        this.personId = EMPTY_ID;
    }

    public Keyword( String name, int personId ) {
        this.id = EMPTY_ID;
        this.name = name;
        this.personId = personId;
    }

    public Keyword( int id, String name, int personId ) {
        this.id = id;
        this.name = name;
        this.personId = personId;
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

    public int getPersonId() {
        return personId;
    }

    public void setPersonId( int personId ) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals( Object o ) {
        if ( o == null ) return false;
        if ( !( o instanceof Keyword ) ) return false;

        Keyword other = (Keyword) o;
        if ( this.getId() != other.getId() ) return false;
        if ( !( this.getName().equals( other.getName() ) ) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id * name.hashCode();
    }
}
