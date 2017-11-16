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
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MESSAGE_SYNCRONIZING;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.SYNCHRONIZED_DATABASE_NAME;

/**
 * Created by skubatko on 15/11/17
 */

public class RepositorySync extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progress;
    private Context context;
    private SQLiteRepository synchronizedRepository;
    private RESTfulRepository apiRepository;

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

            syncPersons();

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
        synchronizedRepository.close();
    }

    private void syncPersons() throws IOException {

        SQLitePersonRepository personsMain = repository.getPersonRepository();
        SQLitePersonRepository personsSynced = synchronizedRepository.getPersonRepository();
        RESTfulPersonRepository personsApi = apiRepository.getPersonRepository();

        List<Person> personListMain = repository.getPersonRepository().getAllPersons();
        List<Person> personListSynced = synchronizedRepository.getPersonRepository().getAllPersons();
        List<Person> personListApi = apiRepository.getPersonRepository().getAllPersons();

        // добавляем в Synced и Main данные, добавленные и измененные в Api
        for ( Person pApi : personListApi ) {
            if ( !personListSynced.contains( pApi ) ) {
                // проверка на обновление данных в pApi
                int foundSyncedPersonId = personsSynced.getPersonById( pApi.getId() ).getId();
                if ( foundSyncedPersonId == EMPTY_ID ) {
                    // была добавлена запись
                    personsSynced.addPerson( pApi );
                    personsMain.addPerson( pApi );
                } else {
                    // данные были обновлены
                    personsSynced.updatePerson( pApi );
                    personsMain.updatePerson( pApi );
                }
            }
        }

        // добавляем в Synced и Api данные, добавленные и измененные в Приложении
        for ( Person pMain : personListMain ) {
            if ( !personListSynced.contains( pMain ) ) {
                // проверка на обновление данных в Приложении
                int foundSyncedPerson = personsSynced.getPersonById( pMain.getId() ).getId();
                if ( foundSyncedPerson == EMPTY_ID ) {
                    // была добавлена запись
                    int personsApiId = personsApi.addPerson( pMain );
                    Person personApi = new Person( personsApiId, pMain.getName() );

                    personsSynced.addPerson( personApi );

                    personsMain.deletePerson( pMain );
                    personsMain.addPerson( personApi );

                } else {
                    // данные были обновлены
                    personsSynced.updatePerson( pMain );
                    personsApi.updatePerson( pMain );
                }
            }
        }

        // проверяем удаленные и измененные записи
        for ( Person pSynced : personListSynced ) {

            // в Api
            if ( !( personListApi.contains( pSynced ) ) ) {
                personsSynced.deletePerson( pSynced );
                personsMain.deletePerson( pSynced );
            }

            // в Main
            if ( !( personListMain.contains( pSynced ) ) ) {
                personsSynced.deletePerson( pSynced );
                personsApi.deletePerson( pSynced );
            }
        }
    }

}
