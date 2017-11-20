package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Data;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories.RESTfulSiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.RESTfulRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLiteSiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;

/**
 * Created by skubatko on 16/11/17
 */

public class SitesSync {

    private SQLiteSiteRepository sitesMain;
    private SQLiteSiteRepository sitesSynced;
    private RESTfulSiteRepository sitesApi;

    private List<Site> siteListMain;
    private List<Site> siteListSynced;
    private List<Site> siteListApi;

    public SitesSync( SQLiteRepository synchronizedRepository, RESTfulRepository apiRepository ) throws IOException {
        sitesMain = repository.getSiteRepository();
        sitesSynced = synchronizedRepository.getSiteRepository();
        sitesApi = apiRepository.getSiteRepository();

        siteListMain = repository.getSiteRepository().getAllSites();
        siteListSynced = synchronizedRepository.getSiteRepository().getAllSites();
        siteListApi = apiRepository.getSiteRepository().getAllSites();
    }

    public void execute() throws IOException {

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
