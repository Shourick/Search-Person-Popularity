package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.IKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.IRestAPI.IKeywordRestAPI;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.API_URL_BASE;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.UPDATE_OK;

/**
 * Created by skubatko on 13/11/17
 */

public class RESTfulKeywordRepository implements IKeywordRepository {

    private IKeywordRestAPI keywordRestAPI;

    public RESTfulKeywordRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( API_URL_BASE )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        keywordRestAPI = retrofit.create( IKeywordRestAPI.class );
    }

    @Override
    public Keyword getKeyword( int id ) throws IOException {
        Response<Keyword> response = keywordRestAPI.getKeyword( id ).execute();
        if ( response.isSuccessful() ) {
            return response.body();
        }
        return new Keyword( EMPTY_ID, EMPTY_NAME, EMPTY_ID );
    }

    @Override
    public List<Keyword> getPersonKeywords( int keywordId ) throws IOException {
        Response<List<Keyword>> response = keywordRestAPI.getPersonKeywords( keywordId ).execute();
        if ( response.isSuccessful() ) {
            return response.body();
        }
        return new ArrayList<Keyword>();
    }

    @Override
    public int addKeyword( Keyword keyword, int keywordId ) throws IOException {
        Response<Integer> response = keywordRestAPI.addKeyword( keywordId, keyword.getName() ).execute();
        if ( response.isSuccessful() ) {
            return response.body();
        }
        return EMPTY_ID;
    }

    @Override
    public List<Keyword> getAllKeywords() throws IOException {
        return keywordRestAPI.getAllKeywords().execute().body();
    }

    @Override
    public int getKeywordsCount() throws IOException {
        Response<List<Keyword>> response = keywordRestAPI.getAllKeywords().execute();
        if ( response.isSuccessful() ) {
            return response.body().size();
        }
        return EMPTY_ID;
    }

    @Override
    public int updateKeyword( Keyword keyword ) throws IOException {
        keywordRestAPI.updateKeyword( keyword.getId(), keyword.getPersonId(), keyword.getName() ).execute();
        return UPDATE_OK;
    }

    @Override
    public void deleteKeyword( Keyword keyword ) throws IOException {
        keywordRestAPI.deleteKeyword( keyword.getId() ).execute();
    }

    @Override
    public void deleteAllKeywords() throws IOException {
        keywordRestAPI.deleteAllKeywords().execute();
    }
}
