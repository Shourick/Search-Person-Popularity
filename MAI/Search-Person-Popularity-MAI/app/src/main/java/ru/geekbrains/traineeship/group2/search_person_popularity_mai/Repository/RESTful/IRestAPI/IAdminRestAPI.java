package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.IRestAPI;

import java.util.List;

import okhttp3.ResponseBody;
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

    @PUT( "users/{login}&{name}&1&{password}" )
    Call<Integer> addAdmin(
            @Path( "login" ) String login,
            @Path( "name" ) String name,
            @Path( "password" ) String password
    );      // добавляем

    @GET( "users/{id}" )
    Call<Admin> getAdminById(
            @Path( "id" ) int id
    );                                          // получаем по id

    @GET( "admins/{name}" )
    Call<Admin> getAdminByLogin(
            @Path( "name" ) String name
    );                                          // получаем по Имени

    @GET( "users/admin/1" )
    Call<List<Admin>> getAllAdmins();         // получаем список всех

    @POST( "users/{id}&{name}" )
    Call<ResponseBody> updateAdmin(
            @Path( "id" ) int id,
            @Path( "name" ) String name
    );                                          // обновляем данные

    @POST( "users/password/{id}&{password}" )
    Call<ResponseBody> updateAdminPassword(
            @Path( "id" ) int id,
            @Path( "password" ) String password
    );                                          // обновляем данные

    @DELETE( "users/{id}" )
    Call<ResponseBody> deleteAdmin(
            @Path( "id" ) int id );             // удаляем по id

    @DELETE( "users/1" )
    Call<ResponseBody> deleteAllAdmins();                    // удаляем все

}
