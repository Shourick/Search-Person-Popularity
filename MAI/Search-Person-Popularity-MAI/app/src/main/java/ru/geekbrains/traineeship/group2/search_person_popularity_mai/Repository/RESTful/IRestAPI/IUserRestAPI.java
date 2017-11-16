package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.IRestAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;

/**
 * Created by skubatko on 16/11/17
 */

public interface IUserRestAPI {
    
    @PUT( "users/{name}" )
    Call<Integer> addUser(
            @Path( "name" ) String name );      // добавляем

    @GET( "users/{id}" )
    Call<String> getUserById(
            @Path( "id" ) int id
    );                                          // получаем по id

    @GET( "users/{name}" )
    Call<Integer> getUserByName(
            @Path( "name" ) String name
    );                                          // получаем по Имени

    @GET( "users" )
    Call<List<User>> getAllUsers();         // получаем список всех

    @POST( "users/{id}/{name}" )
    Call updateUser(
            @Path( "id" ) int id, @Path( "name" ) String name
    );                                          // обновляем данные

    @DELETE( "users/{id}" )
    Call deleteUser(
            @Path( "id" ) int id );             // удаляем по id

    @DELETE( "users" )
    Call deleteAllUsers();                    // удаляем все

}
