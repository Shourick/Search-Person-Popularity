package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.Persons;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.API_URL_BASE;

/**
 * Created by skubatko on 15/11/17
 */

public class RESTfulPersonsLoader extends AsyncTask<Void, Void, List<Person>> {

    private ProgressDialog progress;
    private Context context;

    public RESTfulPersonsLoader( Context context ) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog( context );
        progress.setMessage( "Loading ..." );
        progress.show();
    }

    @Override
    protected List<Person> doInBackground( Void... params ) {
        List<Person> persons = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( API_URL_BASE )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        IPersonRestAPI personRestAPI = retrofit.create( IPersonRestAPI.class );
        try {
            persons = personRestAPI.getAllPersons().execute().body();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        try {
            Thread.sleep( 1000 );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }

        return persons;
    }

    @Override
    protected void onPostExecute( List<Person> persons ) {
        for ( Person o : persons ) {
            repository.getPersonRepository().addPerson( o );
        }
        progress.dismiss();
    }
}
