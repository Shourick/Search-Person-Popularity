package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database.IDatabaseHandler;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database.Players.Admin;

/**
 * Данный интрефейс описывает контракт для работы с данными проекта Search Person Popularity
 * Все классы, которые будут работать с данными проекта должны подписать данный контракт
 *
 * Интрефейс содержит CRUD операции с данными таблицы 'admins'
 *
 * Created by skubatko on 03/11/17.
 */

public interface IDBAdminsHandler {
    public void AddAdmin( Admin admin );         // добавляем Администратора
    Admin getAdmin( int id );                   // получаем Администратора по его id
    List getAllAdmins();                     // получаем список всех Администраторов
    int getAdminsCount();                    // получаем количество Администраторов в БД
    int updateAdmin( Admin admin );              // обновляем Администратора
    void deleteAdmin( Admin admin );             // удаляем Администратора
    void deleteAllAdmins();                  // удаляем всех Администраторов
}
