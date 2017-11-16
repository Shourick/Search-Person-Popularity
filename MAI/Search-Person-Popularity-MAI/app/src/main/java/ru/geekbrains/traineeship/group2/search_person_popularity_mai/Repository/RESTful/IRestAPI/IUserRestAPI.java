package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.IRestAPI;

import java.util.List;

import okhttp3.ResponseBody;
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

    @PUT( "users/{login}&{name}&0&{password}" )
    Call<Integer> addUser(
            @Path( "login" ) String login,
            @Path( "name" ) String name,
            @Path( "password" ) String password
    );      // добавляем

    @GET( "users/{id}" )
    Call<User> getUserById(
            @Path( "id" ) int id
    );                                          // получаем по id

    @GET( "users/{name}" )
    Call<User> getUserByLogin(
            @Path( "name" ) String name
    );                                          // получаем по Имени

    @GET( "users/admin/0" )
    Call<List<User>> getAllUsers();         // получаем список всех

    @POST( "users/{id}/{name}" )
    Call<ResponseBody> updateUser(
            @Path( "id" ) int id,
            @Path( "name" ) String name
    );                                          // обновляем данные

    @POST( "users/password/{id}&{password}" )
    Call<ResponseBody> updateUserPassword(
            @Path( "id" ) int id,
            @Path( "password" ) String password
    );                                          // обновляем данные

    @DELETE( "users/{id}" )
    Call<ResponseBody> deleteUser(
            @Path( "id" ) int id );             // удаляем по id

    @DELETE( "users/0" )
    Call<ResponseBody> deleteAllUsers();                    // удаляем все

}
