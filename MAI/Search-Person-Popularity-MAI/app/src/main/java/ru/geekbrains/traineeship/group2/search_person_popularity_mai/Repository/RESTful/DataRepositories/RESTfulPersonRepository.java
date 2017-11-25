package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.IPersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.IRestAPI.IPersonRestAPI;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.API_URL_BASE;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.UPDATE_OK;

/**
 * Created by skubatko on 13/11/17
 */

public class RESTfulPersonRepository implements IPersonRepository {

    private IPersonRestAPI mPersonRestAPI;

    public RESTfulPersonRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( API_URL_BASE )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        mPersonRestAPI = retrofit.create( IPersonRestAPI.class );
    }

    @Override
    public int addPerson( Person person ) throws IOException {
        Response<Integer> response = mPersonRestAPI.addPerson( person.getName() ).execute();
        if ( response.isSuccessful() ) {
            return response.body();
        }
        return EMPTY_ID;
    }

    @Override
    public Person getPersonById( int id ) throws IOException {
        Response<String> response = mPersonRestAPI.getPersonById( id ).execute();
        if ( response.isSuccessful() ) {
            return new Person( id, response.body() );
        }
        return new Person( EMPTY_ID, EMPTY_NAME );
    }

    @Override
    public Person getPersonByName( String name ) throws IOException {
        Response<Integer> response = mPersonRestAPI.getPersonByName( name ).execute();
        if ( response.isSuccessful() ) {

            return new Person( response.body(), name );
        }
        return new Person( EMPTY_ID, EMPTY_NAME );
    }

    @Override
    public List<Person> getAllPersons() throws IOException {
        return mPersonRestAPI.getAllPersons().execute().body();
    }

    @Override
    public int getPersonsCount() throws IOException {
        Response<List<Person>> response = mPersonRestAPI.getAllPersons().execute();
        if ( response.isSuccessful() ) {
            return response.body().size();
        }
        return EMPTY_ID;
    }

    @Override
    public int updatePerson( Person person ) throws IOException {
        mPersonRestAPI.updatePerson( person.getId(), person.getName() ).execute();
        return UPDATE_OK;
    }

    @Override
    public void deletePerson( Person person ) throws IOException {
        mPersonRestAPI.deletePerson( person.getId() ).execute();
    }

    @Override
    public void deleteAllPersons() throws IOException {
        mPersonRestAPI.deleteAllPersons().execute();
    }
}
