package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Data;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories.RESTfulKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.RESTfulRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLiteKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;

/**
 * Created by skubatko on 16/11/17
 */

public class KeywordsSync {

    private SQLiteKeywordRepository mKeywordsMain;
    private SQLiteKeywordRepository mKeywordsSynced;
    private RESTfulKeywordRepository mKeywordsApi;

    private List<Keyword> mKeywordListMain;
    private List<Keyword> mKeywordListSynced;
    private List<Keyword> mKeywordListApi;

    public KeywordsSync( SQLiteRepository mainRepository, SQLiteRepository synchronizedRepository, RESTfulRepository apiRepository ) throws IOException {

        mKeywordsMain = mainRepository.getKeywordRepository();
        mKeywordsSynced = synchronizedRepository.getKeywordRepository();
        mKeywordsApi = apiRepository.getKeywordRepository();

        mKeywordListMain = mainRepository.getKeywordRepository().getAllKeywords();
        mKeywordListSynced = synchronizedRepository.getKeywordRepository().getAllKeywords();
        mKeywordListApi = apiRepository.getKeywordRepository().getAllKeywords();
    }

    public void execute() throws IOException {

        // добавляем в Synced и Main данные, добавленные и измененные в Api
        for ( Keyword kApi : mKeywordListApi ) {
            if ( !( mKeywordListSynced.contains( kApi ) ) ) {
                // проверка на обновление данных в pApi
                int foundSyncedKeywordId = mKeywordsSynced.getKeyword( kApi.getId() ).getId();
                if ( foundSyncedKeywordId == EMPTY_ID ) {
                    // была добавлена запись
                    mKeywordsSynced.addKeyword( kApi, kApi.getPersonId() );
                    mKeywordsMain.addKeyword( kApi, kApi.getPersonId() );
                } else {
                    // данные были обновлены
                    mKeywordsSynced.updateKeyword( kApi );
                    mKeywordsMain.updateKeyword( kApi );
                }
            }
        }

        // добавляем в Synced и Api данные, добавленные и измененные в Приложении
        for ( Keyword kMain : mKeywordListMain ) {
            if ( !mKeywordListSynced.contains( kMain ) ) {
                // проверка на обновление данных в Приложении
                int foundSyncedKeyword = mKeywordsSynced.getKeyword( kMain.getId() ).getId();
                if ( foundSyncedKeyword == EMPTY_ID ) {
                    // была добавлена запись
                    int keywordsApiId = mKeywordsApi.addKeyword( kMain, kMain.getPersonId() );
                    Keyword keywordApi = new Keyword( keywordsApiId, kMain.getName(), kMain.getPersonId() );

                    mKeywordsSynced.addKeyword( keywordApi, keywordApi.getPersonId() );

                    mKeywordsMain.deleteKeyword( kMain );
                    mKeywordsMain.addKeyword( keywordApi, keywordApi.getPersonId() );

                } else {
                    // данные были обновлены
                    mKeywordsSynced.updateKeyword( kMain );
                    mKeywordsApi.updateKeyword( kMain );
                }
            }
        }

        // проверяем удаленные и измененные записи
        for ( Keyword sSynced : mKeywordListSynced ) {

            // в Api
            if ( !( mKeywordListApi.contains( sSynced ) ) ) {
                mKeywordsSynced.deleteKeyword( sSynced );
                mKeywordsMain.deleteKeyword( sSynced );
            }

            // в Main
            if ( !( mKeywordListMain.contains( sSynced ) ) ) {
                mKeywordsSynced.deleteKeyword( sSynced );
                mKeywordsApi.deleteKeyword( sSynced );
            }
        }
    }
}
