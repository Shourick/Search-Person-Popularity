package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;

/**
 * Данный интрефейс описывает контракт для работы с данными проекта Search Person Popularity
 * Все классы, которые будут работать с данными проекта должны подписать данный контракт
 *
 * Интрефейс содержит CRUD операции с данными таблицы 'admins' Репозитория
 *
 * Created by skubatko on 03/11/17.
 */

public interface IAdminRepository {
    public void addAdmin( Admin admin );         // добавляем Администратора
    Admin getAdmin( int id );                   // получаем Администратора по его id
    List getAllAdmins();                     // получаем список всех Администраторов
    int getAdminsCount();                    // получаем количество Администраторов в Репозитории
    int updateAdmin( Admin admin );              // обновляем Администратора
    void deleteAdmin( Admin admin );             // удаляем Администратора
    void deleteAllAdmins();                  // удаляем всех Администраторов
}
