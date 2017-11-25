package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Data;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories.RESTfulSiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.RESTfulRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLiteSiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;

/**
 * Created by skubatko on 16/11/17
 */

public class SitesSync {

    private SQLiteSiteRepository mSitesMain;
    private SQLiteSiteRepository mSitesSynced;
    private RESTfulSiteRepository mSitesApi;

    private List<Site> mSiteListMain;
    private List<Site> mSiteListSynced;
    private List<Site> mSiteListApi;

    public SitesSync( SQLiteRepository mainRepository, SQLiteRepository synchronizedRepository, RESTfulRepository apiRepository ) throws IOException {
        mSitesMain = mainRepository.getSiteRepository();
        mSitesSynced = synchronizedRepository.getSiteRepository();
        mSitesApi = apiRepository.getSiteRepository();

        mSiteListMain = mainRepository.getSiteRepository().getAllSites();
        mSiteListSynced = synchronizedRepository.getSiteRepository().getAllSites();
        mSiteListApi = apiRepository.getSiteRepository().getAllSites();
    }

    public void execute() throws IOException {

        // добавляем в Synced и Main данные, добавленные и измененные в Api
        for ( Site sApi : mSiteListApi ) {
            if ( !( mSiteListSynced.contains( sApi ) ) ) {
                // проверка на обновление данных в pApi
                int foundSyncedSiteId = mSitesSynced.getSite( sApi.getId() ).getId();
                if ( foundSyncedSiteId == EMPTY_ID ) {
                    // была добавлена запись
                    mSitesSynced.addSite( sApi );
                    mSitesMain.addSite( sApi );
                } else {
                    // данные были обновлены
                    mSitesSynced.updateSite( sApi );
                    mSitesMain.updateSite( sApi );
                }
            }
        }

        // добавляем в Synced и Api данные, добавленные и измененные в Приложении
        for ( Site sMain : mSiteListMain ) {
            if ( !( mSiteListSynced.contains( sMain ) ) ) {
                // проверка на обновление данных в Приложении
                int foundSyncedSite = mSitesSynced.getSite( sMain.getId() ).getId();
                if ( foundSyncedSite == EMPTY_ID ) {
                    // была добавлена запись
                    int sitesApiId = mSitesApi.addSite( sMain );
                    Site siteApi = new Site( sitesApiId, sMain.getName() );

                    mSitesSynced.addSite( siteApi );

                    mSitesMain.deleteSite( sMain );
                    mSitesMain.addSite( siteApi );

                } else {
                    // данные были обновлены
                    mSitesSynced.updateSite( sMain );
                    mSitesApi.updateSite( sMain );
                }
            }
        }

        // проверяем удаленные и измененные записи
        for ( Site sSynced : mSiteListSynced ) {

            // в Api
            if ( !( mSiteListApi.contains( sSynced ) ) ) {
                mSitesSynced.deleteSite( sSynced );
                mSitesMain.deleteSite( sSynced );
            }

            // в Main
            if ( !( mSiteListMain.contains( sSynced ) ) ) {
                mSitesSynced.deleteSite( sSynced );
                mSitesApi.deleteSite( sSynced );
            }
        }
    }

}
