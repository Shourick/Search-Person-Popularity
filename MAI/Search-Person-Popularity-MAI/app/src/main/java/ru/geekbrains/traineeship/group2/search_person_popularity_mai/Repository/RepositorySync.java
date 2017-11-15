package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories.RESTfulPersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.RESTfulRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLitePersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MESSAGE_SYNCRONIZING;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.SYNCHRONIZED_DATABASE_NAME;

/**
 * Created by skubatko on 15/11/17
 */

public class RepositorySync extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progress;
    private Context context;
    SQLiteRepository synchronizedRepository;
    RESTfulRepository apiRepository;

    public RepositorySync( Context context ) {
        this.context = context;
        synchronizedRepository = new SQLiteRepository( context, SYNCHRONIZED_DATABASE_NAME );
        apiRepository = new RESTfulRepository();
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog( context );
        progress.setMessage( MESSAGE_SYNCRONIZING );
        progress.show();
    }

    @Override
    protected Void doInBackground( Void... params ) {

        try {

            // удалить после тестирования
//            repository.getPersonRepository().deleteAllPersons();


            SQLitePersonRepository personsMain = repository.getPersonRepository();
            SQLitePersonRepository personsSynced = synchronizedRepository.getPersonRepository();
            RESTfulPersonRepository personsApi = apiRepository.getPersonRepository();

            List<Person> personListMain = repository.getPersonRepository().getAllPersons();
            List<Person> personListSynced = synchronizedRepository.getPersonRepository().getAllPersons();
            List<Person> personListApi = apiRepository.getPersonRepository().getAllPersons();

            for ( Person pApi : personListApi ) {
                if ( !personListMain.contains( pApi ) ) {
                    personsMain.addPerson( pApi );
                }
            }

        } catch ( IOException e ) {
            e.printStackTrace();
        }

        try {
            Thread.sleep( 1000 );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute( Void aVoid ) {
        progress.dismiss();
    }

}
