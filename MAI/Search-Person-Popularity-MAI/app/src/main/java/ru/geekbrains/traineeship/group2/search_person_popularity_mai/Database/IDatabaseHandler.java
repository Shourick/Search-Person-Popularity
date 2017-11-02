package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database.Data.Keyword;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database.Data.Site;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database.Players.Admin;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database.Players.User;

/**
 * Данный интрефейс описывает контаркт для работы с данными проекта Search Person Popularity
 * Все классы, которые будут работать с данными проекта должны подписать данный контракт
 * Интрефейс содержит CRUD операции с данными таблиц БД
 *
 * Created by skubatko on 28/10/17.
 */

public interface IDatabaseHandler {

    public void AddPerson( Person person );       // добавляем Личность
    Person getPersonById( int id );               // получаем Личность по id
    Person getPersonByName( String name );        // получаем Личность по Имени
    List getAllPersons();                       // получаем список всех Личностей
    int getPersonsCount();                      // получаем количество Личностей в БД
    int updatePerson( Person person );            // обновляем данные по Личности
    void deletePerson( Person person );           // удаляем Личность
    void deleteAllPersons();                    // удаляем все Личности

    public void AddKeyword( Keyword keyword, int personId );  // добавляем Ключевое слово для Личности personId
    Keyword getKeyword( int id );                             // получаем Ключевое слово по его id
    List getPersonKeywords( int personId );                   // получаем ключевые слова Личности personId
    List getAllKeywords();                                  // получаем список всех Ключевых слов
    int getKeywordsCount();                                 // получаем количество Ключевых слов в БД
    int updateKeyword( Keyword keyword );                     // обновляем данные Ключеового слова
    void deleteKeyword( Keyword keyword );                    // удаляем Ключевое слово
    void deleteAllKeywords();                               // удаляем все Ключевые слова

    public void AddSite( Site site );         // добавляем Сайт
    Site getSite( int id );                   // получаем Сайт по его id
    List getAllSites();                     // получаем список всех Сайтов
    int getSitesCount();                    // получаем количество Сайтов в БД
    int updateSite( Site site );              // обновляем Сайт
    void deleteSite( Site site );             // удаляем Сайт
    void deleteAllSites();                  // удаляем все Сайты

    public void AddUser( User user );         // добавляем Поьзователя
    User getUser( int id );                   // получаем Поьзователя по его id
    List getAllUsers();                     // получаем список всех Поьзователей
    int getUsersCount();                    // получаем количество Поьзователей в БД
    int updateUser( User user );              // обновляем Поьзователя
    void deleteUser( User user );             // удаляем Поьзователя
    void deleteAllUsers();                  // удаляем всех Поьзователей

    public void AddAdmin( Admin admin );         // добавляем Администратора
    Admin getAdmin( int id );                   // получаем Администратора по его id
    List getAllAdmins();                     // получаем список всех Администраторов
    int getAdminsCount();                    // получаем количество Администраторов в БД
    int updateAdmin( Admin admin );              // обновляем Администратора
    void deleteAdmin( Admin admin );             // удаляем Администратора
    void deleteAllAdmins();                  // удаляем всех Администраторов

}
