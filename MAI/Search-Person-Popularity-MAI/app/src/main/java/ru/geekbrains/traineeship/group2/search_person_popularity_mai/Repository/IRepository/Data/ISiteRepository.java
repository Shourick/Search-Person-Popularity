package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;

/**
 * Данный интрефейс описывает контракт для работы с данными проекта Search Person Popularity
 * Все классы, которые будут работать с данными проекта должны подписать данный контракт
 * <p>
 * Интрефейс содержит CRUD операции с данными таблицы 'sites' Репозитория
 * <p>
 * Created by skubatko on 03/11/17.
 */

public interface ISiteRepository {

    int addSite( Site site ) throws IOException;         // добавляем Сайт

    Site getSite( int id ) throws IOException;                   // получаем Сайт по его id

    List getAllSites() throws IOException;                     // получаем список всех Сайтов

    int getSitesCount() throws IOException;                    // получаем количество Сайтов в Репозитории

    int updateSite( Site site ) throws IOException;              // обновляем Сайт

    void deleteSite( Site site ) throws IOException;             // удаляем Сайт

    void deleteAllSites() throws IOException;                  // удаляем все Сайты
}
