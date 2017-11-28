package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Data;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories.RESTfulPersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.RESTfulRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLitePersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;


/**
 * Created by skubatko on 16/11/17
 */

public class PersonsSync {
    private SQLitePersonRepository mPersonsMain;
    private SQLitePersonRepository mPersonsSynced;
    private RESTfulPersonRepository mPersonsApi;

    private List<Person> mPersonListMain;
    private List<Person> mPersonListSynced;
    private List<Person> mPersonListApi;

    public PersonsSync( SQLiteRepository mainRepository, SQLiteRepository synchronizedRepository, RESTfulRepository apiRepository ) throws IOException {
        mPersonsMain = mainRepository.getPersonRepository();
        mPersonsSynced = synchronizedRepository.getPersonRepository();
        mPersonsApi = apiRepository.getPersonRepository();

        mPersonListMain = mainRepository.getPersonRepository().getAllPersons();
        mPersonListSynced = synchronizedRepository.getPersonRepository().getAllPersons();
        mPersonListApi = apiRepository.getPersonRepository().getAllPersons();
    }

    public void execute() throws IOException {

        // добавляем в Synced и Main данные, добавленные и измененные в Api
        for ( Person pApi : mPersonListApi ) {
            if ( !( mPersonListSynced.contains( pApi ) ) ) {
                // проверка на обновление данных в pApi
                int foundSyncedPersonId = mPersonsSynced.getPersonById( pApi.getId() ).getId();
                if ( foundSyncedPersonId == EMPTY_ID ) {
                    // была добавлена запись
                    mPersonsSynced.addPerson( pApi );
                    mPersonsMain.addPerson( pApi );
                } else {
                    // данные были обновлены
                    mPersonsSynced.updatePerson( pApi );
                    mPersonsMain.updatePerson( pApi );
                }
            }
        }

        // добавляем в Synced и Api данные, добавленные и измененные в Приложении
        for ( Person pMain : mPersonListMain ) {
            if ( !mPersonListSynced.contains( pMain ) ) {
                // проверка на обновление данных в Приложении
                int foundSyncedPerson = mPersonsSynced.getPersonById( pMain.getId() ).getId();
                if ( foundSyncedPerson == EMPTY_ID ) {
                    // была добавлена запись
                    int personsApiId = mPersonsApi.addPerson( pMain );
                    Person personApi = new Person( personsApiId, pMain.getName() );

                    mPersonsSynced.addPerson( personApi );

                    mPersonsMain.deletePerson( pMain );
                    mPersonsMain.addPerson( personApi );

                } else {
                    // данные были обновлены
                    mPersonsSynced.updatePerson( pMain );
                    mPersonsApi.updatePerson( pMain );
                }
            }
        }

        // проверяем удаленные и измененные записи
        for ( Person pSynced : mPersonListSynced ) {

            // в Api
            if ( !( mPersonListApi.contains( pSynced ) ) ) {
                mPersonsSynced.deletePerson( pSynced );
                mPersonsMain.deletePerson( pSynced );
            }

            // в Main
            if ( !( mPersonListMain.contains( pSynced ) ) ) {
                mPersonsSynced.deletePerson( pSynced );
                mPersonsApi.deletePerson( pSynced );
            }
        }
    }

}
