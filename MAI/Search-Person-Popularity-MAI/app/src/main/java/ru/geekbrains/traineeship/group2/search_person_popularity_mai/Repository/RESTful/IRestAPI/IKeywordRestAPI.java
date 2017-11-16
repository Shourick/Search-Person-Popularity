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

public interface IKeywordRestAPI {

    @PUT( "keywords/{person_id}/{name}" )
    Call<Integer> addKeyword(
            @Path( "person_id" ) int person_id, @Path( "name" ) String name
    );                                                          // добавляем

    @GET( "keywords/{id}" )
    Call<Keyword> getKeyword(
            @Path( "id" ) int id
    );                                                          // получаем  по id

    @GET( "keywords/person/{person_id}" )
    Call<List<Keyword>> getPersonKeywords(
            @Path( "person_id" ) int person_id
    );                                                          // получаем по person_id

    @GET( "keywords" )
    Call<List<Keyword>> getAllKeywords();                       // получаем список всех

    @POST( "keywords/{id}/{person_id}/{name}" )
    Call updateKeyword(
            @Path( "id" ) int id,
            @Path( "person_id" ) int person_id,
            @Path( "name" ) String name
    );                                                          // обновляем данные

    @DELETE( "keywords/{id}" )
    Call deleteKeyword(
            @Path( "id" ) int id );                             // удаляем c id

    @DELETE( "keywords" )
    Call deleteAllKeywords();                                   // удаляем все

}
