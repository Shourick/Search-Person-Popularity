package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Players;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.PlayersRepositories.RESTfulUserRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.RESTfulRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.PlayerRepositories.SQLiteUserRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;

/**
 * Created by skubatko on 16/11/17
 */

public class UsersSync {

    private SQLiteUserRepository usersMain;
    private SQLiteUserRepository usersSynced;
    private RESTfulUserRepository usersApi;

    private List<User> userListMain;
    private List<User> userListSynced;
    private List<User> userListApi;

    public UsersSync( SQLiteRepository synchronizedRepository, RESTfulRepository apiRepository ) throws IOException {
        usersMain = repository.getUserRepository();
        usersSynced = synchronizedRepository.getUserRepository();
        usersApi = apiRepository.getUserRepository();

        userListMain = repository.getUserRepository().getAllUsers();
        userListSynced = synchronizedRepository.getUserRepository().getAllUsers();
        userListApi = apiRepository.getUserRepository().getAllUsers();
    }

    public void execute() throws IOException {

        // добавляем в Synced и Main данные, добавленные и измененные в Api
        for ( User uApi : userListApi ) {
            if ( !userListSynced.contains( uApi ) ) {
                // проверка на обновление данных в pApi
                int foundSyncedUserId = usersSynced.getUser( uApi.getId() ).getId();
                if ( foundSyncedUserId == EMPTY_ID ) {
                    // была добавлена запись
                    usersSynced.addUser( uApi );
                    usersMain.addUser( uApi );
                } else {
                    // данные были обновлены
                    usersSynced.updateUser( uApi );
                    usersMain.updateUser( uApi );
                }
            }
        }

        // добавляем в Synced и Api данные, добавленные и измененные в Приложении
        for ( User uMain : userListMain ) {
            if ( !userListSynced.contains( uMain ) ) {
                // проверка на обновление данных в Приложении
                int foundSyncedUser = usersSynced.getUser( uMain.getId() ).getId();
                if ( foundSyncedUser == EMPTY_ID ) {
                    // была добавлена запись
                    int usersApiId = usersApi.addUser( uMain );
                    User userApi = new User( usersApiId, uMain.getNickName(), uMain.getLogin(), uMain.getPassword() );

                    usersSynced.addUser( userApi );

                    usersMain.deleteUser( uMain );
                    usersMain.addUser( userApi );

                } else {
                    // данные были обновлены
                    usersSynced.updateUser( uMain );
                    usersApi.updateUser( uMain );
                }
            }
        }

        // проверяем удаленные и измененные записи
        for ( User sSynced : userListSynced ) {

            // в Api
            if ( !( userListApi.contains( sSynced ) ) ) {
                usersSynced.deleteUser( sSynced );
                usersMain.deleteUser( sSynced );
            }

            // в Main
            if ( !( userListMain.contains( sSynced ) ) ) {
                usersSynced.deleteUser( sSynced );
                usersApi.deleteUser( sSynced );
            }
        }
    }


}
