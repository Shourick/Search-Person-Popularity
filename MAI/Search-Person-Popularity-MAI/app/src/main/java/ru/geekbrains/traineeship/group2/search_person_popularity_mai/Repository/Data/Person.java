package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data;

/**
 * Created by skubatko on 28/10/17.
 */

public class Person
{
    private int id;
    private String name;

    public Person()
    {
    }

    public Person( String name )
    {
        this.name = name;
    }

    public Person( int id, String name )
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
