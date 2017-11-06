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
}
