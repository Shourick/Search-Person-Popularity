package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_NAME;

/**
 * Created by skubatko on 02/11/17.
 */

public class User {

    @SerializedName( "id" )
    @Expose
    private int id;

    @SerializedName( "name" )
    @Expose
    private String nickName;

    @SerializedName( "login" )
    @Expose
    private String login;

    @SerializedName( "password" )
    @Expose
    private String password;

    public User() {
        this.id = EMPTY_ID;
        this.nickName = EMPTY_NAME;
        this.login = EMPTY_NAME;
        this.password = EMPTY_NAME;
    }

    public User( String nickName, String login, String password ) {
        this.id = EMPTY_ID;
        this.nickName = nickName;
        this.login = login;
        this.password = password;
    }

    public User( String nickName, int id, String login ) {
        this.id = id;
        this.nickName = login;
        this.login = login;
        this.password = EMPTY_NAME;
    }

    public User( int id, String login, String nickName ) {
        this.id = id;
        this.nickName = nickName;
        this.login = login;
        this.password = EMPTY_NAME;
    }

    public User( int id, String nickName, String login, String password ) {
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

    @Override
    public boolean equals( Object o ) {
        if ( o == null ) return false;
        if ( !( o instanceof User ) ) return false;

        User other = (User) o;
        if ( this.getId() != other.getId() ) return false;
        if ( !( this.getNickName().equals( other.getNickName() ) ) ) return false;
        if ( !( this.getLogin().equals( other.getLogin() ) ) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id *
                nickName.hashCode() *
                login.hashCode();
    }
}
