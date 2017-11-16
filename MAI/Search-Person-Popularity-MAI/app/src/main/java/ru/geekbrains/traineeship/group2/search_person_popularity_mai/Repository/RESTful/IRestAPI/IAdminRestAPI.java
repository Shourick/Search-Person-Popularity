package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.IRestAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;

/**
 * Created by skubatko on 16/11/17
 */

public interface IAdminRestAPI {

    @PUT( "admins/{name}" )
    Call<Integer> addAdmin(
            @Path( "name" ) String name );      // добавляем

    @GET( "admins/{id}" )
    Call<String> getAdminById(
            @Path( "id" ) int id
    );                                          // получаем по id

    @GET( "admins/{name}" )
    Call<Integer> getAdminByName(
            @Path( "name" ) String name
    );                                          // получаем по Имени

    @GET( "admins" )
    Call<List<Admin>> getAllAdmins();         // получаем список всех

    @POST( "admins/{id}/{name}" )
    Call updateAdmin(
            @Path( "id" ) int id, @Path( "name" ) String name
    );                                          // обновляем данные

    @DELETE( "admins/{id}" )
    Call deleteAdmin(
            @Path( "id" ) int id );             // удаляем по id

    @DELETE( "admins" )
    Call deleteAllAdmins();                    // удаляем все

}
