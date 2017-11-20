package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Players;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.PlayersRepositories.RESTfulAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.RESTfulRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.PlayerRepositories.SQLiteAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;

/**
 * Created by skubatko on 16/11/17
 */

public class AdminsSync {

    private SQLiteAdminRepository adminsMain;
    private SQLiteAdminRepository adminsSynced;
    private RESTfulAdminRepository adminsApi;

    private List<Admin> adminListMain;
    private List<Admin> adminListSynced;
    private List<Admin> adminListApi;

    public AdminsSync( SQLiteRepository synchronizedRepository, RESTfulRepository apiRepository ) throws IOException {
        adminsMain = repository.getAdminRepository();
        adminsSynced = synchronizedRepository.getAdminRepository();
        adminsApi = apiRepository.getAdminRepository();

        adminListMain = repository.getAdminRepository().getAllAdmins();
        adminListSynced = synchronizedRepository.getAdminRepository().getAllAdmins();
        adminListApi = apiRepository.getAdminRepository().getAllAdmins();
    }

    public void execute() throws IOException {

        // добавляем в Synced и Main данные, добавленные и измененные в Api
        for ( Admin aApi : adminListApi ) {
            if ( !adminListSynced.contains( aApi ) ) {
                // проверка на обновление данных в pApi
                int foundSyncedAdminId = adminsSynced.getAdmin( aApi.getId() ).getId();
                if ( foundSyncedAdminId == EMPTY_ID ) {
                    // была добавлена запись
                    adminsSynced.addAdmin( aApi );
                    adminsMain.addAdmin( aApi );
                } else {
                    // данные были обновлены
                    adminsSynced.updateAdmin( aApi );
                    adminsMain.updateAdmin( aApi );
                }
            }
        }

        // добавляем в Synced и Api данные, добавленные и измененные в Приложении
        for ( Admin aMain : adminListMain ) {
            if ( !adminListSynced.contains( aMain ) ) {
                // проверка на обновление данных в Приложении
                int foundSyncedAdmin = adminsSynced.getAdmin( aMain.getId() ).getId();
                if ( foundSyncedAdmin == EMPTY_ID ) {
                    // была добавлена запись
                    int adminsApiId = adminsApi.addAdmin( aMain );
                    Admin adminApi = new Admin( adminsApiId, aMain.getNickName(), aMain.getLogin(), aMain.getPassword() );

                    adminsSynced.addAdmin( adminApi );

                    adminsMain.deleteAdmin( aMain );
                    adminsMain.addAdmin( adminApi );

                } else {
                    // данные были обновлены
                    adminsSynced.updateAdmin( aMain );
                    adminsApi.updateAdmin( aMain );
                }
            }
        }

        // проверяем удаленные и измененные записи
        for ( Admin sSynced : adminListSynced ) {

            // в Api
            if ( !( adminListApi.contains( sSynced ) ) ) {
                adminsSynced.deleteAdmin( sSynced );
                adminsMain.deleteAdmin( sSynced );
            }

            // в Main
            if ( !( adminListMain.contains( sSynced ) ) ) {
                adminsSynced.deleteAdmin( sSynced );
                adminsApi.deleteAdmin( sSynced );
            }
        }
    }

}
