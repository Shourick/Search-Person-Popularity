package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.IRestAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;

/**
 * Created by skubatko on 13/11/17
 */

public interface IPersonRestAPI {

    @PUT( "persons/{name}" )
    Call<Integer> addPerson(
            @Path( "name" ) String name );      // добавляем

    @GET( "persons/{id}" )
    Call<String> getPersonById(
            @Path( "id" ) int id
    );                                          // получаем по id

    @GET( "persons/{name}" )
    Call<Integer> getPersonByName(
            @Path( "name" ) String name
    );                                          // получаем по Имени

    @GET( "persons" )
    Call<List<Person>> getAllPersons();         // получаем список всех

    @POST( "persons/{id}/{name}" )
    Call updatePerson(
            @Path( "id" ) int id, @Path( "name" ) String name
    );                                          // обновляем данные

    @DELETE( "persons/{id}" )
    Call deletePerson(
            @Path( "id" ) int id );             // удаляем по id

    @DELETE( "persons" )
    Call deleteAllPersons();                    // удаляем все

}
