package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Players;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.PlayersRepositories.RESTfulAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.RESTfulRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.PlayerRepositories.SQLiteAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;

/**
 * Created by skubatko on 16/11/17
 */

public class AdminsSync {

    private SQLiteAdminRepository mAdminsMain;
    private SQLiteAdminRepository mAdminsSynced;
    private RESTfulAdminRepository mAdminsApi;

    private List<Admin> mAdminListMain;
    private List<Admin> mAdminListSynced;
    private List<Admin> mAdminListApi;

    public AdminsSync( SQLiteRepository mainRepository, SQLiteRepository synchronizedRepository, RESTfulRepository apiRepository ) throws IOException {
        mAdminsMain = mainRepository.getAdminRepository();
        mAdminsSynced = synchronizedRepository.getAdminRepository();
        mAdminsApi = apiRepository.getAdminRepository();

        mAdminListMain = mainRepository.getAdminRepository().getAllAdmins();
        mAdminListSynced = synchronizedRepository.getAdminRepository().getAllAdmins();
        mAdminListApi = apiRepository.getAdminRepository().getAllAdmins();
    }

    public void execute() throws IOException {

        // добавляем в Synced и Main данные, добавленные и измененные в Api
        for ( Admin aApi : mAdminListApi ) {
            if ( !( mAdminListSynced.contains( aApi ) ) ) {
                // проверка на обновление данных в pApi
                int foundSyncedAdminId = mAdminsSynced.getAdmin( aApi.getId() ).getId();
                if ( foundSyncedAdminId == EMPTY_ID ) {
                    // была добавлена запись
                    mAdminsSynced.addAdmin( aApi );
                    mAdminsMain.addAdmin( aApi );
                } else {
                    // данные были обновлены
                    mAdminsSynced.updateAdmin( aApi );
                    mAdminsMain.updateAdmin( aApi );
                }
            }
        }

        // добавляем в Synced и Api данные, добавленные и измененные в Приложении
        for ( Admin aMain : mAdminListMain ) {
            if ( !( mAdminListSynced.contains( aMain ) ) ) {
                // проверка на обновление данных в Приложении
                int foundSyncedAdmin = mAdminsSynced.getAdmin( aMain.getId() ).getId();
                if ( foundSyncedAdmin == EMPTY_ID ) {
                    // была добавлена запись
                    int adminsApiId = mAdminsApi.addAdmin( aMain );
                    Admin adminApi = new Admin( adminsApiId, aMain.getNickName(), aMain.getLogin(), aMain.getPassword() );

                    mAdminsSynced.addAdmin( adminApi );

                    mAdminsMain.deleteAdmin( aMain );
                    mAdminsMain.addAdmin( adminApi );

                } else {
                    // данные были обновлены
                    mAdminsSynced.updateAdmin( aMain );
                    mAdminsApi.updateAdmin( aMain );
                }
            }
        }

        // проверяем удаленные и измененные записи
        for ( Admin sSynced : mAdminListSynced ) {

            // в Api
            if ( !( mAdminListApi.contains( sSynced ) ) ) {
                mAdminsSynced.deleteAdmin( sSynced );
                mAdminsMain.deleteAdmin( sSynced );
            }

            // в Main
            if ( !( mAdminListMain.contains( sSynced ) ) ) {
                mAdminsSynced.deleteAdmin( sSynced );
                mAdminsApi.deleteAdmin( sSynced );
            }
        }
    }

}
