package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories.RESTfulPersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories.RESTfulSiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.RESTfulRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLitePersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLiteSiteRepository;
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
//            syncKeywords();
            syncSites();
//            syncUsers();
//            syncAdmins();

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

    private void syncSites() throws IOException {

        SQLiteSiteRepository sitesMain = repository.getSiteRepository();
        SQLiteSiteRepository sitesSynced = synchronizedRepository.getSiteRepository();
        RESTfulSiteRepository sitesApi = apiRepository.getSiteRepository();

        List<Site> siteListMain = repository.getSiteRepository().getAllSites();
        List<Site> siteListSynced = synchronizedRepository.getSiteRepository().getAllSites();
        List<Site> siteListApi = apiRepository.getSiteRepository().getAllSites();

        // добавляем в Synced и Main данные, добавленные и измененные в Api
        for ( Site sApi : siteListApi ) {
            if ( !siteListSynced.contains( sApi ) ) {
                // проверка на обновление данных в pApi
                int foundSyncedSiteId = sitesSynced.getSite( sApi.getId() ).getId();
                if ( foundSyncedSiteId == EMPTY_ID ) {
                    // была добавлена запись
                    sitesSynced.addSite( sApi );
                    sitesMain.addSite( sApi );
                } else {
                    // данные были обновлены
                    sitesSynced.updateSite( sApi );
                    sitesMain.updateSite( sApi );
                }
            }
        }

        // добавляем в Synced и Api данные, добавленные и измененные в Приложении
        for ( Site sMain : siteListMain ) {
            if ( !siteListSynced.contains( sMain ) ) {
                // проверка на обновление данных в Приложении
                int foundSyncedSite = sitesSynced.getSite( sMain.getId() ).getId();
                if ( foundSyncedSite == EMPTY_ID ) {
                    // была добавлена запись
                    int sitesApiId = sitesApi.addSite( sMain );
                    Site siteApi = new Site( sitesApiId, sMain.getName() );

                    sitesSynced.addSite( siteApi );

                    sitesMain.deleteSite( sMain );
                    sitesMain.addSite( siteApi );

                } else {
                    // данные были обновлены
                    sitesSynced.updateSite( sMain );
                    sitesApi.updateSite( sMain );
                }
            }
        }

        // проверяем удаленные и измененные записи
        for ( Site sSynced : siteListSynced ) {

            // в Api
            if ( !( siteListApi.contains( sSynced ) ) ) {
                sitesSynced.deleteSite( sSynced );
                sitesMain.deleteSite( sSynced );
            }

            // в Main
            if ( !( siteListMain.contains( sSynced ) ) ) {
                sitesSynced.deleteSite( sSynced );
                sitesApi.deleteSite( sSynced );
            }
        }
    }

}
