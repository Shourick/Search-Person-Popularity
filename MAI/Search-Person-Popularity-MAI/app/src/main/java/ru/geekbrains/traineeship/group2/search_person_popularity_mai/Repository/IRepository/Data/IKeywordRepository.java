package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;

/**
 * Данный интрефейс описывает контракт для работы с данными проекта Search Person Popularity
 * Все классы, которые будут работать с данными проекта должны подписать данный контракт
 * <p>
 * Интрефейс содержит CRUD операции с данными таблицы 'keywords' Репозитория
 * <p>
 * Created by skubatko on 03/11/17.
 */

public interface IKeywordRepository {

    int addKeyword( Keyword keyword, int personId ) throws IOException;  // добавляем Ключевое слово для Личности personId

    Keyword getKeyword( int id ) throws IOException;                             // получаем Ключевое слово по его id

    List getPersonKeywords( int personId ) throws IOException;                   // получаем ключевые слова Личности personId

    List getAllKeywords() throws IOException;                                  // получаем список всех Ключевых слов

    int getKeywordsCount() throws IOException;                                 // получаем количество Ключевых слов в Репозитории

    int updateKeyword( Keyword keyword ) throws IOException;                     // обновляем данные Ключеового слова

    void deleteKeyword( Keyword keyword ) throws IOException;                    // удаляем Ключевое слово

    void deleteAllKeywords() throws IOException;                               // удаляем все Ключевые слова
}
