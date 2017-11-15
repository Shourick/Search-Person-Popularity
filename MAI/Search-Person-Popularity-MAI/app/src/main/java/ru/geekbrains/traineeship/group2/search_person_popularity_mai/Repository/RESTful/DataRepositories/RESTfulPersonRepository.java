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

/**
 * Created by skubatko on 13/11/17
 */

public class RESTfulPersonRepository implements IPersonRepository {

    private IPersonRestAPI personRestAPI;

    public RESTfulPersonRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( API_URL_BASE )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        personRestAPI = retrofit.create( IPersonRestAPI.class );
    }

    @Override
    public void addPerson( Person person ) throws IOException {
        personRestAPI.addPerson( person.getName() ).execute();
    }

    @Override
    public Person getPersonById( int id ) throws IOException {
        return new Person( id, personRestAPI.getPersonById( id ).execute().body() );
    }

    @Override
    public Person getPersonByName( String name ) throws IOException {
        Response<Integer> response = personRestAPI.getPersonByName( name ).execute();
        if ( response.isSuccessful() ) {

            return new Person( response.body(), name );
        }
        return null;
    }

    @Override
    public List<Person> getAllPersons() throws IOException {
        return personRestAPI.getAllPersons().execute().body();
    }

    @Override
    public int getPersonsCount() throws IOException {
        Response<List<Person>> response = personRestAPI.getAllPersons().execute();
        if ( response.isSuccessful() ) {
            return response.body().size();
        }
        return 0;
    }

    @Override
    public int updatePerson( Person person ) throws IOException {
        personRestAPI.updatePerson( person.getId(), person.getName() ).execute();
        return 0;
    }

    @Override
    public void deletePerson( Person person ) throws IOException {
        personRestAPI.deletePerson( person.getId() ).execute();
    }

    @Override
    public void deleteAllPersons() throws IOException {
        personRestAPI.deleteAllPersons().execute();
    }
}
