package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.IPersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.IPlayersRestAPI.IPersonRestAPI;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.API_URL_BASE;

/**
 * Created by skubatko on 13/11/17
 */

public class RESTfulPersonRepository implements IPersonRepository {

    private Retrofit retrofit;
    private IPersonRestAPI personRestAPI;

    public RESTfulPersonRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl( API_URL_BASE )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        personRestAPI = retrofit.create( IPersonRestAPI.class );
    }

    @Override
    public void addPerson( Person person ) {
        try {
            personRestAPI.addPerson( person.getName() ).execute();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public Person getPersonById( int id ) {
        try {
            return new Person( id, personRestAPI.getPersonById( id ).execute().body() );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Person getPersonByName( String name ) {
        try {
            return new Person( personRestAPI.getPersonByName( name ).execute().body(), name );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Person> getAllPersons() {
        try {
            return personRestAPI.getAllPersons().execute().body();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public int getPersonsCount() {
        personRestAPI.getAllPersons().enqueue( new Callback<List<Person>>() {
            @Override
            public void onResponse( Call<List<Person>> call, Response<List<Person>> response ) {
                System.out.println("Table response: "+response.body().size());
            }

            @Override
            public void onFailure( Call<List<Person>> call, Throwable t ) {

            }
        } );
        return 0;
    }

    @Override
    public int updatePerson( Person person ) {
        try {
            personRestAPI.updatePerson( person.getId(), person.getName() ).execute();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deletePerson( Person person ) {
        try {
            personRestAPI.deletePerson( person.getId() ).execute();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllPersons() {
        try {
            personRestAPI.deleteAllPersons().execute();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}
