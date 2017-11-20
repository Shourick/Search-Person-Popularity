package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Data;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories.RESTfulPersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.RESTfulRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLitePersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;


/**
 * Created by skubatko on 16/11/17
 */

public class PersonsSync {
    private SQLitePersonRepository personsMain;
    private SQLitePersonRepository personsSynced;
    private RESTfulPersonRepository personsApi;

    private List<Person> personListMain;
    private List<Person> personListSynced;
    private List<Person> personListApi;

    public PersonsSync( SQLiteRepository synchronizedRepository, RESTfulRepository apiRepository ) throws IOException {
        personsMain = repository.getPersonRepository();
        personsSynced = synchronizedRepository.getPersonRepository();
        personsApi = apiRepository.getPersonRepository();

        personListMain = repository.getPersonRepository().getAllPersons();
        personListSynced = synchronizedRepository.getPersonRepository().getAllPersons();
        personListApi = apiRepository.getPersonRepository().getAllPersons();
    }

    public void execute() throws IOException {

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
