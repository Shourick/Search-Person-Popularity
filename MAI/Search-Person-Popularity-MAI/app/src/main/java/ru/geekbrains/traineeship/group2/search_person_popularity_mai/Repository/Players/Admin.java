package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players;

/**
 * Created by skubatko on 02/11/17.
 */

public class Admin {

    private int id;
    private String login;
    private String password;

    public Admin() {
    }

    public Admin( String login, String password ) {
        this.login = login;
        this.password = password;
    }

    public Admin( int id, String login, String password ) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin( String login ) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    @Override
    public String toString() {
        return login;
    }

    @Override
    public boolean equals( Object o ) {
        if ( o == null ) return false;
        if ( !( o instanceof Admin ) ) return false;

        Admin other = (Admin) o;
        if ( this.getId() != other.getId() ) return false;
        if ( !( this.getLogin().equals( other.getLogin() ) ) ) return false;
        if ( !( this.getPassword().equals( other.getPassword() ) ) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) id *
                login.hashCode() *
                password.hashCode();
    }
}
