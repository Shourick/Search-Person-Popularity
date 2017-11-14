package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.PlayerModels;

/**
 * Created by skubatko on 02/11/17.
 */

public class AdminModel {

    private int id;
    private String login;
    private String password;

    public AdminModel() {
    }

    public AdminModel( String login, String password ) {
        this.login = login;
        this.password = password;
    }

    public AdminModel( int id, String login, String password ) {
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

}
