package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Data;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories.RESTfulKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.RESTfulRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLiteKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;

/**
 * Created by skubatko on 16/11/17
 */

public class KeywordsSync {

    private SQLiteKeywordRepository keywordsMain;
    private SQLiteKeywordRepository keywordsSynced;
    private RESTfulKeywordRepository keywordsApi;

    private List<Keyword> keywordListMain;
    private List<Keyword> keywordListSynced;
    private List<Keyword> keywordListApi;

    public KeywordsSync( SQLiteRepository synchronizedRepository, RESTfulRepository apiRepository ) throws IOException {

        keywordsMain = repository.getKeywordRepository();
        keywordsSynced = synchronizedRepository.getKeywordRepository();
        keywordsApi = apiRepository.getKeywordRepository();

        keywordListMain = repository.getKeywordRepository().getAllKeywords();
        keywordListSynced = synchronizedRepository.getKeywordRepository().getAllKeywords();
        keywordListApi = apiRepository.getKeywordRepository().getAllKeywords();
    }

    public void execute() throws IOException {

        // добавляем в Synced и Main данные, добавленные и измененные в Api
        for ( Keyword kApi : keywordListApi ) {
            if ( !keywordListSynced.contains( kApi ) ) {
                // проверка на обновление данных в pApi
                int foundSyncedKeywordId = keywordsSynced.getKeyword( kApi.getId() ).getId();
                if ( foundSyncedKeywordId == EMPTY_ID ) {
                    // была добавлена запись
                    keywordsSynced.addKeyword( kApi, kApi.getPersonId() );
                    keywordsMain.addKeyword( kApi, kApi.getPersonId() );
                } else {
                    // данные были обновлены
                    keywordsSynced.updateKeyword( kApi );
                    keywordsMain.updateKeyword( kApi );
                }
            }
        }

        // добавляем в Synced и Api данные, добавленные и измененные в Приложении
        for ( Keyword kMain : keywordListMain ) {
            if ( !keywordListSynced.contains( kMain ) ) {
                // проверка на обновление данных в Приложении
                int foundSyncedKeyword = keywordsSynced.getKeyword( kMain.getId() ).getId();
                if ( foundSyncedKeyword == EMPTY_ID ) {
                    // была добавлена запись
                    int keywordsApiId = keywordsApi.addKeyword( kMain, kMain.getPersonId() );
                    Keyword keywordApi = new Keyword( keywordsApiId, kMain.getName(), kMain.getPersonId() );

                    keywordsSynced.addKeyword( keywordApi, keywordApi.getPersonId() );

                    keywordsMain.deleteKeyword( kMain );
                    keywordsMain.addKeyword( keywordApi, keywordApi.getPersonId() );

                } else {
                    // данные были обновлены
                    keywordsSynced.updateKeyword( kMain );
                    keywordsApi.updateKeyword( kMain );
                }
            }
        }

        // проверяем удаленные и измененные записи
        for ( Keyword sSynced : keywordListSynced ) {

            // в Api
            if ( !( keywordListApi.contains( sSynced ) ) ) {
                keywordsSynced.deleteKeyword( sSynced );
                keywordsMain.deleteKeyword( sSynced );
            }

            // в Main
            if ( !( keywordListMain.contains( sSynced ) ) ) {
                keywordsSynced.deleteKeyword( sSynced );
                keywordsApi.deleteKeyword( sSynced );
            }
        }
    }
}
