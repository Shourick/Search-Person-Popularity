package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;

/**
 * Данный интрефейс описывает контракт для работы с данными проекта Search Person Popularity
 * Все классы, которые будут работать с данными проекта должны подписать данный контракт
 *
 * Интрефейс содержит CRUD операции с данными таблицы 'sites' Репозитория
 *
 * Created by skubatko on 03/11/17.
 */

public interface ISiteRepository {
    public void addSite( Site site );         // добавляем Сайт
    Site getSite( int id );                   // получаем Сайт по его id
    List getAllSites();                     // получаем список всех Сайтов
    int getSitesCount();                    // получаем количество Сайтов в Репозитории
    int updateSite( Site site );              // обновляем Сайт
    void deleteSite( Site site );             // удаляем Сайт
    void deleteAllSites();                  // удаляем все Сайты
}
