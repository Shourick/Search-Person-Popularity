package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.PlayerModels;

/**
 * Created by skubatko on 02/11/17.
 */

public class UserModel {

    private int id;
    private String nickName;
    private String login;
    private String password;

    public UserModel() {
    }

    public UserModel( String nickName, String login, String password ) {
        this.nickName = nickName;
        this.login = login;
        this.password = password;
    }

    public UserModel( int id, String nickName, String login, String password ) {
        this.id = id;
        this.nickName = nickName;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName( String nickName ) {
        this.nickName = nickName;
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
        return nickName;
    }
}
