package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.IRestAPI;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;

/**
 * Created by skubatko on 16/11/17
 */

public interface ISiteRestAPI {
    
    @PUT( "sites/{name}" )
    Call<Integer> addSite(
            @Path( "name" ) String name );      // добавляем

    @GET( "sites/{id}" )
    Call<String> getSiteById(
            @Path( "id" ) int id
    );                                          // получаем по id

    @GET( "sites/{name}" )
    Call<Integer> getSiteByName(
            @Path( "name" ) String name
    );                                          // получаем по Имени

    @GET( "sites" )
    Call<List<Site>> getAllSites();         // получаем список всех

    @POST( "sites/{id}/{name}" )
    Call<ResponseBody> updateSite(
            @Path( "id" ) int id, @Path( "name" ) String name
    );                                          // обновляем данные

    @DELETE( "sites/{id}" )
    Call<ResponseBody> deleteSite(
            @Path( "id" ) int id );             // удаляем по id

    @DELETE( "sites" )
    Call<ResponseBody> deleteAllSites();                    // удаляем все

}
