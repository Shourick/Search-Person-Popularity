package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data;

/**
 * Created by skubatko on 28/10/17.
 */

public class Keyword
{
    private int id;
    private String name;
    private int personId;

    public Keyword()
    {
    }

    public Keyword( String name )
    {
        this.name = name;
    }

    public Keyword( String name, int personId )
    {
        this.name = name;
        this.personId = personId;
    }

    public Keyword( int id, String name, int personId )
    {
        this.id = id;
        this.name = name;
        this.personId = personId;
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

    public int getPersonId()
    {
        return personId;
    }

    public void setPersonId( int personId )
    {
        this.personId = personId;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
