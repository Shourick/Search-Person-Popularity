package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.IRestAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;

/**
 * Created by skubatko on 15/11/17
 */

interface IKeywordRestAPI {
    @PUT( "keywords/{name}" )
    Call<Integer> addKeyword(
            @Path( "name" ) String name );       // добавляем Личность

    @GET( "keywords/{id}" )
    Call<String> getKeywordById(
            @Path( "id" ) int id
    );               // получаем Личность по id

    @GET( "keywords/{name}" )
    Call<Integer> getKeywordByName(
            @Path( "name" ) String name
    );        // получаем Личность по Имени

    @GET( "keywords" )
    Call<List<Keyword>> getAllKeywords();                       // получаем список всех Личностей

    @POST( "keywords/{id}/{name}" )
    Call updateKeyword(
            @Path( "id" ) int id, @Path( "name" ) String name
    );            // обновляем данные по Личности

    @DELETE( "keywords/{id}" )
    Call deleteKeyword(
            @Path( "id" ) int id );           // удаляем Личность

    @DELETE( "keywords" )
    Call deleteAllKeywords();                    // удаляем все Личности
}
