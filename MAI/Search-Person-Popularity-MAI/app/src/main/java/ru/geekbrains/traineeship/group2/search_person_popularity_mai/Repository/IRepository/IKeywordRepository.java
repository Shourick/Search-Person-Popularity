package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;

/**
 * Данный интрефейс описывает контракт для работы с данными проекта Search Person Popularity
 * Все классы, которые будут работать с данными проекта должны подписать данный контракт
 *
 * Интрефейс содержит CRUD операции с данными таблицы 'keywords'
 *
 * Created by skubatko on 03/11/17.
 */

public interface IKeywordRepository {
    public void addKeyword( Keyword keyword, int personId );  // добавляем Ключевое слово для Личности personId
    Keyword getKeyword( int id );                             // получаем Ключевое слово по его id
    List getPersonKeywords( int personId );                   // получаем ключевые слова Личности personId
    List getAllKeywords();                                  // получаем список всех Ключевых слов
    int getKeywordsCount();                                 // получаем количество Ключевых слов в БД
    int updateKeyword( Keyword keyword );                     // обновляем данные Ключеового слова
    void deleteKeyword( Keyword keyword );                    // удаляем Ключевое слово
    void deleteAllKeywords();                               // удаляем все Ключевые слова
}
