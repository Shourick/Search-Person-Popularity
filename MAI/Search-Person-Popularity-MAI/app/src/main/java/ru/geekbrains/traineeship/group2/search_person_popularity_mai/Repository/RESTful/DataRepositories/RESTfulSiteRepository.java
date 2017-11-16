package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.IPersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.ISiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.IRestAPI.IPersonRestAPI;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.IRestAPI.ISiteRestAPI;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.API_URL_BASE;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.UPDATE_OK;

/**
 * Created by skubatko on 13/11/17
 */

public class RESTfulSiteRepository implements ISiteRepository {

    private ISiteRestAPI siteRestAPI;

    public RESTfulSiteRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( API_URL_BASE )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        siteRestAPI = retrofit.create( ISiteRestAPI.class );
    }

    @Override
    public int addSite( Site site ) throws IOException {
        Response<Integer> response = siteRestAPI.addSite( site.getName() ).execute();
        if ( response.isSuccessful() ) {
            return response.body();
        }
        return EMPTY_ID;
    }

    @Override
    public Site getSite( int id ) throws IOException {
        Response<String> response = siteRestAPI.getSiteById( id ).execute();
        if ( response.isSuccessful() ) {
            return new Site( id, response.body() );
        }
        return new Site( EMPTY_ID, EMPTY_NAME );
    }

    @Override
    public List<Site> getAllSites() throws IOException {
        return siteRestAPI.getAllSites().execute().body();
    }

    @Override
    public int getSitesCount() throws IOException {
        Response<List<Site>> response = siteRestAPI.getAllSites().execute();
        if ( response.isSuccessful() ) {
            return response.body().size();
        }
        return EMPTY_ID;
    }

    @Override
    public int updateSite( Site site ) throws IOException {
        siteRestAPI.updateSite( site.getId(), site.getName() ).execute();
        return UPDATE_OK;
    }

    @Override
    public void deleteSite( Site site ) throws IOException {
        siteRestAPI.deleteSite( site.getId() ).execute();
    }

    @Override
    public void deleteAllSites() throws IOException {
        siteRestAPI.deleteAllSites().execute();
    }
}
