package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Players;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.PlayersRepositories.RESTfulUserRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.RESTfulRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.PlayerRepositories.SQLiteUserRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;

/**
 * Created by skubatko on 16/11/17
 */

public class UsersSync {

    private SQLiteUserRepository mUsersMain;
    private SQLiteUserRepository mUsersSynced;
    private RESTfulUserRepository mUsersApi;

    private List<User> mUserListMain;
    private List<User> mUserListSynced;
    private List<User> mUserListApi;

    public UsersSync( SQLiteRepository mainRepository, SQLiteRepository synchronizedRepository, RESTfulRepository apiRepository ) throws IOException {
        mUsersMain = mainRepository.getUserRepository();
        mUsersSynced = synchronizedRepository.getUserRepository();
        mUsersApi = apiRepository.getUserRepository();

        mUserListMain = mainRepository.getUserRepository().getAllUsers();
        mUserListSynced = synchronizedRepository.getUserRepository().getAllUsers();
        mUserListApi = apiRepository.getUserRepository().getAllUsers();
    }

    public void execute() throws IOException {

        // добавляем в Synced и Main данные, добавленные и измененные в Api
        for ( User uApi : mUserListApi ) {
            if ( !( mUserListSynced.contains( uApi ) ) ) {
                // проверка на обновление данных в pApi
                int foundSyncedUserId = mUsersSynced.getUser( uApi.getId() ).getId();
                if ( foundSyncedUserId == EMPTY_ID ) {
                    // была добавлена запись
                    mUsersSynced.addUser( uApi );
                    mUsersMain.addUser( uApi );
                } else {
                    // данные были обновлены
                    mUsersSynced.updateUser( uApi );
                    mUsersMain.updateUser( uApi );
                }
            }
        }

        // добавляем в Synced и Api данные, добавленные и измененные в Приложении
        for ( User uMain : mUserListMain ) {
            if ( !( mUserListSynced.contains( uMain ) ) ) {
                // проверка на обновление данных в Приложении
                int foundSyncedUser = mUsersSynced.getUser( uMain.getId() ).getId();
                if ( foundSyncedUser == EMPTY_ID ) {
                    // была добавлена запись
                    int usersApiId = mUsersApi.addUser( uMain );
                    User userApi = new User( usersApiId, uMain.getNickName(), uMain.getLogin(), uMain.getPassword() );

                    mUsersSynced.addUser( userApi );

                    mUsersMain.deleteUser( uMain );
                    mUsersMain.addUser( userApi );

                } else {
                    // данные были обновлены
                    mUsersSynced.updateUser( uMain );
                    mUsersApi.updateUser( uMain );
                }
            }
        }

        // проверяем удаленные и измененные записи
        for ( User sSynced : mUserListSynced ) {

            // в Api
            if ( !( mUserListApi.contains( sSynced ) ) ) {
                mUsersSynced.deleteUser( sSynced );
                mUsersMain.deleteUser( sSynced );
            }

            // в Main
            if ( !( mUserListMain.contains( sSynced ) ) ) {
                mUsersSynced.deleteUser( sSynced );
                mUsersApi.deleteUser( sSynced );
            }
        }
    }

}
